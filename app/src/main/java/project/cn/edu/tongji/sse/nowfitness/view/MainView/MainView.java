package project.cn.edu.tongji.sse.nowfitness.view.MainView;



import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.pedometerModule.StepService.StepService;
import project.cn.edu.tongji.sse.nowfitness.presenter.MainViewPresenter;

import project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication;

import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;
import project.cn.edu.tongji.sse.nowfitness.view.publishMomentView.PublishMomentView;


public class MainView extends AppCompatActivity {
  //  private final String TAG ="Test Database";


    private LeftFragment leftFragment;//omf
    private UserViewFragment userViewFragment;
    private FragmentManager fragmentManager;
    private MainViewPresenter mainViewPresenter;
    private FloatingActionButton addButton;
    private AHBottomNavigation bottomNavigation;

    private Intent serviceIntent;
    private StepService stepService;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        mainViewPresenter = new MainViewPresenter(this);
        setupService();
    /*    Intent intent = getIntent();
        Log.d("11111111", "onCreate: " + intent.getStringExtra(ConstantMethod.userName_Key));
        Log.d("11111111", "onCreate: " + intent.getStringExtra(ConstantMethod.passWord_Key));
        mainViewPresenter.queryForUserInfo(
                intent.getStringExtra(ConstantMethod.userName_Key)
        );*/
        mainViewPresenter.initView();
   //     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void initView(){

        addButton = findViewById(R.id.add_button);
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
        bottomNavigation.disableItemAtPosition(1); //禁用占位图标
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        fragmentManager=getSupportFragmentManager();//omf
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
        leftFragment = new LeftFragment();
        userViewFragment = new UserViewFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment,leftFragment)
                .commit();
    }
    //omf
    private void initEvent(){
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position == 0){
                    if(wasSelected == false){
                        getSupportFragmentManager()
                                .popBackStack();
                    }
                }
                else if (position == 2){
                    if(wasSelected == false){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.right_slide_in,
                                        R.anim.left_slide_out,
                                        R.anim.left_slide_in,
                                        R.anim.right_slide_out)
                                .replace(R.id.fragment,userViewFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
                return true;
            }
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
      /*  conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                StepService stepService = ((StepService.StepBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };*/
        stepService = new StepService(NOWFITNESSApplication.getContext());
        serviceIntent = new Intent(this,stepService.getClass());
        if(!isMyServiceRunning(stepService.getClass()))
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())){
                Log.i("isMyServiceRunning", "isMyServiceRunning: tttrue");
                return  true;
            }
        }
        Log.i("isMyServiceRunning", "isMyServiceRunning: fffalse");
        return false;
    }


}
