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

/**
 * 语言本地化实体类
 * 因为在识别语言时，可能会有多个语言对应一个语言编码，但是在不同的地方，可能会有不同的描述
 * 例如：zh_CN，可能在某个地方是zh，某个地方是zh_CN，所以这里需要一个再对应
 * @author 方某方
 */
@Table(name = "sys_language_localized")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class SysLanguageLocalized implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 语言编码
     */
    @Column(name = "t_code", length = 10)
    private String code;

    /**
     * 对应的语言编码
     */
    @Column(name = "language_code", length = 10)
    private String languageCode;

    /**
     * 描述
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
