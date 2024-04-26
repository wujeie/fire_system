package com.wzs.fire.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserDto implements Serializable {
    private Integer userId;
    private String username;
    private Integer roleId;
    private String roleName;
    private String createdAt;
    private String email;
}
