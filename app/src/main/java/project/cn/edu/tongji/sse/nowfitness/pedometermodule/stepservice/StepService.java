package project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepLab;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.pedometermodule.accelerometer.StepCount;
import project.cn.edu.tongji.sse.nowfitness.presenter.StepServicePresenter;
import project.cn.edu.tongji.sse.nowfitness.view.mainview.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;


public class StepService extends Service implements SensorEventListener,StepServiceMethod{
    private String TAG = "StepService Debug";

    //30s进行一次存储
    private  int duration = 30 * 1000;
    //传感器管理对象
    private SensorManager sensorManager;
    //广播接受者
    private BroadcastReceiver broadcastReceiver;
    //当前所走的步数
    private int currentStep;
    //计步传感器类型
    private  int stepSensorType = -1;
    //第一次启动服务时是否从系统获取了已有的步数记录
    private boolean hasRecord = false;
    //系统中获取到的已有的步数
    private int hasStepCount = 0;
    //上一次的步数
    private int previousStepCount = 0;
    //通知管理对象
    private NotificationManager notificationManager;

    private StepBinder stepBinder = new StepBinder();

    //时间记录
    private TimeCount time;

    //通知构建者
    private NotificationCompat.Builder builder;


    //计步notification ID
    int notifyID = 100;

    //
    int countTime = 0;

    //频道ID
    String channelID;

    //UI监听器
    private UpdateUICallBack uiCallBack;

    private StepServicePresenter stepServicePresenter = new StepServicePresenter();

    public StepService(){

    }

    public StepService(Context context){
        super();
        Log.d(TAG, "StepService:启动 ");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(this::startStepDetector).start();
        startTimeCount();
    }

