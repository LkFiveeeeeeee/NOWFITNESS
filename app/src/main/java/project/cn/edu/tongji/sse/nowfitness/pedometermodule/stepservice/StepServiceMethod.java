package project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

public interface StepServiceMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
}
