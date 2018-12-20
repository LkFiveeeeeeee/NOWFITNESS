package project.cn.edu.tongji.sse.nowfitness.pedometerModule.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class StepDetector implements SensorEventListener {

    //存放三维数据
    float[] oriValues = new float[3];
    //用于存放计算阈值的波峰波谷差值
    final int valueNum = 4;
    float[] tempValue = new float[valueNum];
    int tempCount = 0;
    //标志是否上升
    boolean isDirectionUp = false;
    //持续上升的次数
    int continueUpCount = 0;
    //上一次持续上升的次数
    int continueUpFormerCount = 0;
    //上一点的状态
    boolean lastStatus = false;
    //波峰值
    float peakOfWave = 0;
    //波谷值
    float valleyOfWave = 0;
    //上次波峰的时间
    long timeOfLastPeak = 0;
    //此次波峰的时间
    long timeOfThisPeak = 0;
    //当前的时间
    long timeOfNow = 0;
    //当前传感器的值
    float gravityNew = 0;
    //上次传感器的值
    float gravityOld = 0;
    //动态阈值需要的数据
    final float initialValue = (float) 1.3;
    //初始阈值
    float threadValue = (float) 2.0;
    //波峰波谷时间差
    int timeInterval = 250;
    private StepCountListener stepCountListener;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //存放三维
        for(int i = 0;i < 3;i++){
            oriValues[i] = sensorEvent.values[i];
        }

        gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
            + oriValues[1] *oriValues[1] + oriValues[2] * oriValues[2]
        );
        detectorNewStep(gravityNew);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void initListener(StepCountListener listener){
        this.stepCountListener = listener;
    }

    public void detectorNewStep(float values){
        if(gravityOld == 0){
            gravityOld = values;
        }else{
            if(detectorPeak(values,gravityOld)){
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if(timeOfNow - timeOfLastPeak >= timeInterval
                        && (peakOfWave - valleyOfWave >= threadValue)){
                    timeOfThisPeak = timeOfNow;
                    stepCountListener.countStep();
                }
            }
            if(timeOfNow - timeOfLastPeak >= timeInterval
                    && (peakOfWave - valleyOfWave >= initialValue)){
                timeOfThisPeak = timeOfNow;
                threadValue = peakValleyThread(peakOfWave - valleyOfWave);
            }
        }
    }

    public boolean detectorPeak(float newValue,float oldValue){
        lastStatus = isDirectionUp;
        if(newValue >= oldValue){
            isDirectionUp = true;
            continueUpCount++;
        }else{
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        if(!isDirectionUp && lastStatus
                && (continueUpFormerCount >= 2 || oldValue >= 20)){
            peakOfWave = oldValue;
            return true;
        }else if(!lastStatus && isDirectionUp){
            valleyOfWave = oldValue;
            return false;
        }else{
            return false;
        }
    }

    public float peakValleyThread(float value){
        float tempThread = threadValue;
        if(tempCount < valueNum){
            tempValue[tempCount] = value;
            tempCount++;
        }else{
            tempThread = averageValue(tempValue,valueNum);
            for(int i = 1; i < valueNum;i++){
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[valueNum - 1] =value;
        }
        return tempThread;
    }

    //计算均值
    public float averageValue(float value[],int n){
        float ave = 0;
        for(int i = 0; i < n;i++){
            ave += value[i];
        }
        ave = ave / valueNum;
        if(ave > 8){
            ave = (float) 4.3;
        }else if(ave >= 7 && ave < 8){
            ave = (float) 3.3;
        }else if(ave >=4 && ave < 7){
            ave = (float) 2.3;
        }else if(ave >=3 && ave < 4){
            ave = (float) 2.0;
        }else{
            ave = (float) 1.3;
        }
        return ave;
    }



}
