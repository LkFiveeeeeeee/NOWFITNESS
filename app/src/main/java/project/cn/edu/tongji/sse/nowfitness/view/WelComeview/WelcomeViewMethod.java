package project.cn.edu.tongji.sse.nowfitness.view.WelComeview;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface WelcomeViewMethod {
    void querySuccess(ResponseModel<UserInfoModel> responseModel);
    void queryError(Throwable e);
}
