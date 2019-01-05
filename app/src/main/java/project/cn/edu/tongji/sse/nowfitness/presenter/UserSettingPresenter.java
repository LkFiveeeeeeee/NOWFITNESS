package project.cn.edu.tongji.sse.nowfitness.presenter;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingMethod;

/**
 * Create by LK on 2018/12/18.
 */

public class UserSettingPresenter extends BasePresenter {
    private UserSettingMethod userSettingMethod;
    public UserSettingPresenter(UserSettingMethod userSettingMethod){
        this.userSettingMethod = userSettingMethod;

    }

    public void putUserInfo(UserInfoModel userInfoModel){
        subscriptions.add(apiRepository.putUserInfo(userInfoModel)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userSettingMethod::putSuccess,userSettingMethod::putError)
        );
    }

    public void changePassword(String newPassword){
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(userInfoModel.getId()));
        RequestBody passWordBody = RequestBody.create(MediaType.parse("text/plain"),newPassword);
        subscriptions.add(apiRepository.changePassword(userIdBody,passWordBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userSettingMethod::changeSuccess,userSettingMethod::putError)
        );

    }
}
