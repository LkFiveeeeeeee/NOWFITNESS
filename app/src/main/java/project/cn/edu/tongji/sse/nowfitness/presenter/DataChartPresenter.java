package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.view.DataChartView.DataChartMethod;

public class DataChartPresenter extends BasePresenter {
    private DataChartMethod dataChartMethod;
    public DataChartPresenter(DataChartMethod dataChartMethod){
        this.dataChartMethod = dataChartMethod;
    }

    public void getStepsData(int dayCount){
        int id = (int) UserInfoLab.get().getUserInfoModel().getId();
        subscriptions.add(apiRepository.getStepsData(id,dayCount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(dataChartMethod::querySuccess,dataChartMethod::queryError)
        );
    }
}
