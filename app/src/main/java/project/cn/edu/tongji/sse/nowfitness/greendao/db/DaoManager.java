package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication;


public class DaoManager {
    private final String TAG="OnDaoManager";
    private static final String DB_NAME="greendao";
    private static DaoManager daoInstance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static DaoManager getDaoInstance(){
        if(daoInstance == null){
            synchronized (DaoManager.class){
                if(daoInstance == null){
                    daoInstance = new DaoManager();
                }
            }
        }
        return daoInstance;
    }

    private DaoManager(){
        if(daoInstance == null){
            if(NOWFITNESSApplication.getContext() == null){
                Log.d(TAG, "DaoManager: context is null!!!!!!");
            }
            MySqliteOpenHelper helper
                    = new MySqliteOpenHelper(
                    NOWFITNESSApplication.getContext(),DB_NAME,null
            );
            SQLiteDatabase db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession =daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public DaoMaster getDaoMaster(){
        return daoMaster;
    }

}
