package project.cn.edu.tongji.sse.nowfitness.presenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice.StepServiceMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class StepServicePresenter extends BasePresenter{
    private final StepServiceMethod stepServiceMethod;
    public StepServicePresenter(StepServiceMethod stepServiceMethod){
        this.stepServiceMethod = stepServiceMethod;
    }

    //更新今日步数
    public void putTodayStepsData(int steps) {
        String temp = "text/plain";
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestId = RequestBody.create(MediaType.parse(temp),
                String.valueOf((int)UserInfoLab.get().getUserInfoModel().getId()));
        RequestBody requestSteps = RequestBody.create(MediaType.parse(temp),
                String.valueOf(steps));
        RequestBody requestCalories = RequestBody.create(MediaType.parse(temp),
                String.valueOf((int)(ConstantMethod.countCalories(steps,userInfoModel.getWeight()))));
        requestBodyMap.put("id",requestId);
        requestBodyMap.put("steps",requestSteps);
        requestBodyMap.put("calories",requestCalories);


        subscriptions.add(apiRepository.putTodayStep(requestBodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stepServiceMethod::putSuccess,stepServiceMethod::putError));
    }
}
