package project.cn.edu.tongji.sse.nowfitness.presenter;


import android.content.Intent;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsCommentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.CommentsListViewAdapter;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.CommentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;

/**
 * Created by a on 2018/11/25.
 */

public class MomentsDetailPresenter extends BasePresenter  {
    private MomentsDetailView momentsDetailView;
    private ExpandableListView expandableListView;
    private CommentsListViewAdapter adapter;
    private MomentsCommentsModel commentsModel;
    private List<CommentsDetailModel> commentsList;
    public MomentsModel pMomentsModel;
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
        adapter = new CommentsListViewAdapter(momentsDetailView, commentsList,pMomentsModel,this);
        expandableListView.setAdapter(adapter);
        momentsDetailView.initExpandableListView(commentsList);
    }

    public void addReplyData(int childPosition,int groupPosition,String commentContent){
        CommentsReplyModel replyDetailModel= new CommentsReplyModel();
        if (childPosition!=-1) {
            replyDetailModel.setToUserName(commentsList.get(groupPosition).getRepliesList().get(childPosition).getFromUserName());
            replyDetailModel.setToUserId(commentsList.get(groupPosition).getRepliesList().get(childPosition).getToUserId());
        }
        else {
            replyDetailModel.setToUserName(commentsList.get(groupPosition).getCommentUserName());
            replyDetailModel.setToUserId(commentsList.get(groupPosition).getCommentUserId());
        }
        replyDetailModel.setFromUserName(UserInfoLab.get().getUserInfoModel().getUserName());
        replyDetailModel.setCommentId(commentsList.get(groupPosition).getId());
        replyDetailModel.setFromUserId((int)UserInfoLab.get().getUserInfoModel().getId());

        replyDetailModel.setContent(commentContent);
        adapter.addTheReplyData(replyDetailModel,groupPosition);
        //TODO  ADD makeNewReply
        makeNewReply(replyDetailModel);
        commentsList = adapter.getCommentsList();
    }

    private void makeNewReply(CommentsReplyModel commentsReplyModel){
        subscriptions.add(apiRepository.makeReply(commentsReplyModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    //TODO ? 我把ID当做replyId的,不知道是不是,不是直接改一下传的参数就行了,反正都是int类型
    private void deleteReply(int replyId){
        subscriptions.add(apiRepository.deleteReply(replyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void addCommentData(String commentContent){
        CommentsDetailModel commentModel = new CommentsDetailModel();
        commentModel.setContent(commentContent);
        commentModel.setCommentUserName(UserInfoLab.get().getUserInfoModel().getUserName());
        commentModel.setCommentUserId((int)UserInfoLab.get().getUserInfoModel().getId());
        commentModel.setMomentsId(pMomentsModel.getMomentsId());
        commentModel.setCommentUserPhoto(UserInfoLab.get().getUserInfoModel().getPictureUrl());
        commentModel.setCommentUserNickName(UserInfoLab.get().getUserInfoModel().getNickName());
        makeNewComments(commentModel);
        pMomentsModel.setCommentsNum(pMomentsModel.getCommentsNum()+1);
        adapter.addTheCommentData(commentModel);
        commentsList = adapter.getCommentsList();
    }

    //模拟数据
    private void getAllComments(){
        commentsModel = new MomentsCommentsModel();
       // commentsModel.setCommentsList(new ArrayList<>());
        CommentsDetailModel emptyPlacement = new CommentsDetailModel();
        commentsList.add(emptyPlacement);
    }


    public void queryForComments(int momentsId){
        subscriptions.add(apiRepository.getCommentsInfo(momentsId)
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsMethod::querySuccess,commentsMethod::queryError)
        );

    }

    private void makeNewComments(CommentsDetailModel commentsDetailModel){
        subscriptions.add(apiRepository.makeNewCommentInfo(commentsDetailModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
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

    public boolean deleteReply(int groupPos,int childPos){
        deleteReply(commentsList.get(groupPos).getRepliesList().get(childPos).getId());
        if(adapter.deleteReply(groupPos, childPos)){
            //服务端请求删除回复
            //TODO add deleteReply
            //
            return true;
        }else
            return false;
    }

    public void deleteComments(int groupPos){
        subscriptions.add(apiRepository.deleteComment(commentsList.get(groupPos).getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
       adapter.deleteComments(groupPos);
    }
    public void jumpToPersonPage(int id,String personName,String nickName,String personPhoto){
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("name",personName);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.setClass(momentsDetailView, PersonPageView.class);
        momentsDetailView.startActivity(intent);
    }
    public boolean isReplyDeletable(int groupPos,int childPos){
        if (commentsList.get(groupPos).getRepliesList().get(childPos).getFromUserId()
                == (int)UserInfoLab.get().getUserInfoModel().getId()
            ||pMomentsModel.getUserId()==(int)UserInfoLab.get().getUserInfoModel().getId())
            return true;
        else
            return false;
    }
}
