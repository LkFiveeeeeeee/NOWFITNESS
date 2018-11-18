package project.cn.edu.tongji.sse.nowfitness.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.RegisterPresenter;

public class RegisterView extends AppCompatActivity {
    private FloatingActionButton cancelButton;
    private CardView registerView;
    private RegisterPresenter registerPresenter;


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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateRevealClose();
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
}
