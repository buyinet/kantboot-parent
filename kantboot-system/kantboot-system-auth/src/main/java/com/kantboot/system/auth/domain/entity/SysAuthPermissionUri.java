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
 * 用户权限URL实体类
 * User Permission URL Entity
 * @author 方某方
 */
@Table(name="sys_auth_permission_uri")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class SysAuthPermissionUri implements Serializable {

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
     * 权限ID
     */
    @Deprecated
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 权限编码
     */
    @Column(name = "permission_code",length = 64)
    private String permissionCode;


    /**
     * URL
     */
    @Column(name = "t_uri")
    private String uri;

    /**
     * 描述
     * Description
     */
    @Column(name = "t_description")
    private String description;

}
