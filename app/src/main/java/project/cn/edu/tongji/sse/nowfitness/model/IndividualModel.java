package project.cn.edu.tongji.sse.nowfitness.model;

public class IndividualModel {
    String imageUrl;
    String userName;
    String sex;
    boolean isFollowed;

    public IndividualModel(String imageUrl, String userName, String sex, boolean isFollowed){
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.sex = sex;
        this.isFollowed = isFollowed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }


}
