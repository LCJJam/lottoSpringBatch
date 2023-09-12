package com.example.lottospringbatch.batch;

import com.example.lottospringbatch.entity.LottoEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job yourJob(Step yourStep) {
        return jobBuilderFactory.get("yourJob")
                .flow(yourStep)
                .end()
                .build();
    }

    @Bean
    public Step yourStep( ItemReader<LottoEntity>  itemReader, ItemWriter<LottoEntity> itemWriter, @Qualifier("taskExecutor") TaskExecutor taskExecutor) {

        return stepBuilderFactory.get("yourStep")
                .<LottoEntity, LottoEntity>chunk(10000)
                .reader(itemReader)
                .writer(itemWriter)
                .taskExecutor(taskExecutor)// 트랜잭션 매니저 설정
                .build();
    }
    @Bean
    public ItemReader<LottoEntity> itemReader() {

//        FlatFileItemReader<LottoEntity> itemReader = new FlatFileItemReader<>();
//        itemReader.setResource(new PathResource("C:\\projects\\examples\\csv_demo.csv"));
//
//        /* DefaultLineMapper */
//        DefaultLineMapper<LottoEntity> lineMapper = new DefaultLineMapper<>();
//        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
//        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper()); /* filedSetMapper */
//
//        itemReader.setLineMapper(lineMapper);
//
//        return itemReader;


        List<LottoEntity> lottoNum = new ArrayList<>();

        int i = 30;

            for (int j = 38; j <= 41; j++) {
                for (int k = j + 1; k <= 42; k++) {
                    for (int x = k + 1; x <= 43; x++) {
                        for (int y = x + 1; y <= 44; y++) {
                            for (int z = y + 1; z <= 45; z++) {
                                lottoNum.add(new LottoEntity(i, j, k, x, y, z));
                            }
                        }
                    }
                }
            }


        return new ListItemReader<>(lottoNum);
    }

    @Bean
    public ItemWriter<LottoEntity> itemWriter(EntityManagerFactory entityManagerFactory) {

        JpaItemWriter<LottoEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1); // 적절한 스레드 풀 크기 설정
        taskExecutor.setMaxPoolSize(40);  // 적절한 스레드 풀 크기 설정
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

}