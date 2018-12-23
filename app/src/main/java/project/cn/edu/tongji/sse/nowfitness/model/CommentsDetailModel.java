package project.cn.edu.tongji.sse.nowfitness.model;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;

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
        private String commentUserNickName;
        private List<CommentsReplyModel> repliesList;

    public CommentsDetailModel(CommentsDTO.CommentsListBean listBean){
        momentsId = listBean.getMomentsId();
        id = listBean.getId();
        momentsId  =listBean.getMomentsId();
        content = listBean.getContent();
        commentUserId = listBean.getCommentUserId();
        commentTime =listBean.getCommentTime();
        commentUserPhoto = listBean.getCommentUserPhoto();
        commentUserName =listBean.getCommentUserName();
        commentUserNickName = listBean.getCommentUserNickName();
        repliesList = new ArrayList<>();
        for(CommentsDTO.CommentsListBean.RepliesListBean reply:listBean.getRepliesList()){
            repliesList.add(new CommentsReplyModel(reply));
        }
    }


        public CommentsDetailModel() {

        }

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
        public String getCommentUserNickName(){
            return commentUserNickName;
        }

}
