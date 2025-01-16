package cn.bugfish.dove_wz25.UserMannageSystem.Beans;

import java.io.Serializable;

public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String steam64id;
    private String email;
    private String qqnumber;
    private String password;

    public String getSteam64id() {
        return steam64id;
    }

    public void setSteam64id(String steam64id) {
        this.steam64id = steam64id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQqnumber() {
        return qqnumber;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBean() {}

    // 有参构造器
    public UserBean(String steam64id, String email,String qqnumber, String password) {
        this.steam64id = steam64id;
        this.email = email;
        this.qqnumber = qqnumber;
        this.password = password;
    }

}
