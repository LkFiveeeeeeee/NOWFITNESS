package project.cn.edu.tongji.sse.nowfitness.data.network.dto;

/**
 * Create by LK on 2018/1/2.
 */

public class MomentsListDTO {
    /**
     * momentsId : 11
     * userId : 5
     * userPhoto : http://localhost:8080/image/4604f7044a7a4fccbd942e50d7dc23ef.jpg
     * userName : bbb
     * nickName :
     * content : wwww
     * releaseTime : 2018-11-21T07:52:25.000+0800
     * image : http://localhost:8080/image/null
     * likes : 0
     * commentsNum : 0
     * likesName : []
     * liked : false
     */

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
