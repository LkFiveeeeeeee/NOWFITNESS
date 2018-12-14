package project.cn.edu.tongji.sse.nowfitness.view;

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

        Intent intent = new Intent(this,StepService.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(intent);
        }else{
            startService(intent);
        }
    }
}
