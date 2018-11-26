package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.disposables.CompositeDisposable;
import project.cn.edu.tongji.sse.nowfitness.data.APIRepositaryImpl;

public class BasePresenter {
    protected  CompositeDisposable subscriptions = new CompositeDisposable();
    protected APIRepositaryImpl apiRepositary= new APIRepositaryImpl();
    public void onViewDestroyed(){
        subscriptions.dispose();
    }
}
