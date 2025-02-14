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

@Table(name="sys_dict_group")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class SysDictGroup implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 字典编码
     */
    @Column(name = "t_code", length = 64)
    private String code;

    /**
     * 字典名称
     */
    @Column(name = "t_name")
    private String name;

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
