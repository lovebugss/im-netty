package com.itrjp.im.api.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/***
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/21 21:51
 */
@Data
public class InitParam {

    @NotBlank(message = "频道ID不能为空")
    private String channelId;
    @NotBlank(message = "用户ID不能为空")
    private String userId;
}
