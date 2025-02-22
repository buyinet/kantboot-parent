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
import java.util.List;

/**
 * AI聊天模型-国际化
 */
@Table(name="bus_ai_chat_model_i18n")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusAiChatModelI18n implements Serializable {

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
     * 模型名称
     * Model name
     */
    @Column(name = "name")
    private String name;

    /**
     * 模型描述
     * Model description
     */
    @Column(name = "description",length = 30000)
    private String description;

    /**
     * 模型介绍
     * Model introduction
     */
    @Column(name = "introduction",length = 30000)
    private String introduction;

    @OneToMany
    @JoinColumn(name = "model_i18n_id",referencedColumnName = "id",insertable = false,updatable = false)
    private List<BusAiChatModelLabelI18n> labels;

}
