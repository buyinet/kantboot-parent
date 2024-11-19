package com.kantboot.functional.file.exception;


import com.kantboot.util.rest.exception.BaseException;

public class FunctionalFileException {

    /**
     * 文件不存在
     * File does not exist
     */
    public static final BaseException FILE_NOT_EXIST = new BaseException("fileNotExist", "File does not exist");

    /**
     * 文件组不存在
     * File group does not exist
     */
    public static final BaseException FILE_GROUP_NOT_EXIST = new BaseException("fileGroupNotExist", "File group does not exist");

}
