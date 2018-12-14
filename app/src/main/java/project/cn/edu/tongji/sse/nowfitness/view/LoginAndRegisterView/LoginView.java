package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.LoginPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MainView.MainView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class LoginView extends AppCompatActivity implements loginMethod {

    private LoginPresenter loginPresenter;
    private TextInputEditText userName;
    private TextInputEditText passWord;
    private TextInputLayout userNameLayout;
    private TextInputLayout passWordLayout;
    private Button loginButton;
    private CardView loginView;
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
        loginView = findViewById(R.id.login_view);
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
                ActivityOptionsCompat optionsCompat
                        = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginView.this);

                loginPresenter.queryForVertify(userName.getText().toString(),passWord.getText().toString());
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
                //TODO
                //注册页面
                Intent intent = new Intent(LoginView.this,RegisterView.class);
                startActivity(intent,optionsCompat.toBundle());
            }
        });

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.vertifyForUserName(userName.getText().toString());
            }
        });

        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loginPresenter.vertifyForPassWord(passWord.getText().toString());
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
    public void loginSuccess(ResponseModel responseModel) {
        Log.d("1111111", "loginSuccess: ");
        Log.d("11111",Constant.LOGIN_SUCCESS);
        if(responseModel.getStatus() == 200){
            useIntent(userName.getText().toString(),passWord.getText().toString());
        }else{
            Toast.makeText(this, responseModel.getError(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void loginError(Throwable e) {
        Log.d("222222", "loginError: ");
        e.printStackTrace();
    }

    public void useIntent(String userName,String passWord){
        Intent intent = new Intent(LoginView.this,MainView.class);
        intent.putExtra(ConstantMethod.userName_Key,userName);
        intent.putExtra(ConstantMethod.passWord_Key,passWord);
        startActivity(intent);
    }
}
