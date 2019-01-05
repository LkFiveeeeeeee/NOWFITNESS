package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

/**
 * Created by LK on 2018/11/24.
 */

public interface RegisterMethod {
    void registerSuccess(ResponseModel<Token> responseModel);

    void netError(Throwable e);

    void querySuccess(ResponseModel<UserInfoModel> responseModel);
}
