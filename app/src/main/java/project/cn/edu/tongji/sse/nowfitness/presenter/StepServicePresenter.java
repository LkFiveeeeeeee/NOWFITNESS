package project.cn.edu.tongji.sse.nowfitness.presenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class StepServicePresenter extends BasePresenter{
    public void putTodayStepsData(int steps) {
        String temp = "multipart/form-data";
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestId = RequestBody.create(MediaType.parse(temp),
                String.valueOf(UserInfoLab.get().getUserInfoModel().getId()));
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
                .subscribe());
    }
}
