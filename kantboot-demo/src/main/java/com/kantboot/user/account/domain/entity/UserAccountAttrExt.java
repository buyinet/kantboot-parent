package com.kantboot.user.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的属性扩展
 * User attribute extension
 */
@Data
@MappedSuperclass
public class UserAccountAttrExt implements Serializable {
}
