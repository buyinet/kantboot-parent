package com.kantboot.functional.email.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailMessageDTO implements Serializable {

    private String email;
    private String subject;
    private String content;
    private Boolean isHtml;

}
