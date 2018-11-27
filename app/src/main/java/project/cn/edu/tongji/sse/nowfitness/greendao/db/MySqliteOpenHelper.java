package project.cn.edu.tongji.sse.nowfitness.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MySqliteOpenHelper extends DaoMaster.OpenHelper {
    String TAG = "GreenDAOHelper Operation";


    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory){
        super(context,name,factory);
    }
    public MySqliteOpenHelper(Context context,String name){
        super(context,name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        if(oldVersion == newVersion){
            Log.d(TAG, "onUpgrade: 数据库是最新版本,无需升级");
            return;
        }
        Log.d(TAG, "onUpgrade: 数据库从" + oldVersion +"升级到"+newVersion+"版本");
        switch (oldVersion){
            case 1:
                String sql ="";
                db.execSQL(sql);
                break;
            case 2:
            default:
                break;
        }
    }
}
