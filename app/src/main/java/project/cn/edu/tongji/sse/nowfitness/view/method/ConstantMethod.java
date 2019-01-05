package project.cn.edu.tongji.sse.nowfitness.view.method;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MyGlideEngine;

/**
 * Created by LK on 2018/11/26.
 */

public class ConstantMethod {
    private ConstantMethod(){}




    //在Fragment中使用Matisse
    public static void useMatisseFromFragment(Fragment fragment){
        Matisse.from(fragment)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
              //  .gridExpectedSize(R.dimen.grid_expected_size)
                .spanCount(Constant.SPAN_COUNT)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(Constant.REQUEST_IMAGE_CODE);
    }


    //在Activity中使用Matisse
    public static void userMatisseFromActivity(Activity activity){
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                //  .gridExpectedSize(R.dimen.grid_expected_size)
                .spanCount(Constant.SPAN_COUNT)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(Constant.REQUEST_IMAGE_CODE);
    }

    //公用Toast函数
    public static void toastShort(Context context,String string){
        Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
    }

    //通过步数和体重计算卡路里
    public static double countCalories(int steps,double weight){
        return steps * weight * Constant.AVA_STEP * 1.036 / Constant.ONE_THOUSAND;
    }


    public static void checkUserInfoModel(){
        if(UserInfoLab.get().getUserInfoModel() == null){
            UserInfoModel userInfoModel = DaoMethod.queryForUserInfo().get(0);
            UserInfoLab.get().setUserInfoModel(userInfoModel);
        }
    }
}
