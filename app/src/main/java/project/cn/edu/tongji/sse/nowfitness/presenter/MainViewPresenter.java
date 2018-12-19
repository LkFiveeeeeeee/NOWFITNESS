package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositaryImpl;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainViewMethod;

public class MainViewPresenter extends BasePresenter {
    private MainView mainView;
    private MainViewMethod mainViewMethod;

    public MainViewPresenter(MainView mainView,MainViewMethod mainViewMethod){
        this.mainView = mainView;
        this.mainViewMethod = mainViewMethod;
    }

    public void queryForUserInfo(String userName){
        subscriptions.add(apiRepositary.queryUserInfo(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(mainViewMethod::querySuccess,mainViewMethod::queryError)
        );
    }


    public void initView(){
        mainView.initView();
    }
}
