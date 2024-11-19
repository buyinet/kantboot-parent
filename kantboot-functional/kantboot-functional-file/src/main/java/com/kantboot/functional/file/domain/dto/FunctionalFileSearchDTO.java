package com.kantboot.functional.file.domain.dto;

import lombok.Data;

@Data
public class FunctionalFileSearchDTO {

    private Long id;

    /**
     * 文件组编码
     * 如：头像、文章图片等
     */
    private String groupCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 上传时的文件名
     */
    private String originalName;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件ContentType
     */
    private String contentType;

    /**
     * 最小的文件大小
     */
    private Long sizeMin;

    /**
     * 最大的文件大小
     */
    private Long sizeMax;

    /**
     * 文件MD5值，通过MD5值判断文件是否重复
     */
    private String md5;

    /**
     * 文件上传者id
     */
    private Long userIdOfUpload;

    /**
     * 上传时IP
     */
    private String ipOfUpload;

    /**
     * 开始的创建时间
      */
    private String gmtCreateStart;

    /**
     * 结束的创建时间
      */
    private String gmtCreateEnd;

    /**
     * 开始的修改时间
      */
    private String gmtModifiedStart;

    /**
     * 结束的修改时间
      */
    private String gmtModifiedEnd;

}
