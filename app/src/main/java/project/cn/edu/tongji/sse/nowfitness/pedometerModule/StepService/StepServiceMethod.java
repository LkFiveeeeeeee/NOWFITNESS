package project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface StepServiceMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
}
