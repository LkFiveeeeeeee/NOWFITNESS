package project.cn.edu.tongji.sse.nowfitness.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import project.cn.edu.tongji.sse.nowfitness.R;

public class MainView extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        initView();

    }

    public void initView(){
        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem itemOne = new AHBottomNavigationItem(R.string.thing,R.drawable.cameradiaphragm,R.color.leftFragment);
        AHBottomNavigationItem itemTwo =new AHBottomNavigationItem("",R.mipmap.ic_add);
        AHBottomNavigationItem itemThree = new AHBottomNavigationItem(R.string.user,R.drawable.user,R.color.rightFragment);



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
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.disableItemAtPosition(1); //禁用占位图标
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
    }
}
