package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsCommentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.CommentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;

/**
 * Created by a on 2018/11/25.
 */

public class MomentsDetailPresenter extends BasePresenter  {
    private MomentsDetailView momentsDetailView;
    private ExpandableListView expandableListView;
    private CommentsListViewAdapter adapter;
    private MomentsCommentsModel commentsModel;
    private List<CommentsDetailModel> commentsList;
    private MomentsModel pMomentsModel;
    private CommentsMethod commentsMethod;
    public MomentsDetailPresenter(MomentsDetailView momentsDetailView, MomentsModel momentsModel,CommentsMethod commentsMethod){
        this.momentsDetailView=momentsDetailView;
        commentsList = new ArrayList<>();
        this.pMomentsModel = momentsModel;
        this.commentsMethod = commentsMethod;
        getAllComments();

    }
    public void initView(){
        momentsDetailView.initView(commentsList);
    }
    public void setExpandableListView(ExpandableListView expandableListView){
        this.expandableListView= expandableListView;
    }

    public void initExpandableList(){
        adapter = new CommentsListViewAdapter(momentsDetailView, commentsList,pMomentsModel);
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
        commentModel.setCommentUserName(UserInfoLab.get().getUserInfoModel().getUserName());
        commentModel.setCommentUserId((int)UserInfoLab.get().getUserInfoModel().getId());
        //makeNewComments(commentModel);
        adapter.addTheCommentData(commentModel);
    }

    //模拟数据
    private void getAllComments(){

        commentsModel = new MomentsCommentsModel();
       // commentsModel.setCommentsList(new ArrayList<>());
        CommentsDetailModel emptyPlacement = new CommentsDetailModel();
        commentsList.add(emptyPlacement);
    }


    public void queryForComments(int momentsId){
        subscriptions.add(apiRepositary.getCommentsInfo(momentsId)
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsMethod::querySuccess,commentsMethod::queryError)
        );

    }

    public void makeNewComments(CommentsDetailModel commentsDetailModel){
        subscriptions.add(apiRepositary.makeNewCommentInfo(commentsDetailModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsMethod::makeCommentsSuccess,commentsMethod::makeCommentsError)
        );
    }

    public void resetCommentsList(List<CommentsDetailModel> commentsDetailModelList){
        List<CommentsDetailModel> tempModelList = new ArrayList<>();
        tempModelList.add(new CommentsDetailModel());
        for(CommentsDetailModel model:commentsDetailModelList){
            tempModelList.add(model);
        }
        commentsList = tempModelList;
        adapter.resetCommentsList(commentsList);
        adapter.notifyDataSetChanged();
        momentsDetailView.initExpandableListView(commentsList);
    }
}
