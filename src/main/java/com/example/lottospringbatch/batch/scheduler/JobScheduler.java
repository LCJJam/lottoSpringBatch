package com.example.lottospringbatch.batch.scheduler;

import com.example.lottospringbatch.batch.config.GetThisWeekWinningNumConfig;
import com.example.lottospringbatch.batch.config.ScoreThisWeekWinnerConfig;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private GetThisWeekWinningNumConfig getThisWeekWinningNumConfig;


    @Autowired
    private ScoreThisWeekWinnerConfig scoreThisWeekWinnerJob;

    // 스케줄링 주기 설정 (예: 매일 오전 2시에 실행)
    @Scheduled(cron = "0 0 10 * * 6") // 토요일 10시
    public void  runGetThisWeekWinningNumJob() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("start time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(getThisWeekWinningNumConfig.job(), jobParameters);
    }

    @Scheduled(cron = "0 0 1 * * 7") // 일요일 01시
    public void runScoreThisWeekWinnerJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("start time",System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(scoreThisWeekWinnerJob.job(), jobParameters);
    }
}