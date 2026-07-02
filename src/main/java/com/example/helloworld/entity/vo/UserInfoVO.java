package com.example.helloworld.entity.vo;

import com.example.helloworld.entity.User;

/**
 * 登录响应中的 info 字段。
 * 前端约定字段：username / nickName / avatar / roleName。
 * 单独封装是为了不把 password 等敏感字段直接吐给前端。
 */
public class UserInfoVO {

    private String username;
    private String nickName;
    private String avatar;
    private String roleName;

    public UserInfoVO() {}

    public static UserInfoVO from(User user) {
        UserInfoVO vo = new UserInfoVO();
        vo.username = user.getName();
        vo.nickName = user.getNickName();
        vo.avatar = user.getAvatar();
        vo.roleName = user.getRoleName();
        return vo;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
