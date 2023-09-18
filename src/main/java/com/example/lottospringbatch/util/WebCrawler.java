package com.example.lottospringbatch.util;

import com.example.lottospringbatch.batch.entity.Game;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class WebCrawler {

    public static Game crawlDataFromWebsite(String url) {
        // 사이트 크롤링
        // https://dhlottery.co.kr/gameResult.do?method=byWin

        String round = null;
        Document document = null;

        log.info("Call Url : {}" + url);

        try {
            document = Jsoup.connect(url).get(); // 웹 페이지에 연결하여 HTML 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 현재 라운드를 얻습니다.
        Element dwrNo = document.select("select").first();
        round = dwrNo.select("option").first().text();
        long weekNumber = LottoCalculate.calculateWeekNumber();

        log.info("get Round:{} , getWeekNumber:{}, TF:{}",round,weekNumber,Long.parseLong(round) == weekNumber);

        // 원하는 테이블 선택하기 (여기서는 첫 번째 테이블 선택)
        Elements tdElements = document.select("table").first().select("td");
        Elements ballNums = document.select("div.nums").select("span");

        return Game.builder()
                .round(Integer.parseInt(round))
                .ballNum1(Integer.parseInt(ballNums.get(0).text()))
                .ballNum2(Integer.parseInt(ballNums.get(1).text()))
                .ballNum3(Integer.parseInt(ballNums.get(2).text()))
                .ballNum4(Integer.parseInt(ballNums.get(3).text()))
                .ballNum5(Integer.parseInt(ballNums.get(4).text()))
                .ballNum6(Integer.parseInt(ballNums.get(5).text()))
                .bonusNum(Integer.parseInt(ballNums.get(6).text()))
                .drwNoDate(LottoCalculate.drawDay(Integer.parseInt(round)))
                .firstPrzwnerCo(StringUtils.deleteCommaStrToNum(tdElements.get(2).text()))
                .firstAccumamnt(StringUtils.koreaWonToNum(tdElements.get(3).text()))
                .secondPrzwnerCo(StringUtils.deleteCommaStrToNum(tdElements.get(8).text()))
                .secondAccumamnt((int) StringUtils.koreaWonToNum(tdElements.get(9).text()))
                .thirdPrzwnerCo(StringUtils.deleteCommaStrToNum(tdElements.get(13).text()))
                .thirdAccumamnt((int) StringUtils.koreaWonToNum(tdElements.get(14).text()))
                .fourthPrzwnerCo(StringUtils.deleteCommaStrToNum(tdElements.get(18).text()))
                .fourthAccumamnt((int) StringUtils.koreaWonToNum(tdElements.get(19).text()))
                .fifthPrzwnerCo(StringUtils.deleteCommaStrToNum(tdElements.get(23).text()))
                .fifthAccumamnt((int) StringUtils.koreaWonToNum(tdElements.get(24).text()))
                .etc(tdElements.get(5).text())
                .build();
    }
}
