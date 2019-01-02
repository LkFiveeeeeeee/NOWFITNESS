package project.cn.edu.tongji.sse.nowfitness.pedometermodule.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class StepDetector implements SensorEventListener {

    //存放三维数据
    private float[] oriValues = new float[3];
    //用于存放计算阈值的波峰波谷差值
    private static final int VALUE_NUM = 4;
    private float[] tempValue = new float[VALUE_NUM];
    private int tempCount = 0;
    //标志是否上升
    private boolean isDirectionUp = false;
    //持续上升的次数
    private int continueUpCount = 0;
    //上一次持续上升的次数
    private int continueUpFormerCount = 0;
    //波峰值
    private float peakOfWave = 0;
    //波谷值
    private float valleyOfWave = 0;
    //上次波峰的时间
    private long timeOfLastPeak = 0;
    //此次波峰的时间
    private long timeOfThisPeak;
    //当前的时间
    private long timeOfNow = 0;
    //上次传感器的值
    private float gravityOld = 0;
    //动态阈值需要的数据
    private static final float INITIAL_VALUE = (float) 1.3;
    //初始阈值
    private float threadValue = (float) 2.0;
    //波峰波谷时间差
    private final int timeInterval = 250;
    private StepCountListener stepCountListener;

    StepDetector() {
        timeOfThisPeak = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //存放三维
        System.arraycopy(sensorEvent.values, 0, oriValues, 0, 3);

        //当前传感器的值
        float gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
                + oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]
        );
        detectorNewStep(gravityNew);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //DO NOTHING
    }

    void initListener(StepCountListener listener){
        this.stepCountListener = listener;
    }

    private void detectorNewStep(float values){
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
                    && (peakOfWave - valleyOfWave >= INITIAL_VALUE)){
                timeOfThisPeak = timeOfNow;
                threadValue = peakValleyThread(peakOfWave - valleyOfWave);
            }
        }
    }

    public boolean detectorPeak(float newValue,float oldValue){
        //上一点的状态
        boolean lastStatus = isDirectionUp;
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
        if(tempCount < VALUE_NUM){
            tempValue[tempCount] = value;
            tempCount++;
        }else{
            tempThread = averageValue(tempValue, VALUE_NUM);
            for(int i = 1; i < VALUE_NUM; i++){
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[VALUE_NUM - 1] =value;
        }
        return tempThread;
    }

    //计算均值
    private float averageValue(float[] value, int n){
        float ave = 0;
        for(int i = 0; i < n;i++){
            ave += value[i];
        }
        ave = ave / VALUE_NUM;
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
