package project.cn.edu.tongji.sse.nowfitness.data.network.DTO;

import java.util.List;

public class MomentsDTO {
    /**
     * momentsNum : 4
     * momentsModelsList : [{"momentsId":11,"userId":5,"userPhoto":null,"userName":"bbb","content":"wwww","releaseTime":"2018-11-21T07:52:25.000+0000","image":null,"likes":0,"commentsNum":0,"liked":false},{"momentsId":10,"userId":4,"userPhoto":null,"userName":"liuke","content":"xixixi","releaseTime":"2018-11-21T07:52:16.000+0000","image":null,"likes":3,"commentsNum":3,"liked":true},{"momentsId":9,"userId":3,"userPhoto":"aecb5cd433f643aba18aee7557558ef3.JPG","userName":"omf","content":"hhh","releaseTime":"2018-11-21T07:52:02.000+0000","image":null,"likes":0,"commentsNum":0,"liked":false},{"momentsId":8,"userId":2,"userPhoto":"63bf800f22b64c53a1391a9e782d9112.JPG","userName":"huitaaa","content":"hhh","releaseTime":"2018-11-21T07:51:45.000+0000","image":null,"likes":0,"commentsNum":0,"liked":false}]
     */

    private int momentsNum;
    private List<MomentsModelsListBean> momentsModelsList;

    public int getMomentsNum() {
        return momentsNum;
    }

    public void setMomentsNum(int momentsNum) {
        this.momentsNum = momentsNum;
    }

    public List<MomentsModelsListBean> getMomentsModelsList() {
        return momentsModelsList;
    }

    public void setMomentsModelsList(List<MomentsModelsListBean> momentsModelsList) {
        this.momentsModelsList = momentsModelsList;
    }

    public static class MomentsModelsListBean {
        /**
         * momentsId : 11
         * userId : 5
         * userPhoto : null
         * userName : bbb
         * content : wwww
         * releaseTime : 2018-11-21T07:52:25.000+0000
         * image : null
         * likes : 0
         * commentsNum : 0
         * liked : false
         */

        private int momentsId;
        private int userId;
        private String userPhoto;
        private String userName;
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
}
