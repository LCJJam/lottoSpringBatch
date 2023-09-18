package com.example.lottospringbatch.batch.config;

import com.example.lottospringbatch.batch.entity.Game;
import com.example.lottospringbatch.repository.GameRepository;
import com.example.lottospringbatch.util.WebCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Objects;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class GetThisWeekWinningNumConfig {
    private static final String JOB_NAME = "getThisWeekWinningNumJob";
    private static final String STEP_NAME = "getThisWeekWinningNumStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final GameRepository gameRepository;

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    public Step step() {

        return stepBuilderFactory.get(STEP_NAME)
                .allowStartIfComplete(true)
                .tasklet((contribution, chunkContext) -> {
                    Game game = WebCrawler.crawlDataFromWebsite("https://dhlottery.co.kr/gameResult.do?method=byWin");
                    gameRepository.save(Objects.requireNonNull(game));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}