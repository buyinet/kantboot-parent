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

@Table(name="bus_ai_chat_model_label_i18n")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusAiChatModelLabelI18n implements Serializable {

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
     * 模型ID
     */
    @Column(name = "model_id")
    private Long modelId;

    /**
     * 主体ID
     */
    @Column(name = "body_id")
    private Long bodyId;

    @Column(name = "language_code")
    private String languageCode;

    /**
     * 标签文字
     */
    @Column(name = "t_text")
    private String text;

    /**
     * modelI18nId
     */
    @Column(name = "model_i18n_id")
    private Long modelI18nId;

    /**
     * 优先级
     */
    @Column(name = "t_priority")
    private Integer priority;

}
