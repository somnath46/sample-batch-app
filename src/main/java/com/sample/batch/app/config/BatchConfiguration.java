package com.sample.batch.app.config;

import com.sample.batch.app.dto.EmployeeDto;
import com.sample.batch.app.entity.Employee;
import com.sample.batch.app.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    // item reader
    @Bean
    @StepScope // when passing jobPararameter need to add this annotation
    public FlatFileItemReader<EmployeeDto> reader(@Value("#{jobParameters['inputFilePath']}")
                                                  FileSystemResource fileSystemResource) {
        return new FlatFileItemReaderBuilder<EmployeeDto>()
                .name("employeeItemReader") // name of the reader
                .linesToSkip(1) // skip line 1 which is header
                .resource(fileSystemResource) // resource where file is present
                .delimited() // delimiter
                .names("employeeId","fullName", "jobTitle","department"
                        ,"businessUnit","gender","ethnicity","age") // field names
                .targetType(EmployeeDto.class) // target class
                .build();
        }

    // item processor
    @Bean
    public ItemProcessor<EmployeeDto, Employee> processor() {
        return new EmployeeProcessor();
    }

    // item writer
    @Bean
    public EmployeeWriter employeeWriter(EmployeeRepository repository) {
        return new EmployeeWriter(repository);
    }

    // step
    @Bean
    public Step importEmployeeStep(final JobRepository repository,
                                   final PlatformTransactionManager platformTransactionManager,
                                   final EmployeeRepository employeeRepository) {
        return new StepBuilder("importEmployeeStep", repository)
                .<EmployeeDto, Employee>chunk(100, platformTransactionManager)
                .reader(reader(null))
                .processor(processor())
                .writer(employeeWriter(employeeRepository))
                .build();
    }

    // job
    @Bean
    public Job importEmployeesJob(final JobRepository repository,
                                  final PlatformTransactionManager transactionManager,
                                  final EmployeeRepository employeeRepository) {
        return new JobBuilder("job" , repository)
                .incrementer(new RunIdIncrementer())
                .start(importEmployeeStep(repository, transactionManager, employeeRepository))
                .build();
    }
}
