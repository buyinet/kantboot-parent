package com.kantboot.system.setting.exception;


import com.kantboot.util.rest.exception.BaseException;

public class SystemSettingException  {

    /**
     * 分组编码已存在
     */
    public static final BaseException GROUP_CODE_EXIST = BaseException.of("groupCodeExist", "分组编码已存在");

    /**
     * 分组不存在
     */
    public static final BaseException GROUP_NOT_EXIST = BaseException.of("groupNotExist", "分组不存在");


}
