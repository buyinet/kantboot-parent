package com.kantboot.api.amadeus;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static void main(String[] args) {
        // 示例：AT时间（大西洋时间）
//      String atTime = "2023-10-15 10:00:00"; // 格式：yyyy-MM-dd HH:mm:ss
        String atTime = "2025-02-08T19:45:00"; // 格式：yyyy-MM-ddTHH:mm:ssZ

        // 定义AT时区（考虑夏令时）
        ZoneId atZone = ZoneId.of("America/Halifax"); // Halifax是大西洋时间的代表城市

        // 解析输入时间
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(atTime, formatter);

        // 转换为ZonedDateTime（AT时间）
        ZonedDateTime atDateTime = ZonedDateTime.of(localDateTime, atZone);

        // 转换为北京时间（CST）
        ZoneId cstZone = ZoneId.of("Asia/Shanghai");
        ZonedDateTime cstDateTime = atDateTime.withZoneSameInstant(cstZone);

        // 输出结果
        System.out.println("AT时间: " + atDateTime.format(formatter));
        System.out.println("北京时间: " + cstDateTime.format(formatter));
    }
}
