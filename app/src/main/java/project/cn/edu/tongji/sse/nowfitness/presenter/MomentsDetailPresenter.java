package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsCommentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;

/**
 * Created by a on 2018/11/25.
 */

public class MomentsDetailPresenter extends BasePresenter{
    private MomentsDetailView momentsDetailView;
    private ExpandableListView expandableListView;
    private CommentsListViewAdapter adapter;
    private MomentsCommentsModel commentsModel;
    private List<CommentsDetailModel> commentsList;
    public MomentsDetailPresenter(MomentsDetailView momentsDetailView){
        this.momentsDetailView=momentsDetailView;
        commentsList = new ArrayList<>();
        getAllComments();

    }
    public void initView(){
        momentsDetailView.initView(commentsList);
    }
    public void setExpandableListView(ExpandableListView expandableListView){
        this.expandableListView= expandableListView;
    }

    public void initExpandableList(){
        adapter = new CommentsListViewAdapter(momentsDetailView, commentsList);
        expandableListView.setAdapter(adapter);
        momentsDetailView.initExpandableListView(commentsList);
    }

    public void addReplyData(int groupPosition,String commentContent){
        CommentsReplyModel replyDetailModel= new CommentsReplyModel();
        replyDetailModel.setContent(commentContent);
        adapter.addTheReplyData(replyDetailModel,groupPosition);
    }
    public void addCommentData(String commentContent){
        CommentsDetailModel commentModel = new CommentsDetailModel();
        commentModel.setContent(commentContent);
        adapter.addTheCommentData(commentModel);
    }

    //模拟数据
    private void getAllComments(){

        commentsModel = new MomentsCommentsModel();
       // commentsModel.setCommentsList(new ArrayList<>());
        CommentsDetailModel emptyPlacement = new CommentsDetailModel();
        commentsList.add(emptyPlacement);
        for(int i=0;i<5;i++){
            CommentsDetailModel commentsDetailModel = new CommentsDetailModel();
            commentsDetailModel.setCommentTime("2018-11-"+i+" 14:28");
            commentsDetailModel.setCommentUserName("bbb"+i);
            commentsDetailModel.setCommentUserPhoto("");
            commentsDetailModel.setContent("如果我是djdjdjdjdjdj"+i);
           commentsList.add(commentsDetailModel);
        }

    }
}
