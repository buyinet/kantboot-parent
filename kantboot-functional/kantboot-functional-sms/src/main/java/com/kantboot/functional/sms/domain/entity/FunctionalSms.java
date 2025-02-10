package com.kantboot.functional.sms.domain.entity;

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
 * 短信实体类
 * SMS Entity
 */
@Table(name="functional_sms")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FunctionalSms implements Serializable {

    /**
     * 主键
     * Primary key
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间（即发送时间）
     * Create time (sent time)
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
     * 短信内容
     * SMS content
     */
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    /**
     * 区号
     * Area code
     */
    @Column(name = "phone_area_code")
    private String phoneAreaCode;

    /**
     * 接收者
     * Receiver
     */
    @Column(name = "t_phone")
    private String phone;

    /**
     * 完整号码
     * Full phone
     */
    @Column(name = "full_phone")
    private String fullPhone;

    /**
     * 消息类型
     * 对应 com.kantboot.functional.sms.enums.MessageTypeCodeEnum
     */
    @Column(name = "message_type_code")
    private String messageTypeCode;

    /**
     * 消息状态编码
     */
    @Column(name = "message_status_code")
    private String messageStatusCode;

    /**
     * 发送失败原因编码
     */
    @Column(name = "message_status_fail_reason_code")
    private String messageStatusFailReasonCode;

    /**
     * 发送失败原因
     */
    @Column(name = "message_status_fail_reason")
    private String messageStatusFailReason;


}
