package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;

/**
 * Created by a on 2018/11/26.
 */

public class CommentsDetailModel {

        private int id;
        private int momentsId;
        private String content;
        private int commentUserId;
        private String commentTime;
        private String commentUserPhoto;
        private String commentUserName;
        private List<CommentsReplyModel> repliesList;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMomentsId() {
            return momentsId;
        }

        public void setMomentsId(int momentsId) {
            this.momentsId = momentsId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getCommentUserPhoto() {
            return commentUserPhoto;
        }

        public void setCommentUserPhoto(String commentUserPhoto) {
            this.commentUserPhoto = commentUserPhoto;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public List<CommentsReplyModel> getRepliesList() {
            return repliesList;
        }

        public void setRepliesList(List<CommentsReplyModel> repliesList) {
            this.repliesList = repliesList;
        }

}
