package project.cn.edu.tongji.sse.nowfitness.view.MainView;

import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface MainViewMethod {
    void querySuccess(UserInfoModel userInfoModel);
    void queryError(Throwable e);
}
