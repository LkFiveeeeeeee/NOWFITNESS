package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsRecyclerAdapter;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;

/**
 * Created by a on 2018/12/13.
 */

public class PersonPagePresenter extends BaseMomentsPresenter{
    private PersonPageView personPageView;

    public PersonPagePresenter(Context context, MomentsMethod momentsMethod,PersonPageView personPageView){
       super(momentsMethod, context);
       this.personPageView = personPageView;
    }
    public void intiView(){
        personPageView.initView();
    }

    public void getUserMoments(int userID, int pageNum){
        subscriptions.add(apiRepository.getUserMoments(userID,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::querySuccess,momentsMethod::queryError));
    }

    public void getUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::queryInfoSuccess,momentsMethod::queryError)
        );

    }

    public void addMomentsList(List<MomentsModel> momentsModelList){
        for(MomentsModel e:momentsModelList)
            pMomentsLab.add(e);
         momentsRecyclerAdapter.resetMomentsModelsList(pMomentsLab);
         momentsRecyclerAdapter.notifyDataSetChanged();
    }
    public void deleteMoments(int position){
        MomentsModel momentsDel = pMomentsLab.get(position);
        pMomentsLab.remove(position);
        momentsRecyclerAdapter.notifyItemRemoved(position);
        momentsRecyclerAdapter.notifyItemRangeChanged(position,pMomentsLab.size()-position);
    }
    public void jumpToMomentsDetail(MomentsModel momentsModel,int position){
        Intent intent = new Intent();
        intent.putExtra("moments",momentsModel);
        intent.setClass(mContext, MomentsDetailView.class);
        personPageView.startActivityForResult(intent,1001);
    }

}
