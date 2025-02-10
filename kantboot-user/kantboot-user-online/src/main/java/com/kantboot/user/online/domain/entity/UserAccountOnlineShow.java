package com.kantboot.user.online.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.kantboot.util.jpa.type.KantbootGenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user_account_online_show")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserAccountOnlineShow implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_account_id",unique = true)
    private Long userAccountId;

    /**
     * 显示的是否在线
     */
    @Column(name = "is_online_of_show")
    private Boolean isOnlineOfShow;

    /**
     * 显示的最后一次在线时间
     */
    @Column(name = "gmt_last_online_of_show")
    private Date gmtLastOnlineOfShow;

    /**
     * 显示的最后一次离线时间
     */
    @Column(name = "gmt_last_offline_of_show")
    private Date gmtLastOfflineOfShow;

}
