package project.cn.edu.tongji.sse.nowfitness.view.UserSettingView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.SaltModel;


/**
 * Create by LK on 2018/12/22.
 */

public interface UserSettingMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
    void changeSuccess(ResponseModel<SaltModel> saltModelResponseModel);
}
