package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {
    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Value("${server.port}")
    private String port;
    @Override
    public String getPort() {
        return port;
    }

    @Override
    public void calculate() {
        calculateStandard();
        calculateParallel();
        calculateLongStream();
    }

    @Override
    public void calculateStandard() {                    // Если идти чисто по заданию, то это самый быстрый метод из заявленных в уроке
        long start = System.currentTimeMillis();

        int sum = Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );

        long end = System.currentTimeMillis();

        logger.info("Execution period: " + (end - start));
    }
    @Override
    public void calculateParallel() {                   // Данный метод указан в шпоргалке, но с таким лимитом он делает хуже,
        long start = System.currentTimeMillis();        // но если поднять лимит, то в какой то момент начнет работать быстрее

        int sum = Stream
                .iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );

        long end = System.currentTimeMillis();

        logger.info("Execution period parallel: " + (end - start));
    }

    @Override
    public void calculateLongStream() {                 // Данный метод был показан куратором, он значительно ускоряет подщет
        long start = System.currentTimeMillis();

        long sum = LongStream
                .range(1, 1_000_000)
                .sum();

        long end = System.currentTimeMillis();

        logger.info("Execution period parallel: " + (end - start));
    }
}
