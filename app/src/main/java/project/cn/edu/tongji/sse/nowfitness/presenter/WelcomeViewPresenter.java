package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.view.WelcomeView.WelcomeView;
import project.cn.edu.tongji.sse.nowfitness.view.WelcomeView.WelcomeViewMethod;

public class WelcomeViewPresenter extends BasePresenter {
    private WelcomeView welcomeView;
    private WelcomeViewMethod welcomeViewMethod;
    public WelcomeViewPresenter(WelcomeView welcomeView, WelcomeViewMethod welcomeViewMethod){
        this.welcomeView = welcomeView;
        this.welcomeViewMethod = welcomeViewMethod;
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepositary.queryUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(welcomeViewMethod::querySuccess,welcomeViewMethod::queryError)
        );
    }
}
