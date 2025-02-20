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

@Table(name="bus_exam_question_choice_option")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusExamQuestionChoiceOption implements Serializable {

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
     * 题目ID
     * Question ID
     */
    @Column(name = "question_id")
    private Long questionId;

    /**
     * 题目内容
     * Question content
     */
    @Column(name = "contentJsonStr", length = 65535)
    private String contentJsonStr;

    /**
     * 是否正确
     * Whether it is correct
     */
    @Column(name = "is_right")
    private Boolean isRight;

    /**
     * 备注
     * Remark
     */
    @Column(name = "remark")
    private String remark;

}
