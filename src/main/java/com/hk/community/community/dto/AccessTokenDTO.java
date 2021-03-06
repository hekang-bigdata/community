package com.hk.community.community.dto;

import lombok.Data;

/**
 * 作者: 何康先生
 * 时间: 2020-03-15 12:29
 * 描述:
 **/
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

}
