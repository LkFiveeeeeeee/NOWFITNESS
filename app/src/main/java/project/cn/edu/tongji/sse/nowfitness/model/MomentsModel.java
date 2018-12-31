package project.cn.edu.tongji.sse.nowfitness.model;

import java.io.Serializable;

import project.cn.edu.tongji.sse.nowfitness.data.network.dto.MomentsDTO;

public class MomentsModel implements Serializable {
    private int momentsId;
    private int userId;
    private String userPhoto;
    private String userName;
    private String nickName;
    private String content;
    private String releaseTime;
    private String image;
    private int likes;
    private int commentsNum;
    private boolean liked;

    public MomentsModel(MomentsDTO.ListBean listBean){
        momentsId = listBean.getMomentsId();
        userId =listBean.getUserId();
        userPhoto = listBean.getUserPhoto();
        userName = listBean.getUserName();
        content = listBean.getContent();
        releaseTime = listBean.getReleaseTime();
        image = listBean.getImage();
        likes = listBean.getLikes();
        commentsNum = listBean.getCommentsNum();
        liked = listBean.isLiked();
        nickName = listBean.getNickName();
    }

    public int getMomentsId() {
        return momentsId;
    }

    public void setMomentsId(int momentsId) {
        this.momentsId = momentsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
