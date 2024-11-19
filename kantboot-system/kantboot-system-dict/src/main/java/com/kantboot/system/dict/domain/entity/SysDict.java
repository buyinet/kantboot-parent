package com.kantboot.system.dict.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Table(name="sys_dict")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class SysDict implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 字典分组code
     */
    @Column(name = "t_group_code", length = 64)
    private String groupCode;

    /**
     * 字典编码
     */
    @Column(name = "t_code", length = 3000)
    private String code;

    /**
     * 字典值
     */
    @Column(name = "t_value")
    private String value;

    /**
     * 英文值
     */
    @Column(name = "t_value_en")
    private String valueEn;

    /**
     * 字典描述
     */
    @Column(name = "description")
    private String description;
    
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后一次修改时间
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

}
