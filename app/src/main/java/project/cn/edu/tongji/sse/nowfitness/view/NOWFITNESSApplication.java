package project.cn.edu.tongji.sse.nowfitness.view;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
public class NOWFITNESSApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DaoManager.getDaoInstance();
    }

    public static Context getContext(){
        return context;
    }


    /*
     * 将启动服务迁移到MainView
     */

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((Context context,RefreshLayout layout) -> {
            return new ClassicsHeader(context).setEnableLastTime(false);
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((Context context,RefreshLayout layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

}
