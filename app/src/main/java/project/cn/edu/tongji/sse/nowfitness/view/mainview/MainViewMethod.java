package project.cn.edu.tongji.sse.nowfitness.view.mainview;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface MainViewMethod {
    void querySuccess(ResponseModel<UserInfoModel> userInfoModel);
    void queryError(Throwable e);
}
