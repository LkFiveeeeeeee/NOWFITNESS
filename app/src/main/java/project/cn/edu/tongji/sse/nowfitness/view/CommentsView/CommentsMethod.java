package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;


/**
 * Created by a on 2018/11/29.
 */

public interface CommentsMethod {
    void querySuccess(ResponseModel<CommentsDetailModelList> commentsDetailModelsList);
    void queryError(Throwable e);

    void makeCommentsSuccess(ResponseModel responseModel);
    void makeCommentsError(Throwable e);

    void connectSuccess(ResponseModel responseModel);
    void connectError(Throwable e);
}
