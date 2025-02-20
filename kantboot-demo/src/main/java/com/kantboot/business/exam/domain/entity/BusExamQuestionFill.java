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

@Table(name="bus_exam_question")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusExamQuestionFill implements Serializable {

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
     * 答案
     * Answer
     */
    @Column(name = "t_answer", length = 65535)
    private String answer;

    /**
     * 是否需要完全匹配
     * 如果不需要完全匹配，则调用AI判断
     * Need full match or not
     * If not, call AI to judge
     */
    @Column(name = "is_full_match")
    private Boolean isFullMatch;

    /**
     * 备注
     * Remark
     */
    @Column(name = "t_remark", length = 65535)
    private String remark;
}
