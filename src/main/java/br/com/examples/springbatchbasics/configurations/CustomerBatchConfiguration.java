package br.com.examples.springbatchbasics.configurations;

import br.com.examples.springbatchbasics.domains.Customer;
import br.com.examples.springbatchbasics.models.CustomerInput;
import br.com.examples.springbatchbasics.processors.CustomerItemProcessor;
import br.com.examples.springbatchbasics.readers.CustomerItemReader;
import br.com.examples.springbatchbasics.writters.CustomerItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class CustomerBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    CustomerItemReader customerItemReader;

    @Autowired
    CustomerItemProcessor customerItemProcessor;

    @Autowired
    CustomerItemWriter customerItemWriter;

    @Bean
    public Step importCustomersStep() {
        return stepBuilderFactory.get("STEP-CUSTOMERS-01")
                .<CustomerInput, Customer> chunk(10)
                .reader(customerItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .build();
    }

    @Bean
    public Job importCustomersJob() {
        return jobBuilderFactory.get("JOB-IMPORT-CUSTOMER")
                .flow(importCustomersStep())
                .end()
                .build();
    }

}
