package com.example.lottospringbatch.batch.config;

import com.example.lottospringbatch.batch.ItemReader.WebCrawlingItemReader;
import com.example.lottospringbatch.batch.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@EnableBatchProcessing
public class GetThisWeekWinningNumConfig {
    private static final String JOB_NAME = "getThisWeekWinningNumJob";
    private static final String STEP_NAME = "getThisWeekWinningNumStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    public GetThisWeekWinningNumConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(step())
                .on("*")
                .end()
                .end()
                .build();
    }

    public Step step() {

        return stepBuilderFactory.get(STEP_NAME)
                .<Game, Game>chunk(10000)
                .reader(reader())
                .writer(writer())
                .build();
    }

    public WebCrawlingItemReader<? extends Game> reader() {
        return new WebCrawlingItemReader<Game>( "https://dhlottery.co.kr/gameResult.do?method=byWin"); // 웹 사이트 URL 설정
    }

    @Bean
    public ItemWriter<Game> writer() {
        JpaItemWriter<Game> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }



}