# sample-batch-app
## Agenda
* Using spring batch we are going to process a csv file and save it to employee_details table
* Call 'POST api/v1/springbatchdemo/jobs' endpoint to start processing
* After hitting the endpoint JobLauncher start the job
* In BatchConfiguration the following Beans are defined who are responsible for batch processing
  * ItemReader to read the csv file
  * ItemProcessor to process the data, here the job_title Vice President is skipped
  * ItemWriter to write the data into table using Employee entity
  * Step 'importEmployeeStep' accumulate the reader, processor, writer
  * Job 'importEmployeesJob' execute 'importEmployeeStep'
## Help
* https://github.com/saranyakalaiselvan/spring.batch.demo
* https://www.youtube.com/watch?v=uUlv2fveXkk&t=385s

