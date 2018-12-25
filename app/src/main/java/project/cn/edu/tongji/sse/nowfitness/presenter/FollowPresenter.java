package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FollowPresenter extends BasePresenter {
    public void postFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.postFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void deleteFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.deleteFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }
}
