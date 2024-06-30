package com.sample.batch.app.config;

import com.sample.batch.app.dto.EmployeeDto;
import com.sample.batch.app.entity.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<EmployeeDto, Employee> {

    @Override
    public Employee process(EmployeeDto employeeDto) throws Exception {
        // just excluding job role "Vice President"
        if (employeeDto.jobTitle().equals("Vice President")) {
            return null;
        }
        return mapEmployee(employeeDto);
    }

    private Employee mapEmployee(EmployeeDto employeeDto) {
        return Employee.builder()
                .employeeId(employeeDto.employeeId())
                .age(employeeDto.age())
                .gender(employeeDto.gender())
                .department(employeeDto.department())
                .fullName(employeeDto.fullName())
                .ethnicity(employeeDto.ethnicity())
                .businessUnit(employeeDto.businessUnit())
                .jobTitle(employeeDto.jobTitle())
                .build();
    }

}
