package project.cn.edu.tongji.sse.nowfitness.pedometermodule.accelerometer;

/**
 * Created by LK on 2018/12/11.
 */

public class StepCount implements StepCountListener{
    private int priCount = 0;
    private int uiCount = 0;
    private StepValuePassListener stepValuePassListener;
    private long timeOfLastPeak = 0;
    private long timeOfThisPeak = 0;
    private StepDetector stepDetector;

    public StepCount(){
        stepDetector = new StepDetector();
        //TODO 修改bug
        stepDetector.initListener(this);
    }
    public void initListener(StepValuePassListener listener){
        this.stepValuePassListener = listener;
    }

    public StepDetector getStepDetector(){
        return stepDetector;
    }


    //三秒内计步超过5步才开始正式计步
    @Override
    public void countStep() {
        final long peakNum = 3000L;
        final int limitSecond = 5;
        this.timeOfLastPeak = this.timeOfThisPeak;
        this.timeOfThisPeak = System.currentTimeMillis();
        if(this.timeOfThisPeak -this.timeOfLastPeak <= peakNum){
            if(this.priCount < limitSecond){
                this.priCount++;
            }else if(this.priCount == limitSecond){
                this.priCount++;
                this.uiCount = this.priCount;
                notifyListener();
            }else{
                this.uiCount++;
                notifyListener();
            }
        }else{
            this.priCount = 1;
        }
    }

    public void notifyListener(){
        if(this.stepValuePassListener != null){
            this.stepValuePassListener.stepChanged(this.uiCount);
        }
    }

    public void setSteps(int initValue){
        uiCount = initValue;
        priCount = 0;
        timeOfLastPeak = 0;
        timeOfThisPeak = 0;
        notifyListener();
    }
}
