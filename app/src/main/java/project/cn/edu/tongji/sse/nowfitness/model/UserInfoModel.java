package project.cn.edu.tongji.sse.nowfitness.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.data.network.dto.UserInfoDTO;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LK on 2018/11/26.
 */

@Entity
public class UserInfoModel {
    @Id(autoincrement = true)
    private long id;
    private String userName;
    private String nickName;
    private String password;
    private double height;
    private double weight;
    private String sex;
    private int age;
    private String pictureUrl;
    private int followingNum;
    private int fansNum;
    private int momentsNum;
    private String dateCheckString;



    private String salt;

    @Transient
    private List<String> dateCheckList;

    



    @Transient
    private StringAndListConvertMethod convert = new StringAndListConvertMethod();


    public UserInfoModel(UserInfoDTO userInfoDTO){
        this.id = userInfoDTO.getId();
        this.userName = userInfoDTO.getUserName();
        this.password = userInfoDTO.getPassword();
        this.nickName = userInfoDTO.getNickName();
        this.height = userInfoDTO.getHeight();
        this.weight = userInfoDTO.getWeight();
        this.sex = userInfoDTO.getSex();
        this.age = userInfoDTO.getAge();
        this.salt = userInfoDTO.getSalt();
        this.pictureUrl = userInfoDTO.getPicture();
        this.followingNum = userInfoDTO.getFollowing();
        this.fansNum = userInfoDTO.getFans();
        this.momentsNum = userInfoDTO.getMomentsNum();
        this.dateCheckList = userInfoDTO.getDateCheckList();
        this.dateCheckString = convert.convertToDatabaseValue(this.dateCheckList);
    }

    public UserInfoModel(UserInfoModel userInfoModel){
        this.id = userInfoModel.getId();
        this.userName = userInfoModel.getUserName();
        this.password = userInfoModel.getPassword();
        this.nickName = userInfoModel.getNickName();
        this.height = userInfoModel.getHeight();
        this.weight = userInfoModel.getWeight();
        this.sex = userInfoModel.getSex();
        this.age = userInfoModel.getAge();
        this.pictureUrl = userInfoModel.getPictureUrl();
        this.followingNum = userInfoModel.getFollowingNum();
        this.fansNum = userInfoModel.getFansNum();
        this.momentsNum = userInfoModel.getMomentsNum();
        this.dateCheckList = userInfoModel.getDateCheckList();
        this.dateCheckString = convert.convertToDatabaseValue(this.dateCheckList);
    }

    @Generated(hash = 204096738)
    public UserInfoModel(long id, String userName, String nickName, String password, double height,
            double weight, String sex, int age, String pictureUrl, int followingNum, int fansNum,
            int momentsNum, String dateCheckString, String salt) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.age = age;
        this.pictureUrl = pictureUrl;
        this.followingNum = followingNum;
        this.fansNum = fansNum;
        this.momentsNum = momentsNum;
        this.dateCheckString = dateCheckString;
        this.salt = salt;
    }

    @Generated(hash = 886444478)
    public UserInfoModel() {
    }

    

   



    public long getId() {
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

    public void setId(long id) {
        this.id = id;
    }

    public String getDateCheckString() {
        return this.dateCheckString;
    }

    public void setDateCheckString(String dateCheckString) {
        this.dateCheckString = dateCheckString;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
