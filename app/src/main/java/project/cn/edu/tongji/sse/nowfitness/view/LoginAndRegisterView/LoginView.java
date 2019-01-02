package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.LoginPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class LoginView extends AppCompatActivity implements LoginMethod {

    private LoginPresenter loginPresenter;
    private TextInputEditText userName;
    private TextInputEditText passWord;
    private TextInputLayout userNameLayout;
    private TextInputLayout passWordLayout;
    private Button loginButton;
    private FloatingActionButton switchToRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_one);
        loginPresenter = new LoginPresenter(this,this);
        loginPresenter.initView();

    }

    public void initView(){
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        loginButton = findViewById(R.id.start);
        switchToRegister = findViewById(R.id.switch_button);
        userNameLayout = findViewById(R.id.usernamelayout);
        passWordLayout = findViewById(R.id.passwordlayout);
        setListener();
    }

    private void setListener(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Explode explode = new Explode();
                explode.setDuration(300);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);

                loginPresenter.queryForVerify(userName.getText().toString(),passWord.getText().toString());
            }
        });

        switchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setEnterTransition(null);
                getWindow().setExitTransition(null);
                ActivityOptionsCompat optionsCompat
                        = ActivityOptionsCompat
                        .makeSceneTransitionAnimation
                                (LoginView.this,switchToRegister,switchToRegister.getTransitionName());
                //注册页面
                Intent intent = new Intent(LoginView.this,RegisterView.class);
                startActivity(intent,optionsCompat.toBundle());
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.verifyForUserName(userName.getText().toString());
            }
        });

        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //DO NOTHING
            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.verifyForPassWord(passWord.getText().toString());
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        switchToRegister.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchToRegister.setVisibility(View.VISIBLE);
    }

    public void userNameSetError(String error){
        userNameLayout.setError(error);
    }

    public void passWordSetError(String error){
        passWordLayout.setError(error);
    }

    @Override
    public void loginSuccess(ResponseModel<Token> responseModel) {
        Log.d("1111111", "loginSuccess: ");
        Log.d("11111",Constant.LOGIN_SUCCESS);
        if(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300){
            Token token = responseModel.getData();
            token.setUserName(userName.getText().toString());
            DaoMethod.insertToken(token);
            loginPresenter.queryForUserInfo(userName.getText().toString());

        }else{
            Toast.makeText(this, responseModel.getError(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void netError(Throwable e) {
        Log.d("LoginView", e.toString());
        ConstantMethod.toastShort(LoginView.this,"网络错误!");
    }

    @Override
    public void querySuccess(ResponseModel<UserInfoModel> responseModel) {
        if(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300){
            UserInfoLab.get().setUserInfoModel(responseModel.getData());
            DaoManager.getDaoInstance().getDaoSession().getUserInfoModelDao().deleteAll();
            DaoManager.getDaoInstance().getDaoSession().getUserInfoModelDao().insertOrReplace(responseModel.getData());
            Intent intent = new Intent(LoginView.this,MainView.class);
            startActivity(intent);
            finish();
        }else{
            ConstantMethod.toastShort(LoginView.this,responseModel.getError());
        }
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
