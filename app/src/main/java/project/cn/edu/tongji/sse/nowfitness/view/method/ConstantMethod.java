package project.cn.edu.tongji.sse.nowfitness.view.method;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.MyGlideEngine;

public class ConstantMethod {
    public static String[] PERMISSIONS_STROAGE={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static int REQUEST_PERMISSION_CODE = 55;
    public static int REQUEST_IMAGE_CODE = 188;
    public static String userName_Key = "userName";
    public static String passWord_Key = "passWord";

    public static Intent newIntent(Context packageContext,Class<?> targetClass){
        Intent intent = new Intent(packageContext,targetClass);
        return intent;
    }

    //TODO 模板Intent函数?

    public static void useMatisseFromFragment(Fragment fragment){
        Matisse.from(fragment)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
              //  .gridExpectedSize(R.dimen.grid_expected_size)
                .spanCount(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_IMAGE_CODE);
    }


    public static void userMatisseFromActivity(Activity activity){
        Matisse.from(activity)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(1)
                //  .gridExpectedSize(R.dimen.grid_expected_size)
                .spanCount(3)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_IMAGE_CODE);
    }
}