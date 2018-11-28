package project.cn.edu.tongji.sse.nowfitness.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

public class IndividualModel {

    //存储另一张表的主键
    private Long keyID;
    private String imageUrl;
    private String userName;
    private String sex;
    private boolean isFollowed;
    public IndividualModel(Long keyID,String imageUrl,
            String userName, String sex, boolean isFollowed) {
        this.keyID = keyID;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.sex = sex;
        this.isFollowed = isFollowed;
    }

    public IndividualModel() {
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public boolean getIsFollowed() {
        return this.isFollowed;
    }
    public void setIsFollowed(boolean isFollowed) {
        this.isFollowed = isFollowed;
    }
    public Long getKeyID() {
        return keyID;
    }

    public void setKeyID(Long keyID) {
        this.keyID = keyID;
    }



}
