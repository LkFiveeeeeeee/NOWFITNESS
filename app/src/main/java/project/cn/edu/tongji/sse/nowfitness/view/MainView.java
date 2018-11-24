package project.cn.edu.tongji.sse.nowfitness.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;


public class MainView extends AppCompatActivity {

    private LeftFragment leftFragment;//omf
    private UserViewFragment userViewFragment;
    private FragmentManager fragmentManager;



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

        fragmentManager=getSupportFragmentManager();//omf
        initEvent();
    }

    //omf
    private void initEvent(){
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(position==0){
                    if(leftFragment ==null) {
                        leftFragment = new LeftFragment();
                        fragmentTransaction.add(R.id.fragment, leftFragment);
                    }else{
                        fragmentTransaction.show(leftFragment);
                    }
                    fragmentTransaction.commit();
                }else if(position == 2){
                    if(userViewFragment == null){
                        userViewFragment = new UserViewFragment();
                        fragmentTransaction.add(R.id.fragment,userViewFragment);
                    }else{
                        fragmentTransaction.show(userViewFragment);
                    }
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

    }
    //omf
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
