package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface MomentsMethod {

    void queryError(Throwable e);

    void querySuccess(ResponseModel<MomentsModelList> momentsModelListResponseModel);

    void queryInfoSuccess(ResponseModel<UserInfoModel> userInfoModelResponseModel);

}
