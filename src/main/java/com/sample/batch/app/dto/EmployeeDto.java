package com.sample.batch.app.dto;

public record EmployeeDto(
        String employeeId,
        String fullName,
        String jobTitle,
        String department,
        String businessUnit,
        String gender,
        String ethnicity,
        Integer age
) {
}
