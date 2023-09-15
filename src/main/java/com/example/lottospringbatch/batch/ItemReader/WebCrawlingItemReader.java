package com.example.lottospringbatch.batch.ItemReader;

import com.example.lottospringbatch.batch.entity.Game;
import com.example.lottospringbatch.util.WebCrawler;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.IOException;

public class WebCrawlingItemReader<Game> implements ItemReader<Game> {

    private final String url;

    public WebCrawlingItemReader(String url) {
        this.url = url;
    }

    @Override
    public Game read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        try {
            return (Game) WebCrawler.crawlDataFromWebsite(url);
        } catch (IOException e) {
            throw new RuntimeException("Failed to crawl data from website.", e);
        }
    }
}
