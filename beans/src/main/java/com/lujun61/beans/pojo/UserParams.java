package com.lujun61.beans.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserParams {

    private String userId;
    private String nickname;
    private String userMobile;
    private String userEmail;
    private String userSex;
    private String userBirth;
    private Date userModtime = new Date();

}
