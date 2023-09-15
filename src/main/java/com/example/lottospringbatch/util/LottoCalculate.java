package com.example.lottospringbatch.util;

import java.util.Calendar;

public class LottoCalculate {
    public static long calculateWeekNumber() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2002, Calendar.NOVEMBER, 30,21,0,0);

        // 현재 날짜를 얻습니다.
        Calendar currentDate = Calendar.getInstance();

        // 주차를 계산합니다.
        long millisecondsDifference = currentDate.getTimeInMillis() - startDate.getTimeInMillis();
        long weeksDifference = millisecondsDifference / (7 * 24 * 60 * 60 * 1000);

        return weeksDifference;
    }

    public static int ScoringAndGrading (int[] gameArr , int BonusNum , int[] myBallArr){
        int count = 0; // 일치하는 원소의 개수를 저장할 변수 초기화

        // 배열의 길이만큼 반복하여 원소 비교
        for (int i = 0; i < gameArr.length; i++) {
            for (int j = 0; j < myBallArr.length; j++){
                if (gameArr[i] == myBallArr[j]) {
                    count++; // 일치하는 경우 카운트 증가
                }
            }
        }

        switch (count){
            case 6:
                return 1;
            case 5:
                for(int i=0; i<myBallArr.length; i++) {
                    if(myBallArr[i] == BonusNum) {
                        return 2;
                    }
                }
                return 3;
            case 4:
                return 4;
            case 3:
                return 5;
            default:
                return -1;
        }
    }
}
