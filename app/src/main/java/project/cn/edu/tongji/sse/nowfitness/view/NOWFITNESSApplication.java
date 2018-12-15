package project.cn.edu.tongji.sse.nowfitness.view;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.StepService;

public class NOWFITNESSApplication extends Application {
    private final String TAG = "onApplication";
    private static Context context;
    private Intent serviceIntent;
    private StepService stepService;
   // private boolean isBind = false;
    ServiceConnection conn;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d(TAG, "onCreate: " + context.toString());
        if(context == null){
            Log.d(TAG, "onCreate: context = null!!!!!!");
        }
        DaoManager.getDaoInstance();
        setupService();
      //  initIndividualMap();
    }

    public static Context getContext(){
        return context;
    }
    private void setupService(){
      /*  conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                StepService stepService = ((StepService.StepBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };*/
        stepService = new StepService(getContext());
        serviceIntent = new Intent(this,stepService.getClass());
        if(!isMyServiceRunning(stepService.getClass()))
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(serviceIntent);
        }else{
            startService(serviceIntent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())){
                Log.i("isMyServiceRunning", "isMyServiceRunning: tttrue");
                return  true;
            }
        }
        Log.i("isMyServiceRunning", "isMyServiceRunning: fffalse");
        return false;
    }

    //TODO
    /**
     * 将启动服务迁移到MainView
     */

}
