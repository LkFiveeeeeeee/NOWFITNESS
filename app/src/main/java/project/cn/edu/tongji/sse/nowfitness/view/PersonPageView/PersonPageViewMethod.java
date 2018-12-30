package project.cn.edu.tongji.sse.nowfitness.view.PersonPageView;

import project.cn.edu.tongji.sse.nowfitness.model.FollowingRelation;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface PersonPageViewMethod {

    void queryError(Throwable e);
    void queryRelationSuccess(ResponseModel<FollowingRelation> followingRelationResponseModel);
}
