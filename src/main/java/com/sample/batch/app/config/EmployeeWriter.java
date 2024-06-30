package com.sample.batch.app.config;

import com.sample.batch.app.entity.Employee;
import com.sample.batch.app.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class EmployeeWriter implements ItemWriter<Employee> {

    private final EmployeeRepository repository;

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        repository.saveAll(chunk);
    }
}
