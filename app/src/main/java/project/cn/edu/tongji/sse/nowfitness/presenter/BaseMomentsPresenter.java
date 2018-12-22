package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsRecyclerAdapter;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;

/**
 * Created by a on 2018/12/13.
 */

public class BaseMomentsPresenter extends BasePresenter {
    protected MomentsRecyclerAdapter momentsRecyclerAdapter;
    private RecyclerView momentsRecyclerView;
    protected List<MomentsModel> pMomentsLab;
    protected int pages;
    protected int pageSize;
    protected int pageNum;
    protected int totalMoments;
    protected int currentPageSize;
    private Context mContext;
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
    public void setMomentsRecyerView(RecyclerView recyclerView){
        this.momentsRecyclerView=recyclerView;
    }
    public void setAdapter(){
        //pMomentsLab = momentsModelList;
        momentsRecyclerAdapter = new MomentsRecyclerAdapter(pMomentsLab,this);
        momentsRecyclerView.setAdapter(momentsRecyclerAdapter);
    }
    public void jumpToMomentsDetail(MomentsModel momentsModel){
        Intent intent = new Intent();
        intent.putExtra("moments",momentsModel);
        intent.setClass(mContext, MomentsDetailView.class);
        mContext.startActivity(intent);
    }
    public void jumpToPersonPage(int id,String userName,String nickName,String personPhoto){
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.putExtra("userName",userName);
        intent.setClass(mContext, PersonPageView.class);
        mContext.startActivity(intent);
    }

    public void setAdapterStates(int states){
        momentsRecyclerAdapter.setViewStatus(states);
        momentsRecyclerAdapter.notifyItemChanged(0);
    }

    public int getNextPage(){
            if(pages == pageNum)
                return pageNum+1;
            else
                return pageNum+1;
    }
    public int getTotal(){
        return totalMoments;
    }


}
