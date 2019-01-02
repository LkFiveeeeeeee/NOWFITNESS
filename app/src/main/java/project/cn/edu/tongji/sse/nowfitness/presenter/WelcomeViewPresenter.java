package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.view.WelComeview.WelcomeViewMethod;

public class WelcomeViewPresenter extends BasePresenter {
    private WelcomeViewMethod welcomeViewMethod;
    public WelcomeViewPresenter(WelcomeViewMethod welcomeViewMethod){
        this.welcomeViewMethod = welcomeViewMethod;
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(welcomeViewMethod::querySuccess,welcomeViewMethod::queryError)
        );
    }
}
