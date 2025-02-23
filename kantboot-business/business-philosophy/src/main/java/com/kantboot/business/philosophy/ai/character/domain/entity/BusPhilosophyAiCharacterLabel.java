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

@Table(name="bus_philosophy_ai_character_label")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusPhilosophyAiCharacterLabel implements Serializable {

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
     * 角色ID
     */
    @Column(name = "character_id")
    private Long characterId;

    /**
     * 标签文字
     */
    @Column(name = "t_text")
    private String text;

    /**
     * 优先级，倒序
     */
    @OrderBy
    @Column(name = "t_priority")
    private Integer priority;

}
