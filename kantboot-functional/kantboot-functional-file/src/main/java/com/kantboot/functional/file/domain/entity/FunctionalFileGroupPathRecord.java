package com.kantboot.functional.file.domain.entity;

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

/**
 * 文件组路径记录
 * 用于记录文件组的路径变更记录
 * File group path record
 * Used to record the path change record of the file group
 *
 * @author 方某方
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "functional_file_group_path_record")
@DynamicUpdate
@DynamicInsert
public class FunctionalFileGroupPathRecord implements Serializable {

    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 文件组修改的次数
     * The number of times the file group is modified
     */
    @Column(name = "number_Of_times")
    private Integer numberOfTimes;

    /**
     * 文件组编码
     * File group code
     */
    @Column(name = "group_code",length = 64)
    private String groupCode;

    /**
     * 文件组路径
     * File group path
     */
    @Column(name = "path")
    private String path;

    /**
     * 文件组路径名称
     * File group path name
     */
    @Column(name = "name")
    private String name;

    /**
     * 文件组路径描述
     * File group path description
     */
    @Column(name = "description")
    private String description;

}
