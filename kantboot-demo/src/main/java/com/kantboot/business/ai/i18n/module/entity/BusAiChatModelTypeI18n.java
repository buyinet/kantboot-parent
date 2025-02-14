package com.kantboot.business.ai.i18n.module.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * 模型分类国际化表
 * Model category internationalization table
 */
@Table(name="bus_ai_chat_model_type_i18n")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusAiChatModelTypeI18n implements Serializable {

    /**
     * 主键
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Modified time
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 主体ID
     * Body ID
     */
    @Column(name = "body_id")
    private Long bodyId;

    /**
     * 语言编码
     * Language code
     */
    @Column(name = "language_code")
    private String languageCode;

    /**
     * 名称
     * Name
     */
    @Column(name = "t_name")
    private String name;


}
