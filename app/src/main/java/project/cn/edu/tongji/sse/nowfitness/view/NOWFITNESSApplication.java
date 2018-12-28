package project.cn.edu.tongji.sse.nowfitness.view;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.model.StepLab;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.StepServiceMethod;
import project.cn.edu.tongji.sse.nowfitness.presenter.StepServicePresenter;

public class NOWFITNESSApplication extends Application {
    private final String TAG = "onApplication";
    private static Context context;
    private StepServicePresenter stepServicePresenter = new StepServicePresenter();
   // private boolean isBind = false;
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

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        StepModel stepModel = StepLab.get().getStepModel();
        if(stepModel != null){
            stepServicePresenter.putTodayStepsData(Integer.valueOf(stepModel.getStep()));
        }
    }

    //TODO
    /**
     * 将启动服务迁移到MainView
     */

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context).setEnableLastTime(false);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}
