package com.kantboot.socket.websocket.domain.entity;

import com.kantboot.util.jpa.type.KantbootGenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * Websocket的连接记录
 */
@Entity
@Getter
@Setter
@Table(name = "socket_websocket_record")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SocketWebsocketRecord implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
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
     * 存储的键
     */
    @Column(name = "memory")
    private String memory;

    /**
     * 用户ID
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * 状态编码
     * 等待中：waiting
     * 连接失败：connectFail
     * 连接中：connecting
     * 已断开：disconnected
     */
    @Column(name = "status_code")
    private String statusCode;

    /**
     * websocket的ID
     */
    @Column(name = "websocket_id")
    private Long websocketId;

}
