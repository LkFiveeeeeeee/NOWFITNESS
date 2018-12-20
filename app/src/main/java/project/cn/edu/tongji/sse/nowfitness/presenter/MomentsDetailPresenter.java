package project.cn.edu.tongji.sse.nowfitness.presenter;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import android.util.Log;
import android.widget.ExpandableListView;

import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsCommentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
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
        adapter = new CommentsListViewAdapter(momentsDetailView, commentsList,pMomentsModel,this);
        expandableListView.setAdapter(adapter);
        momentsDetailView.initExpandableListView(commentsList);
    }

    public void addReplyData(int childPosition,int groupPosition,String commentContent){
        CommentsReplyModel replyDetailModel= new CommentsReplyModel();
        if (childPosition!=-1)
            replyDetailModel.setToUserName(commentsList.get(groupPosition).getRepliesList().get(childPosition).getFromUserName());
        else
            replyDetailModel.setToUserName(commentsList.get(groupPosition).getCommentUserName());
        replyDetailModel.setContent(commentContent);
        replyDetailModel.setFromUserName("asdfasdf");//UserInfoLab.get().getUserInfoModel().getUserName());
        adapter.addTheReplyData(replyDetailModel,groupPosition);
        commentsList = adapter.getCommentsList();
    }
    public void addCommentData(String commentContent){
        CommentsDetailModel commentModel = new CommentsDetailModel();
        commentModel.setContent(commentContent);
        commentModel.setCommentUserName("rqfadsf");//UserInfoLab.get().getUserInfoModel().getUserName());
        commentModel.setCommentUserId(1);//(int)UserInfoLab.get().getUserInfoModel().getId());
        makeNewComments(commentModel);
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
        subscriptions.add(apiRepositary.getCommentsInfo(momentsId)
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsMethod::querySuccess,commentsMethod::queryError)
        );

    }

    public void makeNewComments(CommentsDetailModel commentsDetailModel){
        JSONObject postData = new JSONObject();
        try{
            postData.put("momentsId",commentsDetailModel.getMomentsId());
            postData.put("content",commentsDetailModel.getContent());
            postData.put("commentUserId",commentsDetailModel.getCommentUserId());
        }catch (JSONException e) {
            e.printStackTrace();
            Log.d("JsonException", "makeNewComments: ");
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type,application/json"),
                postData.toString());

        subscriptions.add(apiRepositary.makeNewCommentInfo(requestBody)
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

    public boolean deleteReply(int groupPos,int childPos){
        if(adapter.deleteReply(groupPos,childPos)==true){
            //服务端请求删除回复
            return true;
        }else
            return false;
    }

    public boolean deleteComments(int groupPos){
        if(adapter.deleteComments(groupPos)){
            //请求服务端删除评论
            return true;
        }else
            return false;
    }
    public void jumpToPersonPage(int id,String personName,String personPhoto){
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("name",personName);
        intent.putExtra("photo",personPhoto);
        intent.setClass(momentsDetailView, PersonPageView.class);
        momentsDetailView.startActivity(intent);
    }
}
