package com.kantboot.functional.template.domain.entity;

import com.kantboot.util.jpa.type.KantbootGenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板国际化实体类
 * 用于存储模板的国际化信息
 * 比如中文、英文、日文等
 */
@Entity
@Getter
@Setter
@Table(name = "functional_template_i18n")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FunctionalTemplateI18n implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 编码
     */
    @Column(name="t_code",length = 64)
    private String code;

    /**
     * 分组编码
     */
    @Column(name="t_group_code",length = 64)
    private String groupCode;

    /**
     * 语言编码
     */
    @Column(name = "language_code")
    private String languageCode;

    /**
     * 主要内容
     */
    @Column(name="t_content", columnDefinition="TEXT")
    private String content;

    /**
     * 请求参数示例
     */
    @Column(name="request_param_example")
    private String requestParamExample;

    /**
     * 标题
     */
    @Column(name="title")
    private String title;

    /**
     * 名称
     */
    @Column(name="t_name")
    private String name;

    /**
     * 描述
     */
    @Column(name="description")
    private String description;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;


}
