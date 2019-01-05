package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

/**
 * Created by LK on 2018/11/28.
 */

public interface MomentsMethod {

    void queryError(Throwable e);

    void querySuccess(ResponseModel<MomentsModelList> momentsModelListResponseModel);

    void queryInfoSuccess(ResponseModel<UserInfoModel> userInfoModelResponseModel);

}
