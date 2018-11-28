package project.cn.edu.tongji.sse.nowfitness.view;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;

public class NOWFITNESSApplication extends Application {
    private final String TAG = "onApplication";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d(TAG, "onCreate: " + context.toString());
        if(context == null){
            Log.d(TAG, "onCreate: context = null!!!!!!");
        }
        DaoManager.getDaoInstance();
      //  initIndividualMap();
    }

    public static Context getContext(){
        return context;
    }

}
