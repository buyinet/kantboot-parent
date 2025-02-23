package com.kantboot.business.philosophy.ai.character.domain.entity;

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
 * AI角色预置对话
 */
@Table(name="bus_philosophy_ai_character")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusPhilosophyAiCharacterPreset implements Serializable {

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
     * 角色
     */
    @Column(name = "role")
    private String role;

    /**
     * 内容
     */
    @Column(name = "t_content",length = 65535)
    private String content;

    /**
     * 优先级
     */
    @Column(name = "t_priority")
    private Integer priority;

    /**
     * 角色ID
     */
    @Column(name = "character_id")
    private Long characterId;

    /**
     * 语言编码
     */
    @Column(name = "language_code")
    private String languageCode;


}
