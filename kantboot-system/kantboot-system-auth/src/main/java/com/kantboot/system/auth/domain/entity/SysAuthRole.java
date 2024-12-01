package com.kantboot.system.auth.domain.entity;

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
 * 用户角色实体类
 * User Role Entity
 * @author 方某方
 */
@Table(name="sys_auth_role")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class SysAuthRole implements Serializable {

    /**
     * 主键
     * Primary Key
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = com.kantboot.util.jpa.type.KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     * Create Time
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     * Modified Time
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 角色名称
     * Role Name
     */
    @Column(name = "t_name")
    private String name;

}
