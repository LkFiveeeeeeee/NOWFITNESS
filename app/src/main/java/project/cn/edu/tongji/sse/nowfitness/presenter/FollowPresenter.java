package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.FollowMethod;

public class FollowPresenter extends BasePresenter {
    private FollowMethod followMethod;

    public FollowPresenter(FollowMethod followMethod){
        this.followMethod = followMethod;
    }

    public void postFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.postFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followMethod::postSuccess,followMethod::sendError)
        );
    }

    public void deleteFollowInfo(int userId,int followId){
        subscriptions.add(apiRepository.deleteFollowInfo(userId,followId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followMethod::deleteSuccess,followMethod::sendError)
        );
    }
}
