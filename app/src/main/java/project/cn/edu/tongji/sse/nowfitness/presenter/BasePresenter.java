package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.disposables.CompositeDisposable;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositoryImpl;
import project.cn.edu.tongji.sse.nowfitness.data.DouBanAPIRepositoryImpl;

public class BasePresenter {
    protected  CompositeDisposable subscriptions = new CompositeDisposable();
    protected APIRepositoryImpl apiRepository = new APIRepositoryImpl();
    public void onViewDestroyed(){
        subscriptions.dispose();
    }
    protected DouBanAPIRepositoryImpl douBanAPIRepository = new DouBanAPIRepositoryImpl();
}
