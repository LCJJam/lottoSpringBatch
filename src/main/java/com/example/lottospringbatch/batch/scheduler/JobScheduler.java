package com.example.lottospringbatch.batch.scheduler;

import com.example.lottospringbatch.batch.config.GetThisWeekWinningNumConfig;
import com.example.lottospringbatch.batch.config.ScoreThisWeekWinnerConfig;
import com.example.lottospringbatch.util.LottoCalculate;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class JobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private GetThisWeekWinningNumConfig getThisWeekWinningNumConfig;


    @Autowired
    private ScoreThisWeekWinnerConfig scoreThisWeekWinnerJob;

    // 스케줄링 주기 설정 (예: 매일 오전 2시에 실행)
    @Scheduled(cron = "1/2 3 * * * ?")
    public void  runGetThisWeekWinningNumJob() throws Exception {
        JobParameters jobParameters = new JobParameters();
        jobLauncher.run(getThisWeekWinningNumConfig.job(), jobParameters);
    }

//    @Scheduled(cron = "2/10 * * * * ?")
//    public void runScoreThisWeekWinnerJob() throws Exception {
//        JobParameters jobParameters = new JobParameters();
//        jobLauncher.run(scoreThisWeekWinnerJob.job(), jobParameters);
//    }
}