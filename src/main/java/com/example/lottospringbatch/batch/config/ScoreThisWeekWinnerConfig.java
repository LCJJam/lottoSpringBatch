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
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                .start(step())
                .on("*")
                .end()
                .end()
                .build();
    }


    public Step step() {
        int round = (int) LottoCalculate.calculateWeekNumber();
        return stepBuilderFactory.get(STEP_NAME)
                .<MyGameDetail, MyGameDetail>chunk(100)
                .reader(reader(round))
                .processor(processor(round))
                .writer(writer())
                .build();
    }

    private JpaPagingItemReader<MyGameDetail> reader(int round) {
        log.info( "round:" +round);

        JpaPagingItemReader<MyGameDetail> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT e FROM MyGameDetail e where e.round = :round");
        reader.setParameterValues(Collections.singletonMap("round", round));
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(3);
        return reader;
    }

    private ItemProcessor<MyGameDetail,MyGameDetail> processor(int round) {
        List<Game> game = gameRepository.findByRound(round);
//        log.info("log : {}", game.get(0).toString());
        if(game.size() == 0) return null;

        log.info(MyGameDetail.builder().build().toString());

        int[] myBallArr = {
                MyGameDetail.builder().build().getBallNum1(),
                MyGameDetail.builder().build().getBallNum2(),
                MyGameDetail.builder().build().getBallNum3(),
                MyGameDetail.builder().build().getBallNum4(),
                MyGameDetail.builder().build().getBallNum5(),
                MyGameDetail.builder().build().getBallNum6(),
        };

        int[] gameArr = {
                game.get(0).getBallNum1() ,
                game.get(0).getBallNum2() ,
                game.get(0).getBallNum3() ,
                game.get(0).getBallNum4() ,
                game.get(0).getBallNum5() ,
                game.get(0).getBallNum6()
        };
        int bonusNum = game.get(0).getBonusNum();
        int grade = LottoCalculate.ScoringAndGrading(gameArr,bonusNum,myBallArr);
        long winnings;
        switch(grade){
            case 1 : winnings = game.get(0).getFirstAccumamnt();
            case 2 : winnings = game.get(0).getFifthAccumamnt();
            case 3 : winnings = game.get(0).getFifthAccumamnt();
            case 4 : winnings = game.get(0).getFifthAccumamnt();
            case 5 : winnings = game.get(0).getFifthAccumamnt();
            case 6 : winnings = game.get(0).getFifthAccumamnt();
            default: winnings = 0;
        }

        long finalWinnings = winnings;

        return item -> MyGameDetail.builder()
                .id(item.getId())
                .ballNum1(item.getBallNum1())
                .ballNum2(item.getBallNum2())
                .ballNum3(item.getBallNum3())
                .ballNum4(item.getBallNum4())
                .ballNum5(item.getBallNum5())
                .ballNum6(item.getBallNum6())
                .gameGrade(grade)
                .gameWinnings(finalWinnings)
                .build();
    }

    private JpaItemWriter<MyGameDetail> writer() {
        JpaItemWriter<MyGameDetail> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }



}
