package project.cn.edu.tongji.sse.nowfitness.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.LoginPresenter;

public class LoginView extends AppCompatActivity {

    private LoginPresenter loginPresenter;
    private TextInputEditText userName;
    private TextInputEditText passWord;
    private Button loginButton;
    private CardView loginView;
    private FloatingActionButton switchToRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_one);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.initView();

    }

    public void initView(){
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        loginButton = findViewById(R.id.start);
        loginView = findViewById(R.id.login_view);
        switchToRegister = findViewById(R.id.switch_button);
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
                //TODO 跳转向首页面
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
}
