package com.kantboot.business.ai.domain.entity;

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
 * AI聊天模型
 */
@Table(name="bus_ai_chat_model")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusAiChatModel implements Serializable {

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
     * 介绍
     * Introduction
     */
    @Column(name = "introduction",length = 30000)
    private String introduction;

    /**
     * 模型分类ID
     */
    @Column(name = "type_id")
    private Long typeId;

    /**
     * 模型种类编码
     * Model code
     * 人物：person
     * 书籍：book
     * ...
     */
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 性别编码
     * Gender code
     * 男：male
     * 女：female
     * 其他：other
     */
    @Column(name = "gender_code")
    private String genderCode;

    /**
     * 出生年月
     * Birthday
     */
    @Column(name = "gmt_birthday")
    private Date gmtBirthday;

    /**
     * 年龄，如果为空，则根据出生年月计算
     * Age
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 头像ID
     */
    @Column(name = "file_id_of_avatar")
    private Long fileIdOfAvatar;

    /**
     * 优先级
     */
    @Column(name = "t_priority")
    private Integer priority;

    @OneToMany
    @JoinColumn(name = "model_id")
    private List<BusAiChatModelLabel> labels;

    @OneToMany
    @JoinColumn(name = "model_id")
    private List<BusAiChatModelLanguageSupport> languageSupports;

}
