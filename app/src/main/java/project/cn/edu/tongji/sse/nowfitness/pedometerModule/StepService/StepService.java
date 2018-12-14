package project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService;

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
import project.cn.edu.tongji.sse.nowfitness.model.StepLab;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.accelerometer.StepCount;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.accelerometer.StepValuePassListener;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainView;


public class StepService extends Service implements SensorEventListener {
    private String TAG = "StepService Debug";

    //30s进行一次存储
    private static int duration = 30 * 1000;
    //当前日期
    private static String currentDate = "";
    //传感器管理对象
    private SensorManager sensorManager;
    //广播接受者
    private BroadcastReceiver broadcastReceiver;
    //当前所走的步数
    private int currentStep;
    //计步传感器类型
    private static int stepSensorType = -1;
    //第一次启动服务时是否从系统获取了已有的步数记录
    private boolean hasRecord = false;
    //系统中获取到的已有的步数
    private int hasStepCount = 0;
    //上一次的步数
    private int previousStepCount = 0;
    //通知管理对象
    private NotificationManager notificationManager;
    //加速度传感器中获取的步数
    private StepCount stepCount;

    private StepBinder stepBinder = new StepBinder();

    //时间记录
    private TimeCount time;

    //通知构建者
    private NotificationCompat.Builder builder;


    //计步notification ID
    int notifyID = 100;

    //频道ID
    String channelID;

    //UI监听器
    private UpdateUICallBack uiCallBack;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();
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
                    .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                    //        .setContentIntent(getDe)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.fitnesslogo);
        }else{
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("NowFitness计步")
                    .setContentText("今日计步" + currentStep +" 步")
                    .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                    //        .setContentIntent(getDe)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.fitnesslogo);
        }
  /*      builder = new NotificationCompat.Builder(this,channelID);
        builder.setContentTitle("NowFitness计步")
                .setContentText("今日计步" + currentStep +" 步")
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
        //        .setContentIntent(getDe)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.fitnesslogo);*/
        Notification notification = builder.build();

        startForeground(notifyID,notification);
        Log.d(TAG, "initNotification: ");
    }

    private PendingIntent getDefalutIntent(int flagOngoingEvent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,new Intent(),flagOngoingEvent);
        return pendingIntent;
    }

    private void initTodayData(){
        currentDate = getTodayDate();
        //TODO 数据库相关
        List<StepModel> stepList = DaoMethod.queryByDate(currentDate,1);
        if(stepList.size() == 0 || stepList.isEmpty()){
            currentStep = 0;
            StepModel stepModel = new StepModel();
            stepModel.setId(null);
            stepModel.setStep(String.valueOf(currentStep));
            stepModel.setToday(currentDate);
            StepLab.get().setStepModel(stepModel);
            DaoManager.getDaoInstance().getDaoSession().getStepModelDao().insert(stepModel);
        }else if(stepList.size() == 1){
            Log.d(TAG, "initTodayData: 步数初始化");
            currentStep = Integer.parseInt(stepList.get(0).getStep());
            StepLab.get().setStepModel(stepList.get(0));
        }else{
            Log.d(TAG, "initTodayData: 出错了orz");
        }
        updateNotification();
    }

    private String getTodayDate(){
        Date date = new Date(System.currentTimeMillis());
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
        //TODO notify跳转Intent
        //TODO 设置界面
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
        //TODO updateUI
        if(uiCallBack != null){
            uiCallBack.updateUI(currentStep);
        }
    }

    //注册UI监听器
    public void registerCallBack(UpdateUICallBack updateUICallBack){
        this.uiCallBack = updateUICallBack;
    }


    class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture,long countDownInterval){
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

        }
    }

    public int getStepCount(){
        return currentStep;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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
        int VERSION = Build.VERSION.SDK_INT;
        if(VERSION >= 19){
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
        stepCount = new StepCount();
        stepCount.setSteps(currentStep);
        Sensor sensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean isAvailable = sensorManager.registerListener(
                stepCount.getStepDetector(),sensor,SensorManager.SENSOR_DELAY_UI
        );
        stepCount.initListener(new StepValuePassListener(){
            @Override
            public void stepChanged(int steps) {
                currentStep = steps;
                updateNotification();
            }
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
        }else if(stepSensorType == Sensor.TYPE_STEP_DETECTOR){
            if(sensorEvent.values[0] == 1.0){
                currentStep++;
            }
        }
        updateNotification();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
    private void isNewDay(){
        String time = "00:00";
        if(time.equals(new SimpleDateFormat("HH:mm").format(new Date()))){
            initTodayData();
        }
    }

    //保存计步数据
    private void save(){
        int tempStep = currentStep;
        //TODO 数据库操作
        //TODO 修正用户ID
        List<StepModel> stepList = DaoMethod.queryByDate(currentDate,1);
        StepModel data = new StepModel();

        data.setUserId(1);
        data.setToday(currentDate);
        data.setStep(tempStep + "");
        if(stepList.size() == 0 || stepList.isEmpty()){
            DaoManager.getDaoInstance().getDaoSession()
                    .getStepModelDao().insertOrReplace(data);
        }else if(stepList.size() == 1){
            DaoManager.getDaoInstance().getDaoSession()
                    .getStepModelDao().insertOrReplace(data);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createChannel(){
        String channelID = "countStepService";
        String chaanelName = "NowFitness";
        NotificationChannel channel = new NotificationChannel(channelID,chaanelName,NotificationManager.IMPORTANCE_NONE);
        notificationManager.createNotificationChannel(channel);
        return channelID;
    }

}