package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.disposables.CompositeDisposable;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositoryImpl;
import project.cn.edu.tongji.sse.nowfitness.data.DouBanAPIRepositoryImpl;

/**
 * Created by LK on 2018/11/26.
 */

public class BasePresenter {
    //基类定义
    protected  CompositeDisposable subscriptions = new CompositeDisposable();
    protected APIRepositoryImpl apiRepository = new APIRepositoryImpl();
    public void onViewDestroyed(){
        subscriptions.dispose();
    }
    protected DouBanAPIRepositoryImpl douBanAPIRepository = new DouBanAPIRepositoryImpl();
}
