package project.cn.edu.tongji.sse.nowfitness.model;

/**
 * Created by LK on 2018/12/11.
 */

public class StepLab {
    private static StepLab stepModelLab;
    private StepModel stepModel;
    public  StepModel getStepModel(){
        return stepModel;
    }
    public void setStepModel(StepModel stepModel){
        this.stepModel = stepModel;
    }

    private StepLab(){
        //DO NOTHING
    }

    public static StepLab get(){
        if(stepModelLab == null){
            stepModelLab = new StepLab();
        }
        return stepModelLab;
    }
    public void setStep(String step){
        stepModel.setStep(step);
    }
}
