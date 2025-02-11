package com.kantboot.functional.verify.code.domain.entity;

import com.kantboot.util.jpa.type.KantbootGenerationType;
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
 * 验证码的实体类
 * @author 方某方
 */
@Table(name="functional_verification_code")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FunctionalVerificationCode implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间（即发送时间）
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 值
     */
    @Column(name = "t_value")
    private String value;

    /**
     * 类型编码
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 场景编码
     */
    @Column(name = "scene_code")
    private String sceneCode;

    /**
     * 状态编码
     */
    @Column(name = "status_code")
    private String statusCode;

    /**
     * 接收者
     * 如果是手机号则 +86-13112341234
     */
    @Column(name = "t_to")
    private String to;

    /**
     * 过期时间
     */
    @Column(name = "gmt_expire")
    private Date gmtExpire;

}
