package com.kantboot.functional.message.domain.entity;

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

@Table(name="functional_message")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FunctionalMessage implements Serializable {

    /**
     * 主键
     * Primary key
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = com.kantboot.util.jpa.type.KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
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

    @Column(name = "tool_code")
    private String toolCode;

    /**
     * 用户账号ID
     * User account ID
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * emit
     */
    @Column(name = "t_emit")
    private String emit;

    /**
     * 数据
     */
    @Column(name = "data_str",length = 30000)
    private String dataStr;

}
