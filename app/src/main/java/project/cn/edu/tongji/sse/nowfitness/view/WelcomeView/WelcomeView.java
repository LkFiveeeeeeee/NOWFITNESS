package project.cn.edu.tongji.sse.nowfitness.view.WelcomeView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.wangyuwei.particleview.ParticleView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.WelcomeViewPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginView;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.PermissionMethod;

public class WelcomeView extends AppCompatActivity implements WelcomeViewMethod,PermissionMethod {
    ParticleView welcomePage;
    WelcomeViewPresenter welcomeViewPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_view);
        welcomeViewPresenter = new WelcomeViewPresenter(this,this);
        welcomePage = (ParticleView) findViewById(R.id.welcome_nowfitness);
        welcomePage.startAnim();
        welcomePage.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                if(checkPermission()){
                    checkPageJump();
                }
            }
        });
    }

    @Override
    public void querySuccess(ResponseModel<UserInfoModel> responseModel) {
        //jump to 主页面
        if(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300){
            UserInfoLab.get().setUserInfoModel(responseModel.getData());
            DaoManager.getDaoInstance().getDaoSession().getUserInfoModelDao().deleteAll();
            DaoManager.getDaoInstance().getDaoSession().getUserInfoModelDao().insertOrReplace(responseModel.getData());
            Intent intent = new Intent(WelcomeView.this,MainView.class);
            startActivity(intent);
            finish();
        }else{
            ConstantMethod.toastShort(WelcomeView.this,responseModel.getError());
        }
    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
        UserInfoModel userInfoModel = DaoMethod.queryForUserInfo().get(0);
        UserInfoLab.get().setUserInfoModel(userInfoModel);
        Intent intent = new Intent(WelcomeView.this,MainView.class);
        startActivity(intent);
    }

    @Override
    public boolean checkPermission() {
        if(ContextCompat.checkSelfPermission(WelcomeView.this,ConstantMethod.PERMISSIONS_STORAGE[0])
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(WelcomeView.this,ConstantMethod.PERMISSIONS_STORAGE[1])
                    != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(WelcomeView.this,
                    ConstantMethod.PERMISSIONS_STORAGE[0]) &&
                    ActivityCompat.shouldShowRequestPermissionRationale
                    (WelcomeView.this, ConstantMethod.PERMISSIONS_STORAGE[1])){
                    ActivityCompat.requestPermissions(WelcomeView.this,
                        ConstantMethod.PERMISSIONS_STORAGE,ConstantMethod.REQUEST_PERMISSION_CODE
                );
             /*   ConstantMethod.toastShort(WelcomeView.this,"缺少读写权限");
                finish();*/
            }else{
                ActivityCompat.requestPermissions(WelcomeView.this,
                        ConstantMethod.PERMISSIONS_STORAGE,ConstantMethod.REQUEST_PERMISSION_CODE
                        );
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case ConstantMethod.REQUEST_PERMISSION_CODE:{
                if(grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    checkPageJump();
                }else{
                    ConstantMethod.toastShort(WelcomeView.this,"缺少必要的读写权限");
                    finish();
                }

            }
        }
    }

    public void checkPageJump(){

        List<Token> tokenList = DaoMethod.queryForToken();
        if(tokenList.size() != 0){
            //查询个人信息
            welcomeViewPresenter.queryForUserInfo(tokenList.get(0).getUserName());

        }else{
            //TODO startIntent to 登录界面
            Intent intent = new Intent(WelcomeView.this,LoginView.class);
            startActivity(intent);
            finish();
        }
    }
}
