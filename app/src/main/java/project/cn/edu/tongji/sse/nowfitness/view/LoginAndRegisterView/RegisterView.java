package project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.RegisterPresenter;

public class RegisterView extends AppCompatActivity {
    private FloatingActionButton cancelButton;
    private CardView registerView;
    private RegisterPresenter registerPresenter;
    private TextInputEditText userName;
    private TextInputEditText passWord;
    private TextInputEditText repeatPassWord;
    private TextInputLayout userNameLayout;
    private TextInputLayout passWordLayout;
    private TextInputLayout repeatPassWordLayout;
    private Button submitButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_two);
        registerPresenter = new RegisterPresenter(this);
        registerPresenter.showAnimate();
        registerPresenter.initView();

    }

    public void initView(){
        cancelButton = findViewById(R.id.cancel_button);
        registerView = findViewById(R.id.register_view);
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        repeatPassWord = findViewById(R.id.repeatpassword);
        userNameLayout = findViewById(R.id.usernamelayout);
        passWordLayout = findViewById(R.id.passwordlayout);
        repeatPassWordLayout = findViewById(R.id.repeatpasswordlayout);
        submitButton = findViewById(R.id.finish);
        initListener();
    }

    private void initListener(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateRevealClose();
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
                registerPresenter.vertifyUserName(userName.getText().toString());
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
                registerPresenter.vertifyPassWord(passWord.getText().toString());
                if(!repeatPassWord.getText().toString().equals("")){
                    registerPresenter.vertifyPassWordAgain(passWord.getText().toString(),repeatPassWord.getText().toString());
                }
            }
        });


        repeatPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                registerPresenter.vertifyPassWordAgain(repeatPassWord.getText().toString(),passWord.getText().toString());
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                //提交表单,一系列验证操作
            }
        });

    }

    public void showEnterAnimaton(){
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                registerView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }


    public void animateRevealShow(){
        Animator animator = ViewAnimationUtils.createCircularReveal
                (registerView,registerView.getWidth()/2,0,
                        cancelButton.getWidth()/2,registerView.getHeight()
                );
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                registerView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    public void animateRevealClose(){
        Animator animator = ViewAnimationUtils.createCircularReveal(
                registerView,registerView.getWidth()/2,
                0,registerView.getHeight(),
                cancelButton.getWidth()/2
        );
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                registerView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                cancelButton.setImageResource(R.mipmap.ic_add_round);
                RegisterView.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


    public void userNameSetError(String error){
        userNameLayout.setError(error);
    }

    public void passWordSetError(String error){
        passWordLayout.setError(error);
    }

    public void repeatPassWordError(String error){
        repeatPassWordLayout.setError(error);
    }
}
