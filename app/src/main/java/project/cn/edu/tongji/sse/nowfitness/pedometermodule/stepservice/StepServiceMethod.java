package project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice;

import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;

/**
 * Created by LK on 2018/12/11.
 */

public interface StepServiceMethod {
    void putSuccess(ResponseModel responseModel);
    void putError(Throwable e);
}
