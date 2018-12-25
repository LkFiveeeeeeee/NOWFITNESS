package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.support.annotation.MainThread;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayView;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayViewMethod;

public class DisplayPresenter extends BasePresenter{
    private DisplayView displayView;
    private DisplayViewMethod displayViewMethod;

    public DisplayPresenter(DisplayView displayView, DisplayViewMethod displayViewMethod){
        this.displayView = displayView;
        this.displayViewMethod = displayViewMethod;
    }

    public void initView(){
        displayView.initView();
    }

    public void queryForFollowingOrFansInfo(int type){
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        if(type == DisplayView.STARS_TYPE){
            subscriptions.add(apiRepository.getFollowingInfo((int) userInfoModel.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(displayViewMethod::queryForSuccess,displayViewMethod::queryError)
            );
        }else if(type == DisplayView.FANS_TYPE){
            subscriptions.add(apiRepository.getFansInfo((int) userInfoModel.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(displayViewMethod::queryForSuccess,displayViewMethod::queryError)
            );
        }
    }

    public void postFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.postFollowInfo(userId,followId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        );
    }

    public void deleteFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.postFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }


}
