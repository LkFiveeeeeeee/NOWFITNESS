package project.cn.edu.tongji.sse.nowfitness.model;

import java.util.List;

/**
 * Created by a on 2018/11/26.
 */

public class MomentsCommentsModel {
    private int commentsNum;
    private List<CommentsDetailModel> commentsDetailModelList;

    public MomentsCommentsModel(){

    }
    private List<CommentsDetailModel> commentsList;

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public List<CommentsDetailModel> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<CommentsDetailModel> commentsList) {
        this.commentsList = commentsList;
    }

}
