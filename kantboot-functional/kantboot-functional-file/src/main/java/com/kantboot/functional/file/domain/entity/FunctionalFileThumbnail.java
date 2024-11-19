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

@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "functional_file_thumbnail")
@DynamicUpdate
@DynamicInsert
public class FunctionalFileThumbnail {

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
     * 文件id
     */
    @Column(name = "file_id")
    private Long fileId;

    /**
     * quality 缩略质量
     */
    @Column(name = "quality")
    private Float quality;

    /**
     * 文件组编码
     * 如：头像、文章图片等
     */
    @Column(name = "file_group_code",length = 64)
    private String groupCode;

    /**
     * 编码
     */
    @Column(name = "code",length = 64)
    private String code;

    /**
     * 上传时的文件名
     */
    @Column(name = "file_original_name")
    private String originalName;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String name;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String type;

    /**
     * 文件ContentType
     */
    @Column(name = "file_content_type")
    private String contentType;

    @OneToOne
    @JoinColumn(name = "file_group_code",referencedColumnName = "code",
            foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT)
            ,insertable = false,updatable = false)
    private FunctionalFileGroup group;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Long size;

    /**
     * 文件MD5值，通过MD5值判断文件是否重复
     */
    @Column(name = "file_md5",length = 64)
    private String md5;

    /**
     * 文件上传者id
     */
    @Column(name = "user_account_id_of_upload")
    private Long userIdAccountOfUpload;

    /**
     * 上传时IP
     */
    @Column(name = "ip_of_upload")
    private String ipOfUpload;

}
