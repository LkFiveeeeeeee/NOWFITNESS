package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

/**
 * Create by LK on 2018/12/30.
 */

public interface QuestionResultMethod {
    void postSuccess(ResponseModel responseModel);
    void postError(Throwable e);
}
