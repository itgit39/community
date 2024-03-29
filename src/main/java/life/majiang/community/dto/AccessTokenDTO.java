package life.majiang.community.dto;

import lombok.Data;

/**
 * 登录授权参数封装对象
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
