package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;

public class UserInfoModel {
    private int id;
    private String userName;
    private String passWord;
    private double height;
    private double weight;
    private String sex;
    private int age;
    private String pictureUrl;
    private int followingNum;
    private int fansNum;
    private int momentsNum;

    public UserInfoModel(int id, String userName, String passWord, double height,
                         double weight, String sex, int age, String pictureUrl,
                         int followingNum, int fansNum, int momentsNum, List<String> dateCheckList) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.age = age;
        this.pictureUrl = pictureUrl;
        this.followingNum = followingNum;
        this.fansNum = fansNum;
        this.momentsNum = momentsNum;
        this.dateCheckList = dateCheckList;
    }

    public UserInfoModel(UserInfoDTO userInfoDTO){
        this.id = userInfoDTO.getId();
        this.userName = userInfoDTO.getUserName();
        this.passWord = userInfoDTO.getPassword();
        this.height = userInfoDTO.getHeight();
        this.weight = userInfoDTO.getWeight();
        this.sex = userInfoDTO.getSex();
        this.age = userInfoDTO.getAge();
        this.pictureUrl = userInfoDTO.getPicture();
        this.followingNum = userInfoDTO.getFollowing();
        this.fansNum = userInfoDTO.getFans();
        this.momentsNum = userInfoDTO.getMomentsNum();
        this.dateCheckList = userInfoDTO.getDateCheckList();
    }



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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getMomentsNum() {
        return momentsNum;
    }

    public void setMomentsNum(int momentsNum) {
        this.momentsNum = momentsNum;
    }

    public List<String> getDateCheckList() {
        return dateCheckList;
    }

    public void setDateCheckList(List<String> dateCheckList) {
        this.dateCheckList = dateCheckList;
    }

    private List<String> dateCheckList;
}
