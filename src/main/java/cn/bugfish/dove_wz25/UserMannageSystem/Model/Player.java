package cn.bugfish.dove_wz25.UserMannageSystem.Model;

import java.sql.Timestamp;

public class Player {
    private int id;
    private String userid;
    private String nickname;
    private int point;
    private int catfood;
    private int catfoodmutiply;
    private int exp;
    private int expmutiply;
    private int level;
    private int killnum;
    private int mvptime;
    private String mvpmusic;
    private String chenghao;
    private String chenghaocolor;
    private String admin;
    private Timestamp overtime;
    private String manrenjinfu;
    private String jishayinxiao;
    private String jinfuguangbo;
    private String youxian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getCatfood() {
        return catfood;
    }

    public void setCatfood(int catfood) {
        this.catfood = catfood;
    }

    public int getCatfoodmutiply() {
        return catfoodmutiply;
    }

    public void setCatfoodmutiply(int catfoodmultiply) {
        this.catfoodmutiply = catfoodmultiply;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExpmutiply() {
        return expmutiply;
    }

    public void setExpmutiply(int expmutiply) {
        this.expmutiply = expmutiply;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getKillnum() {
        return killnum;
    }

    public void setKillnum(int killnum) {
        this.killnum = killnum;
    }

    public int getMvptime() {
        return mvptime;
    }

    public void setMvptime(int mvptime) {
        this.mvptime = mvptime;
    }

    public String getMvpmusic() {
        return mvpmusic;
    }

    public void setMvpmusic(String mvpmusic) {
        this.mvpmusic = mvpmusic;
    }

    public String getChenghao() {
        return chenghao;
    }

    public void setChenghao(String chenghao) {
        this.chenghao = chenghao;
    }

    public String getChenghaocolor() {
        return chenghaocolor;
    }

    public void setChenghaocolor(String chenghaocolor) {
        this.chenghaocolor = chenghaocolor;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Timestamp getOvertime() {
        return overtime;
    }

    public void setOvertime(Timestamp overtime) {
        this.overtime = overtime;
    }

    public String getManrenjinfu() {
        return manrenjinfu;
    }

    public void setManrenjinfu(String manrenjinfu) {
        this.manrenjinfu = manrenjinfu;
    }

    public String getJishayinxiao() {
        return jishayinxiao;
    }

    public void setJishayinxiao(String jishayinxiao) {
        this.jishayinxiao = jishayinxiao;
    }

    public String getJinfuguangbo() {
        return jinfuguangbo;
    }

    public void setJinfuguangbo(String jinfuguangbo) {
        this.jinfuguangbo = jinfuguangbo;
    }

    public String getYouxian() {
        return youxian;
    }

    public void setYouxian(String youxian) {
        this.youxian = youxian;
    }
}
