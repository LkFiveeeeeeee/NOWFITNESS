package project.cn.edu.tongji.sse.nowfitness.view.usersettingview;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.SaltModel;

public interface UserSettingMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
    void changeSuccess(ResponseModel<SaltModel> saltModelResponseModel);
}
