package com.example.lottospringbatch.util;

public class StringUtils {

    public static long koreaWonToNum(String str){
        return Long.parseLong(str.replace(",","").replace("원",""));

    }

    public static int deleteCommaStrToNum(String str){
        return Integer.parseInt(str.replace(",",""));
    }

}
