package com.kantboot.functional.file.domain.vo;

import com.kantboot.functional.file.domain.entity.FunctionalFileGroup;
import com.kantboot.functional.file.domain.entity.FunctionalFileThumbnail;
import com.kantboot.util.jpa.type.KantbootGenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 文件管理实体类
 * 用于管理文件的上传、下载等
 * Functional file management entity class
 * Used to manage file upload, download, etc.
 *
 * @author 方某方
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "functional_file")
@DynamicUpdate
@DynamicInsert
public class FunctionalFileVO implements Serializable {

    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 缩略图
     * Thumbnails
     */
    @OneToMany
    @JoinColumn(name = "file_id",referencedColumnName = "id",insertable = false,updatable = false)
    @OrderBy("quality ASC")
    private List<FunctionalFileThumbnail> thumbnails;

    /**
     * 文件组编码
     * 如：头像、文章图片等
     * File group code
     * Such as: avatar, article picture, etc.
     */
    @Column(name = "file_group_code",length = 64)
    private String groupCode;

    /**
     * 编码
     * 用于文件下载、删除、访问等（以上操作，也可以通过ID进行）
     * Code
     * Used for file download, delete, access, etc.(The above operations can also be performed through ID)
     */
    @Column(name = "code",length = 64)
    private String code;

    /**
     * 上传时的文件名
     * Original file name when uploaded
     */
    @Column(name = "file_original_name")
    private String originalName;

    /**
     * 文件名
     * File name
     */
    @Column(name = "file_name")
    private String name;

    /**
     * 文件类型
     * 如：image/jpeg、application/pdf等
     * File type
     * Such as: image/jpeg, application/pdf, etc.
     */
    @Column(name = "file_type")
    private String type;

    /**
     * 文件ContentType
     * 如：image/jpeg、application/pdf等
     * File ContentType
     * Such as: image/jpeg, application/pdf, etc.
     */
    @Column(name = "file_content_type")
    private String contentType;

    @OneToOne
    @JoinColumn(name = "file_group_code",referencedColumnName = "code",insertable = false,updatable = false)
    private FunctionalFileGroup group;

    /**
     * 文件大小
     * File size
     */
    @Column(name = "file_size")
    private Long size;

    /**
     * 文件MD5值，通过MD5值判断文件是否重复
     * File MD5 value, determine whether the file is duplicated by MD5 value
     */
    @Column(name = "file_md5",length = 64)
    private String md5;

    /**
     * 文件上传者id
     * User ID of the file uploader
     */
    @Column(name = "user_id_of_upload")
    private Long userIdOfUpload;

    /**
     * 上传时IP
     * IP when uploading
     */
    @Column(name = "ip_of_upload")
    private String ipOfUpload;


}
