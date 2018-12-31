package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.view.mainview.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.mainview.MainViewMethod;

public class MainViewPresenter extends BasePresenter {
    private MainView mainView;
    private MainViewMethod mainViewMethod;

    public MainViewPresenter(MainView mainView){
        this.mainView = mainView;
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepository.queryUserInfo(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mainViewMethod::querySuccess,mainViewMethod::queryError)
        );
    }


    public void initView(){
        mainView.initView();
    }
}
