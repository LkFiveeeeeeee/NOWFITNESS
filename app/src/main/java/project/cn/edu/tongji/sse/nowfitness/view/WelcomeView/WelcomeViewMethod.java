package project.cn.edu.tongji.sse.nowfitness.view.WelcomeView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface WelcomeViewMethod {
    void querySuccess(ResponseModel<UserInfoModel> responseModel);
    void queryError(Throwable e);
}
