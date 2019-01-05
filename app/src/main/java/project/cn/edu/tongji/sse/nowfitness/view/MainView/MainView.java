package project.cn.edu.tongji.sse.nowfitness.view.MainView;



import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.pedometermodule.stepservice.StepService;

import project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication;

import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;
import project.cn.edu.tongji.sse.nowfitness.view.PublishMomentView.PublishMomentView;

/**
 * Created by LK on 2018/11/22.
 */

public class MainView extends AppCompatActivity {


    private UserViewFragment userViewFragment;
    private FloatingActionButton addButton;
    private AHBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        setupService();
        initView();
    }


    private void initView(){

        addButton = findViewById(R.id.add_button);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem itemOne = new AHBottomNavigationItem
                (R.string.thing,R.drawable.cameradiaphragm,R.color.leftFragment);
        AHBottomNavigationItem itemTwo =new AHBottomNavigationItem("",R.mipmap.ic_add);
        AHBottomNavigationItem itemThree = new AHBottomNavigationItem
                (R.string.user,R.drawable.user,R.color.rightFragment);



        bottomNavigation.addItem(itemOne);
        bottomNavigation.addItem(itemTwo);
        bottomNavigation.addItem(itemThree);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setBehaviorTranslationEnabled(false);

        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setColored(true);
        bottomNavigation.disableItemAtPosition(1); //禁用占位图标
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        initFragment();
        initEvent();
        setListener();
    }

    private void setListener(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainView.this,PublishMomentView.class);
                startActivity(intent);
            }
        });
    }

    private void initFragment(){
        //omf
        LeftFragment leftFragment = new LeftFragment();
        userViewFragment = new UserViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, leftFragment)
                .commit();
    }
    //omf
    private void initEvent(){
        bottomNavigation.setOnTabSelectedListener((int position, boolean wasSelected) ->{
            if(position == 0){
                if(!wasSelected){
                    getSupportFragmentManager()
                            .popBackStack();
                }
            }
            else if (position == 2 && !wasSelected){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_slide_in, R.anim.left_slide_out,
                                R.anim.left_slide_in, R.anim.right_slide_out)
                        .replace(R.id.fragment,userViewFragment)
                        .addToBackStack(null)
                        .commit();
            }else{
                //DO NOTHING
            }
            return true;
        });
        bottomNavigation.setCurrentItem(0);
    }
    //omf
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //处理Android自带Back按钮对Fragment栈的影响
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupService(){
        StepService stepService = new StepService(NOWFITNESSApplication.getContext());
        Intent serviceIntent = new Intent(this, stepService.getClass());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(serviceIntent);
        }else{
            startService(serviceIntent);
        }
    }





}
