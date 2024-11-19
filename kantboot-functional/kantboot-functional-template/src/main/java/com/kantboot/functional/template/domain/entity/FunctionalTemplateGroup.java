package com.kantboot.functional.template.domain.entity;

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
 * 模板分组实体类
 */
@Entity
@Getter
@Setter
@Table(name = "functional_template_group")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class FunctionalTemplateGroup implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 编码
     */
    @Column(name="t_code",length = 64,unique = true)
    private String code;

    /**
     * 名称
     */
    @Column(name="t_name")
    private String name;

    /**
     * 描述
     */
    @Column(name="description",length = 1024)
    private String description;

    /**
     * 优先级
     */
    @Column(name="t_priority")
    private Long priority;

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


}
