package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    @Value("${avatars.dir.path}")
    private String avatarsDir;
    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }


    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find avatar");

        return avatarRepository.findByStudentId(studentId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Avatar findOrCreateAvatar(Long studentId) {
        logger.info("Was invoked method for find or create avatar");

        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        logger.warn("Uploading inappropriate avatars will be punished!");


        Student student = studentService.get(studentId);
        Path filePath = buildPath(student, avatarFile.getOriginalFilename());
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findOrCreateAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    @Override
    public List<Avatar> getPage(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for get avatars page");
        logger.debug("getPage, pageNumber=" + pageNumber + "; pageSize=" + pageSize + ";" );

        return avatarRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path buildPath(Student student, String fileName) {
        return Path.of(avatarsDir, student.getId() + "_" + student.getName() + "." + getExtensions(fileName));
    }
}
