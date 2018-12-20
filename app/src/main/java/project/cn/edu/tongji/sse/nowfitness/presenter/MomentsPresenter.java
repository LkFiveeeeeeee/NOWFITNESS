package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

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
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsView;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsPresenter extends BaseMomentsPresenter{
    private MomentsView momentsView;
    //private List<MomentsModel> pMomentsLab;
   // private MomentsMethod momentsMethod;
    //private MomentsRecyclerAdapter momentsRecyclerAdapter;


    public void initView(){
        momentsView.initView();
    }
    public void likeOrDislike(){

    }
    public MomentsPresenter(MomentsView momentsView, MomentsMethod momentsMethod){
        super(momentsMethod,momentsView.getActivity());
        this.momentsView = momentsView;
        pMomentsLab = new ArrayList<>();
        this.momentsMethod = momentsMethod;
    }

    public void queryForInfo(int userId){

        /*subscriptions.add(apiRepositary.getStarsInfo(userId,1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(momentsMethod::querySuccess,momentsMethod::queryError)
        );*/

        String momentsListJson=" [\n" +
                "        {\n" +
                "            \"momentsId\": 1,\n" +
                "            \"userId\": 1,\n" +
                "            \"userPhoto\": \"c2bb788195ac4082ac2ffa5da70aefcf.jpg\",\n" +
                "            \"userName\": \"huitaa\",\n" +
                "            \"content\": \"my first moments\",\n" +
                "            \"releaseTime\": \"2018-11-20T16:47:37.000+0800\",\n" +
                "            \"image\": \"\",\n" +
                "            \"likes\": 3,\n" +
                "            \"commentsNum\": 0,\n" +
                "            \"likesName\": [\n" +
                "                \"huitaa\",\n" +
                "                \"bbb\",\n" +
                "                \"omf\"\n" +
                "            ],\n" +
                "            \"liked\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"momentsId\": 4,\n" +
                "            \"userId\": 1,\n" +
                "            \"userPhoto\": \"c2bb788195ac4082ac2ffa5da70aefcf.jpg\",\n" +
                "            \"userName\": \"huitaa\",\n" +
                "            \"content\": \"1my first moments\",\n" +
                "            \"releaseTime\": \"2018-11-20T16:49:40.000+0800\",\n" +
                "            \"image\": null,\n" +
                "            \"likes\": 0,\n" +
                "            \"commentsNum\": 0,\n" +
                "            \"likesName\": [],\n" +
                "            \"liked\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"momentsId\": 5,\n" +
                "            \"userId\": 1,\n" +
                "            \"userPhoto\": \"c2bb788195ac4082ac2ffa5da70aefcf.jpg\",\n" +
                "            \"userName\": \"huitaa\",\n" +
                "            \"content\": \"2my first moments\",\n" +
                "            \"releaseTime\": \"2018-11-20T16:49:47.000+0800\",\n" +
                "            \"image\": null,\n" +
                "            \"likes\": 0,\n" +
                "            \"commentsNum\": 0,\n" +
                "            \"likesName\": [],\n" +
                "            \"liked\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"momentsId\": 6,\n" +
                "            \"userId\": 1,\n" +
                "            \"userPhoto\": \"c2bb788195ac4082ac2ffa5da70aefcf.jpg\",\n" +
                "            \"userName\": \"huitaa\",\n" +
                "            \"content\": \"3my first moments\",\n" +
                "            \"releaseTime\": \"2018-11-20T16:49:56.000+0800\",\n" +
                "            \"image\": null,\n" +
                "            \"likes\": 0,\n" +
                "            \"commentsNum\": 0,\n" +
                "            \"likesName\": [],\n" +
                "            \"liked\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"momentsId\": 7,\n" +
                "            \"userId\": 1,\n" +
                "            \"userPhoto\": \"c2bb788195ac4082ac2ffa5da70aefcf.jpg\",\n" +
                "            \"userName\": \"huitaa\",\n" +
                "            \"content\": \"3my first moments\",\n" +
                "            \"releaseTime\": \"2018-11-21T07:51:32.000+0800\",\n" +
                "            \"image\": null,\n" +
                "            \"likes\": 0,\n" +
                "            \"commentsNum\": 0,\n" +
                "            \"likesName\": [],\n" +
                "            \"liked\": false\n" +
                "        }\n" +
                "    ]";
        Gson gson ;
        GsonBuilder builder;
        builder=new GsonBuilder();
        gson=builder.create();
        List<MomentsModel> mList=new ArrayList<MomentsModel>();
        Type type1=new TypeToken<List<MomentsModel>>(){}.getType();
        mList=gson.fromJson(momentsListJson, type1);
        java.util.Random r= new java.util.Random();
        if(r.nextInt()%4==0) {
            if (mList.size() > 0) {
                setAdapterStates(MomentsRecyclerAdapter.NORMAL);
                addMomentsList(mList);
            }
        }
        else {
            if(pMomentsLab.size()==0) {
                if (r.nextInt() % 2 == 0)
                    setAdapterStates(MomentsRecyclerAdapter.NO_NETWORK);
                else
                    setAdapterStates(MomentsRecyclerAdapter.NO_CONTENT);
            }
        }

    }

    public void resetMomentsList(List<MomentsModel> momentsModelList){
        pMomentsLab = momentsModelList;
        momentsRecyclerAdapter.resetMomentsModelsList(momentsModelList);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }

    public void addMomentsList(List<MomentsModel> momentsModelList){
        for(MomentsModel e:momentsModelList)
            pMomentsLab.add(e);
        momentsRecyclerAdapter.resetMomentsModelsList(pMomentsLab);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }
}
