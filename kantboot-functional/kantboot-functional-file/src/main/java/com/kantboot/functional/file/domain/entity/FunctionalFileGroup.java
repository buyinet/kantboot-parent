package com.kantboot.functional.file.domain.entity;

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

import java.util.Date;

/**
 * 文件组管理的实体类
 * 用于管理文件组的上传、下载、路径等
 * Entity class for file group management
 * Used to manage file group upload, download, path, etc.
 *
 * @author 方某方
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "functional_file_group")
@DynamicUpdate
@DynamicInsert
public class FunctionalFileGroup {

    /**
     * 主键
     * 使用雪花算法生成
     * Primary key
     * Generated using snowflake algorithm
     */
    @Id
    @GenericGenerator(name = "snowflakeId", strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     * Create time
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     * Modify time
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;


    /**
     * 文件组编码，用于文件组下载、删除、访问等
     * 如：头像、文章图片等
     * File group code, used for file group download, delete, access, etc.
     * Such as: avatar, article picture, etc.
     */
    @Column(name = "code", length = 64,unique = true)
    private String code;

    /**
     * 文件组名称
     * File group name
     */
    @Column(name = "name", columnDefinition="TEXT")
    private String name;

    /**
     * 文件组路径
     * File group path
     */
    @Column(name = "path", columnDefinition="TEXT")
    private String path;

    /**
     * 是否有缩略图
     * Whether there is a thumbnail
     */
    @Column(name = "is_has_thumbnail")
    private Boolean isHasThumbnail;

    /**
     * 缩略图路径
     * Thumbnail path
     */
    @Column(name = "thumbnail_path", columnDefinition="TEXT")
    private String thumbnailPath;

}
