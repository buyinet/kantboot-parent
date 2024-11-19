package com.kantboot.user.account.util;

import com.kantboot.user.account.domain.entity.UserAccount;

import java.util.Date;

public class UserAccountUtil {

    /**
     * 获取年龄
     */
    public static Integer getAge(UserAccount userAccount) {
        Date birthday = userAccount.getGmtBirthday();
        if (birthday == null) {
            return null;
        }
        // 获取当前时间的年月日
        Date now = new Date();
        int nowYear = now.getYear();
        int nowMonth = now.getMonth();
        int nowDay = now.getDate();
        // 获取生日的年月日
        int birthYear = birthday.getYear();
        int birthMonth = birthday.getMonth();
        int birthDay = birthday.getDate();
        // 计算年龄
        int age = nowYear - birthYear;
        if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
            age--;
        }
        return age;
    }

}
