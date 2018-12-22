package project.cn.edu.tongji.sse.nowfitness.view.UserSettingView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface UserSettingMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
}
