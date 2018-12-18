package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsView;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsPresenter extends BasePresenter{
    private RecyclerView momentsRecyclerView;
    private MomentsView momentsView;
    private List<MomentsModel> pMomentsLab;
    private MomentsMethod momentsMethod;
    private MomentsRecyclerAdapter momentsRecyclerAdapter;

    public void setMomentsRecyerView(RecyclerView recyclerView){
        this.momentsRecyclerView=recyclerView;
    }

    public void initView(){
        momentsView.initView();
    }
    public void likeOrDislike(){

    }
    public MomentsPresenter(MomentsView momentsView, MomentsMethod momentsMethod){
        this.momentsView = momentsView;
        pMomentsLab = new ArrayList<>();
        this.momentsMethod = momentsMethod;
    }

    public void queryForInfo(int userId){
        subscriptions.add(apiRepositary.getStarsInfo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(momentsMethod::querySuccess,momentsMethod::queryError)
        );

    }
    public void setAdapter(){
        //pMomentsLab = momentsModelList;
        momentsRecyclerAdapter = new MomentsRecyclerAdapter(pMomentsLab,this);
        momentsRecyclerView.setAdapter(momentsRecyclerAdapter);
    }

    public void resetMomentsList(List<MomentsModel> momentsModelList){
        pMomentsLab = momentsModelList;
        momentsRecyclerAdapter.resetMomentsModelsList(momentsModelList);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }

    public void jumpToMomentsDetail(MomentsModel momentsModel){
        Intent intent = new Intent();
        intent.putExtra("moments",momentsModel);
        intent.setClass(momentsView.getActivity(), MomentsDetailView.class);
        momentsView.startActivity(intent);
    }




}
