package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

import java.util.List;

public class UserInfoDTO {


    /**
     * id : 1
     * userName : huitaa
     * password : 123456
     * height : 175.0
     * weight : 69.1
     * sex : 男
     * age : 10
     * picture : c41e9aee50d04541b4c9f19c7e13134b.JPG
     * following : 4
     * fans : 1
     * momentsNum : 5
     * dailyCheckList : ["2018-11-26","2018-11-25","2018-11-24","2018-11-23","2018-11-17"]
     */

    private int id;
    private String userName;
    private String password;



    private String nickName;
    private double height;
    private double weight;
    private String sex;
    private int age;
    private String picture;
    private int following;
    private int fans;
    private int momentsNum;
    private List<String> dailyCheckList;



    private String salt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getMomentsNum() {
        return momentsNum;
    }

    public void setMomentsNum(int momentsNum) {
        this.momentsNum = momentsNum;
    }

    public void setDailyCheckList(List<String> dailyCheckList) {
        this.dailyCheckList = dailyCheckList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<String> getDateCheckList() {
        return dailyCheckList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
