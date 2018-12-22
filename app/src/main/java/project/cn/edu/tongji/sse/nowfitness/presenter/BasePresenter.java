package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.disposables.CompositeDisposable;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositoryImpl;

public class BasePresenter {
    protected  CompositeDisposable subscriptions = new CompositeDisposable();
    protected APIRepositoryImpl apiRepositary= new APIRepositoryImpl();
    public void onViewDestroyed(){
        subscriptions.dispose();
    }
}
