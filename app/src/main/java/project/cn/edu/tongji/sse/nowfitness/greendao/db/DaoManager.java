package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication;


public class DaoManager {
    private static final String DB_NAME="greendao";
    private static DaoManager daoInstance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static DaoManager getDaoInstance(){
        if(null == daoInstance){
            daoInstance = new DaoManager();
        }
        return daoInstance;
    }

    private DaoManager(){
        if(daoInstance == null){
            if(NOWFITNESSApplication.getContext() == null){
                String TAG = "OnDaoManager";
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