    //初始化通知栏
    private void initNotification(){

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channelID = createChannel();
            builder = new NotificationCompat.Builder(this,channelID);
            builder.setContentTitle("NowFitness计步")
                    .setContentText("今日计步" + currentStep +" 步")
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.VISIBILITY_PUBLIC)
                    .setVibrate(new long[]{0L})
                    .setSound(null)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setOnlyAlertOnce(true)
                    .setSmallIcon(R.drawable.fitnesslogo);
        }else{
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("NowFitness计步")
                    .setContentText("今日计步" + currentStep +" 步")
                    .setContentIntent(getDefaultIntent(Notification.FLAG_ONGOING_EVENT))
                    //        .setContentIntent(getDe)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setVibrate(new long[]{0L})
                    .setAutoCancel(false)
                    .setSound(null)
                    .setOngoing(true)
                    .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                    .setSmallIcon(R.drawable.fitnesslogo);
        }

        Notification notification = builder.build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;
        startForeground(notifyID,notification);
        Log.d(TAG, "initNotification: ");
    }

    private PendingIntent getDefaultIntent(int flagOngoingEvent) {

        return PendingIntent.getActivity(this,1,new Intent(),flagOngoingEvent);
    }

    private void initTodayData(){
        countTime = 0;
        //当前日期
        String currentDate = getTodayDate();
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        if(userInfoModel == null){
            UserInfoLab.get().setUserInfoModel(DaoMethod.queryForUserInfo().get(0));
            userInfoModel = UserInfoLab.get().getUserInfoModel();
        }else{
            Log.d(TAG, "initTodayData: userInfoModel notNull!!!!!!");
        }
        List<StepModel> stepList = DaoMethod.queryByDate(currentDate,userInfoModel.getId());
        Log.d(TAG, "initTodayData: querySize = " + stepList.size());
        if(stepList.isEmpty()){
            currentStep = 0;
            StepModel stepModel = new StepModel();
            stepModel.setId(null);
            stepModel.setUserId((int) userInfoModel.getId());
            stepModel.setStep(String.valueOf(currentStep));
            stepModel.setToday(currentDate);
            StepLab.get().setStepModel(stepModel);
            DaoManager.getDaoInstance().getDaoSession().getStepModelDao().insertOrReplace(stepModel);
        }else if(stepList.size() == 1){
            Log.d(TAG, "initTodayData: 步数初始化");
            currentStep = Integer.parseInt(stepList.get(0).getStep());
            StepLab.get().setStepModel(stepList.get(0));
            Log.d(TAG, "initTodayData: " + stepList.get(0).getId());
        }else{
            Log.d(TAG, "initTodayData: 出错了orz");
        }
        updateNotification();
    }

    private String getTodayDate(){
        Date date = new Date(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    private void initBroadcastReceiver(){
        final IntentFilter filter = new IntentFilter();
        //灭屏
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机
        filter.addAction(Intent.ACTION_SHUTDOWN);
        //亮屏
        filter.addAction(Intent.ACTION_SCREEN_ON);
        //解锁
        filter.addAction(Intent.ACTION_SCREEN_ON);
        //监听关机对话?
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //监听日期变化
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(Intent.ACTION_SCREEN_ON.equals(action)){
                    Log.d(TAG, "onReceive: Screen On");
                }else if(Intent.ACTION_SCREEN_OFF.equals(action)){
                    Log.d(TAG, "onReceive: Screen Off");
                    duration = 60 * 1000;
                }else if(Intent.ACTION_USER_PRESENT.equals(action)){
                    Log.d(TAG, "onReceive: Screen Unlock");
                    duration = 30 * 1000;
                }else if(Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)){
                    Log.d(TAG, "onReceive: Screen out Dialog");
                    save();
                }else if(Intent.ACTION_SHUTDOWN.equals(action)){
                    Log.d(TAG, "onReceive: SHUT DOWN");
                    save();
                    isNewDay();
                }else if(Intent.ACTION_TIME_CHANGED.equals(action) | Intent.ACTION_DATE_CHANGED.equals(action)
                        | Intent.ACTION_TIME_TICK.equals(action)){
                    Log.d(TAG, "onReceive: Time Changed");
                    save();
                    isNewDay();
                }
            }
        };
        registerReceiver(broadcastReceiver,filter);
    }

    private void startTimeCount(){
        if(time == null){
            time = new TimeCount(duration,1000);
        }
        time.start();
    }

    private void updateNotification(){
        Intent intent = new Intent(this,MainView.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
     //   PendingIntent pendingIntent =
     //           PendingIntent.getActivity(this,0,)

        Notification notification = builder.setContentTitle
                (getResources().getString(R.string.app_name))
                .setContentText("今日步数" + currentStep + " 步")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.fitnesslogo)
            //    .setContentIntent()
                .build();
        notificationManager.notify(notifyID,notification);
        if(uiCallBack != null){
            uiCallBack.updateUI(currentStep);
        }
    }

    //注册UI监听器
    public void registerCallBack(UpdateUICallBack updateUICallBack){
        this.uiCallBack = updateUICallBack;
    }

    @Override
    public void putSuccess(ResponseModel responseModel) {
        if(!(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300)){
            ConstantMethod.toastShort(getApplicationContext(),responseModel.getMessage());
        }
    }

    @Override
    public void putError(Throwable e) {
        Log.d(TAG, e.toString());
        ConstantMethod.toastShort(getApplicationContext(),"网络连接错误");
    }


    class TimeCount extends CountDownTimer{
        TimeCount(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onFinish() {
            time.cancel();
            save();
            startTimeCount();
        }

        @Override
        public void onTick(long l) {
            //DO NOTHING
        }
    }

    public int getStepCount(){
        return currentStep;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    private void startStepDetector(){
        if(sensorManager != null){
            sensorManager = null;
        }
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        int version = Build.VERSION.SDK_INT;
        if(version >= 19){
            addCountStepListener();
        }else{
            addBasePedometerListener();
        }
    }

    //添加传感器监听
    //首先选择安卓传感器
    private void addCountStepListener(){
        Sensor countSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(countSensor != null){
            stepSensorType = Sensor.TYPE_STEP_COUNTER;
            Log.d(TAG, "addCountStepListener: STEP_COUNTER");
            sensorManager.registerListener(StepService.this,
                    countSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else if(detectorSensor != null){
            stepSensorType = Sensor.TYPE_STEP_DETECTOR;
            Log.d(TAG, "addCountStepListener: STEP_DETECTOR");
            sensorManager.registerListener(StepService.this,
                    detectorSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Log.d(TAG, "addCountStepListener: not availbale!");
            addBasePedometerListener();
        }
    }

    //通过加速度传感器计步
    private void addBasePedometerListener(){
        //加速度传感器中获取的步数
        StepCount stepCount = new StepCount();
        stepCount.setSteps(currentStep);
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable = sensorManager.registerListener(
                stepCount.getStepDetector(),sensor,SensorManager.SENSOR_DELAY_UI
        );
        stepCount.initListener(steps -> {
            currentStep = steps;
            updateNotification();
        });
        if(isAvailable){
            Log.d(TAG, "addBasePedometerListener: 使用加速度传感器");
        }else{
            Log.d(TAG, "addBasePedometerListener: 加速度传感器也无效");
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(stepSensorType == Sensor.TYPE_STEP_COUNTER){
            int tempStep = (int) sensorEvent.values[0];
            if(!hasRecord){
                hasRecord = true;
                hasStepCount = tempStep;
            }else{
                int thisStepCount = tempStep - hasStepCount;
                int thisStep = thisStepCount - previousStepCount;
                currentStep += thisStep;
                previousStepCount = thisStepCount;
            }
        }else if(stepSensorType == Sensor.TYPE_STEP_DETECTOR && sensorEvent.values[0] == 1.0){
                currentStep++;

        }
        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //DO NOTHING
    }

    public class StepBinder extends Binder{
        public StepService getService(){
            return StepService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stepBinder;
    }


    //检测是否到了新的一天
    @SuppressLint("SimpleDateFormat")
    private void isNewDay(){
        String newTime = "00:00";
        if(newTime.equals(new SimpleDateFormat("HH:mm").format(new Date()))){
            initTodayData();
            putTodayStepsData();
        }
    }

    //保存计步数据
    private void save(){
        int tempStep = currentStep;
        Log.d(TAG, "save: do a save");
        StepLab.get().setStep(tempStep + "");
        Log.d(TAG, "save: " + tempStep);
        DaoManager.getDaoInstance().getDaoSession().getStepModelDao().insertOrReplace(StepLab.get().getStepModel());
        countTime++;
        Log.d(TAG, "save: putTodayStepsData"  + countTime);
        if(countTime == 10){
            putTodayStepsData();
            Log.d(TAG, "save: putTodayStepsData" + "success");
            countTime = 0;
        }
    }

    private void putTodayStepsData(){
        int tempStep = currentStep;
        stepServicePresenter.putTodayStepsData(tempStep);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(broadcastReceiver);
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private String createChannel(){
        String myChannelID = "countStepService";
        String channelName = "NowFitness";
        NotificationChannel channel = new NotificationChannel(myChannelID,channelName,NotificationManager.IMPORTANCE_NONE);
        notificationManager.createNotificationChannel(channel);
        return myChannelID;
    }

}
