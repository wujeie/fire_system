package com.wzs.fire.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserVo {
    private Integer userId;
    private String username;
    private Integer roleId;
    private String roleName;
    private String createdAt;
    private String email;
}
