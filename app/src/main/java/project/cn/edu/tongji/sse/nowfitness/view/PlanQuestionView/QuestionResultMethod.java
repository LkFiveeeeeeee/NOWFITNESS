package project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface QuestionResultMethod {
    void postSuccess(ResponseModel responseModel);
    void postError(Throwable e);
}
