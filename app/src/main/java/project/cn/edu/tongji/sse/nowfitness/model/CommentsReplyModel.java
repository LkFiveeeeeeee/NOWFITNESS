package project.cn.edu.tongji.sse.nowfitness.model;

import project.cn.edu.tongji.sse.nowfitness.data.network.dto.CommentsDTO;

/**
 * Created by a on 2018/11/26.
 */

public class CommentsReplyModel {
    private int id;

    private int commentId;

    private int fromUserId;

    private int toUserId;

    private String replyType;

    private String content;

    private String replyTime;

    private int replyId;

    private String fromUserName;

    private String toUserName;

    private String fromUserNickName;

    private String toUserNickName;

    public CommentsReplyModel(CommentsDTO.CommentsListBean.RepliesListBean repliesListBean){
        this.id = repliesListBean.getId();
        this.commentId = repliesListBean.getCommentId();
        this.fromUserId = repliesListBean.getFromUserId();
        this.toUserId = repliesListBean.getToUserId();
        this.replyType = repliesListBean.getReplyType();
        this.replyTime = repliesListBean.getReplyTime();
        this.content = repliesListBean.getContent();
        this.replyId = repliesListBean.getReplyId();
        this.fromUserName = repliesListBean.getFromUserName();
        this.toUserName = repliesListBean.getToUserName();
        this.fromUserNickName = repliesListBean.getFromUserNickName();
        this.toUserNickName = repliesListBean.getToUserNickName();
    }
    public CommentsReplyModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserNickName() {
        return fromUserNickName;
    }

    public void setFromUserNickName(String fromUserNickName) {
        this.fromUserNickName = fromUserNickName;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

}
