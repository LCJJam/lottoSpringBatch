package com.example.lottospringbatch.batch.config;

import com.example.lottospringbatch.batch.entity.Game;
import com.example.lottospringbatch.batch.entity.MyGameDetail;
import com.example.lottospringbatch.repository.GameRepository;
import com.example.lottospringbatch.util.LottoCalculate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.*;

@Slf4j
@Configuration
@EnableBatchProcessing
public class ScoreThisWeekWinnerConfig {
    private final static String JOB_NAME = "scoreThisWeekWinnerJob";
    private final static String STEP_NAME = "scoreThisWeekWinnerStep";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final GameRepository gameRepository;


    public ScoreThisWeekWinnerConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory, GameRepository gameRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.gameRepository = gameRepository;
    }

    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
//                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }


    public Step step() {
        int round = (int) LottoCalculate.calculateWeekNumber();
        Optional<Game> game = gameRepository.findByRound(round);

        return game.map(value -> stepBuilderFactory.get(STEP_NAME)
                .allowStartIfComplete(true)
                .<MyGameDetail, MyGameDetail>chunk(100)
                .reader(reader(round))
                .processor(processor(value))
                .writer(writer())
                .build())
                .orElse(null);

    }

    private JpaPagingItemReader<MyGameDetail> reader(int round) {
        JpaPagingItemReader<MyGameDetail> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT e FROM MyGameDetail e where e.round = :round");
        reader.setParameterValues(Collections.singletonMap("round", round));
        reader.setEntityManagerFactory(entityManagerFactory);
        return reader;
    }

    private ItemProcessor<MyGameDetail,MyGameDetail> processor(Game game) {
        return item -> {
//            log.info(item.toString());

            int[] myBallArr = {
                    item.getBallNum1(),
                    item.getBallNum2(),
                    item.getBallNum3(),
                    item.getBallNum4(),
                    item.getBallNum5(),
                    item.getBallNum6(),
            };

            int[] gameArr = {
                    game.getBallNum1(),
                    game.getBallNum2(),
                    game.getBallNum3(),
                    game.getBallNum4(),
                    game.getBallNum5(),
                    game.getBallNum6()
            };
            int grade = LottoCalculate.ScoringAndGrading(gameArr, game.getBonusNum() , myBallArr);
            long winnings = 0;
            switch (grade) {
                case 1:
                    winnings = game.getFirstAccumamnt();
                    break;
                case 2:
                    winnings = game.getSecondAccumamnt();
                    break;
                case 3:
                    winnings = game.getThirdAccumamnt();
                    break;
                case 4:
                    winnings = game.getFourthAccumamnt();
                    break;
                case 5:
                    winnings = game.getFifthAccumamnt();
                    break;
                default:
                    winnings = 0;
            }

//            log.info("grade : {} ,finalWinnings :{}",grade, winnings);

            return MyGameDetail.builder()
                    .id(item.getId())
                    .email(item.getEmail())
                    .round(item.getRound())
                    .gameNum(item.getGameNum())
                    .ballNum1(item.getBallNum1())
                    .ballNum2(item.getBallNum2())
                    .ballNum3(item.getBallNum3())
                    .ballNum4(item.getBallNum4())
                    .ballNum5(item.getBallNum5())
                    .ballNum6(item.getBallNum6())
                    .gameGrade(grade)
                    .gameWinnings(winnings)
                    .build();
        };
    }

    private JpaItemWriter<MyGameDetail> writer() {
        JpaItemWriter<MyGameDetail> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }



}
