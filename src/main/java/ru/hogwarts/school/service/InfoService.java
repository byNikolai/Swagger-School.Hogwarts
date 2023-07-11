package ru.hogwarts.school.service;

public interface InfoService {
    String getPort();

    void calculate();

    void calculateStandard();

    void calculateParallel();

    void calculateLongStream();
}
