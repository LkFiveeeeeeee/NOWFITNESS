package project.cn.edu.tongji.sse.nowfitness.view.PersonPageView;

import project.cn.edu.tongji.sse.nowfitness.model.FollowingRelation;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;


/**
 * Create by LK on 2018/12/24.
 */

public interface PersonPageViewMethod {

    void queryError(Throwable e);
    void queryRelationSuccess(ResponseModel<FollowingRelation> followingRelationResponseModel);
}
