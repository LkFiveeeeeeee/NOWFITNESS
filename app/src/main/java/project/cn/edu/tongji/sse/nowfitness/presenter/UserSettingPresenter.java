package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingView;

public class UserSettingPresenter extends BasePresenter {
    UserSettingView userSettingView;
    UserInfoModel userInfoModel;
    public UserSettingPresenter(UserSettingView userSettingView){
        this.userSettingView = userSettingView;
        userInfoModel = UserInfoLab.get().getUserInfoModel();
    }
}
