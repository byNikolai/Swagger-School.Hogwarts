-- liquibase formatted sql

--changeset bynikolay:create_student_name_index
--comment: Creating index for students search by name
create index student_name_index on students (name);

--changeset bynikolay:create_faculty_name_color_index
--comment: Creating index for faculty search by name and color
create index faculty_name_color_index on faculties (name, color);
