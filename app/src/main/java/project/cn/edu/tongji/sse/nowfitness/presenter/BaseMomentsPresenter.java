package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.tencent.connect.share.QzoneShare;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsRecyclerAdapter;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.ToPersonPageView;

public class BaseMomentsPresenter extends BasePresenter implements ToPersonPageView {
    protected MomentsRecyclerAdapter momentsRecyclerAdapter;
    private RecyclerView momentsRecyclerView;
    protected List<MomentsModel> pMomentsLab;
    protected int pages;
    protected int pageSize;
    protected int pageNum;
    protected int totalMoments;
    protected int currentPageSize;
    protected Context mContext;
    protected MomentsMethod momentsMethod;

    public void  setPageNum(int pageNum){
        this.pageNum =pageNum;
    }
    public void setPages(int pages){
        this.pages = pages;
    }
    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }
    public void setTotalMoments(int totalMoments){
        this.totalMoments = totalMoments;
    }

    public BaseMomentsPresenter(MomentsMethod momentsMethod,Context context){
        this.momentsMethod = momentsMethod;
        this.mContext = context;
        pMomentsLab = new ArrayList<>();
        pageNum = 0;
        pages = 0;
        totalMoments = 0;
        currentPageSize = 0;
        pageSize = 10;
    }

    public Context getContext(){
        return mContext;
    }

    public void setMomentsRecyclerView(RecyclerView recyclerView){
        this.momentsRecyclerView=recyclerView;
    }

    public void setAdapter(){
        momentsRecyclerAdapter = new MomentsRecyclerAdapter(pMomentsLab,this);
        momentsRecyclerView.setAdapter(momentsRecyclerAdapter);
    }

    public void setAdapterStates(int states){
        momentsRecyclerAdapter.setViewStatus(states);
        momentsRecyclerAdapter.notifyItemChanged(0);
    }

     /**
      * @Author: omf
      * @Description: 获得下一个动态的页码
      * @Param
      * @Return: int
      */
    public int getNextPage(){
        return pageNum + 1;
    }

    public int getTotal(){
        return totalMoments;
    }

    public void postLikeInfo(int momentsId,int likesId){
        subscriptions.add(apiRepository.postLikeInfo(momentsId,likesId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::connectSuccess,momentsMethod::connectError)
        );
    }

    public void deleteLikeInfo(int momentsId,int likesId){
        subscriptions.add(apiRepository.delLikeInfo(momentsId,likesId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::connectSuccess,momentsMethod::connectError)
        );
    }

    public void postFollowingInfo(int userId,int followId){
        subscriptions.add(apiRepository.postFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::connectSuccess,momentsMethod::connectError)
        );
    }
    public void deleteFollowingInfo(int userId,int followId){
        subscriptions.add(apiRepository.deleteFollowInfo(userId, followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::connectSuccess,momentsMethod::connectError)
        );
    }

    public void resetMomentsList(List<MomentsModel> momentsModelList){
        pMomentsLab = momentsModelList;
        momentsRecyclerAdapter.resetMomentsModelsList(momentsModelList);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }

    public void notifyCommentsNumChange(int position,int commentsNum){
        pMomentsLab.get(position).setCommentsNum(commentsNum);
        momentsRecyclerAdapter.notifyItemChanged(position);
    }

    public void jumpToMomentsDetail(MomentsModel momentsModel,int position) {
        //override in subclass
    }

     /**
      * @Author: omf
      * @Description: 将要分享的到QZONE的信息打包
      * @Param title
      * @Param summary
      * @Param contentUrl
      * @Param imageUrl
      * @Return: android.os.Bundle
      */
    public static Bundle setShareContent(String title,String summary,String contentUrl,String imageUrl) {
        Bundle params;
        params = new Bundle();
        if(title==null||title.equals("")) {
            title = "来自NOWFITNESS应用";
        }
        if(summary.equals("")) {
            summary = "欢迎使用NOWFITNESS";
        }
        if(contentUrl.equals("http://47.107.167.12:8080/api/image/null")) {
            contentUrl = imageUrl;
        }
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);// 摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,contentUrl);// 内容地址
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(imageUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,imgUrlList);// 图片地址
        // 分享操作要在主线程中完成
        return params;
    }

    public void shareToQzone(String title,String summary,String contentUrl,String imageUrl){
        // override in subclass
    }

    @Override
    public void jumpToPersonPage(int id,String userName,String nickName,String personPhoto){
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.putExtra("userName",userName);
        intent.setClass(mContext, PersonPageView.class);
        mContext.startActivity(intent);
    }
}
