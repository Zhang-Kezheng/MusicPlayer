package com.example.musicplayer.pojo;


import java.io.Serializable;
import java.sql.Date;

/**
 * @author 章可政
 * @date 2020/10/16 14:35
 */
public class Information implements Serializable {
        private Integer id;
        private String userName;
        private String loginName;
        private String password;
        private String email;
        private String phoneNum;
        private String address;
        private Date birthday;
        private Date createTime;
        private String headImage;

        public Information(Integer id, String userName, String loginName, String password, String email, String phoneNum, String address, Date birthday, Date createTime, String headImage) {
                this.id = id;
                this.userName = userName;
                this.loginName = loginName;
                this.password = password;
                this.email = email;
                this.phoneNum = phoneNum;
                this.address = address;
                this.birthday = birthday;
                this.createTime = createTime;
                this.headImage = headImage;
        }

        public Information() {
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getLoginName() {
                return loginName;
        }

        public void setLoginName(String loginName) {
                this.loginName = loginName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhoneNum() {
                return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public Date getBirthday() {
                return birthday;
        }

        public void setBirthday(Date birthday) {
                this.birthday = birthday;
        }

        public Date getCreateTime() {
                return createTime;
        }

        public void setCreateTime(Date createTime) {
                this.createTime = createTime;
        }

        public String getHeadImage() {
                return headImage;
        }

        public void setHeadImage(String headImage) {
                this.headImage = headImage;
        }
}
