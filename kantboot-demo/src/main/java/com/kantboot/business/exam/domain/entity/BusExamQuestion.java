package com.kantboot.business.exam.domain.entity;

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
 * 题目
 * Question
 */
@Table(name="bus_exam_question")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusExamQuestion implements Serializable {

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

    /**
     * 题目内容
     * Question content
     */
    @Column(name = "contentJsonStr", length = 65535)
    private String contentJsonStr;

    /**
     * 类型编码
     * 选择题：choice
     * 填空题：fill
     * 判断题：judge
     * 论述题：discourse
     */
    @Column(name = "type_code", length = 32)
    private String typeCode;

    /**
     * 选择题编码
     * 单选题：single_choice
     * 多选题：multiple_choice
     */
    @Column(name = "choice_type_code", length = 32)
    private String choiceTypeCode;

    /**
     * 备注
     * Remark
     */
    @Column(name = "t_remark")
    private String remark;


}
