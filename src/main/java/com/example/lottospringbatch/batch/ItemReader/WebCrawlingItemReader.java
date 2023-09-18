package com.example.lottospringbatch.batch.ItemReader;

import com.example.lottospringbatch.batch.entity.Game;
import com.example.lottospringbatch.util.WebCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.IOException;
import java.util.List;

@Slf4j
public class WebCrawlingItemReader<Game> implements ItemReader<Game> {

    private final String url;

    public WebCrawlingItemReader(String url) {
        log.info("url : "+url);
        this.url = url;
    }

    @Override
    public Game read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        log.info("url2 :"+url);
        return (Game) WebCrawler.crawlDataFromWebsite(url);
    }
}
