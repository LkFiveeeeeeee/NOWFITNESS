package project.cn.edu.tongji.sse.nowfitness.view.MainView;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoSession;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.IndiInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.IndiRelationModelDao;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.UserInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.model.IndiInfoModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndiRelationModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MainViewPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.LeftFragment;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.PermissionMethod;


public class MainView extends AppCompatActivity implements PermissionMethod,MainViewMethod{
    private final String TAG ="Test Database";


    private LeftFragment leftFragment;//omf
    private UserViewFragment userViewFragment;
    private FragmentManager fragmentManager;
    private MainViewPresenter mainViewPresenter;



    private AHBottomNavigation bottomNavigation;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        mainViewPresenter = new MainViewPresenter(this,this);
        Intent intent = getIntent();
        Log.d("11111111", "onCreate: " + intent.getStringExtra(ConstantMethod.userName_Key));
        Log.d("11111111", "onCreate: " + intent.getStringExtra(ConstantMethod.passWord_Key));
        mainViewPresenter.queryForUserInfo(
                intent.getStringExtra(ConstantMethod.userName_Key),
                intent.getStringExtra(ConstantMethod.passWord_Key)
        );
        testDataBase();
        mainViewPresenter.initView();
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
        bottomNavigation.disableItemAtPosition(1); //禁用占位图标
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        fragmentManager=getSupportFragmentManager();//omf
        initFragment();
        initEvent();
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
             /*   int leftcount = 0;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(position==0){
                    if(leftFragment ==null) {
                        leftFragment = new LeftFragment();
                        fragmentTransaction.add(R.id.fragment, leftFragment);
                    }
                    if(wasSelected == false || leftcount == 0){
                        fragmentTransaction.replace(R.id.fragment,leftFragment);
                        fragmentTransaction.commit();
                        leftcount++;
                        Log.d("FragmentTAC", "onTabSelected 0");
                    }
                }else if(position == 2){
                    if(userViewFragment == null){
                        userViewFragment = new UserViewFragment();
                        fragmentTransaction.add(R.id.fragment,userViewFragment);
                    }
                    if(wasSelected == false){
                        fragmentTransaction
                                .setCustomAnimations(R.anim.right_slide_in,
                                        R.anim.left_slide_out,
                                        R.anim.left_slide_in,
                                        R.anim.right_slide_out)
                                .replace(R.id.fragment,userViewFragment);

                        fragmentTransaction.commit();
                        Log.d("FragmentTAC", "onTabSelected 2");
                    }

                }*/
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

    @Override
    public void checkPermission() {
        List<String> permissionList = new ArrayList<>();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            Log.d("1111", "onRequestPermissionsResult:sendsend ");
            for(int i = 0; i < ConstantMethod.PERMISSIONS_STROAGE.length; i++){
                if(ContextCompat.checkSelfPermission
                        (MainView.this,ConstantMethod.PERMISSIONS_STROAGE[i]) !=
                        PackageManager.PERMISSION_GRANTED){
                    permissionList.add(ConstantMethod.PERMISSIONS_STROAGE[i]);
                }
                if(permissionList.isEmpty()){
                    return;
                }else{
                    String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                    ActivityCompat.requestPermissions(MainView.this,permissions,ConstantMethod.REQUEST_PERMISSION_CODE);;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void querySuccess(UserInfoModel userInfoModel) {
        UserInfoLab.get().setUserInfoModel(userInfoModel);
        Long num = Long.valueOf(2);
        DaoSession daoSession = DaoManager.getDaoInstance().getDaoSession();
        UserInfoModelDao userInfoModelDao = daoSession.getUserInfoModelDao();
        userInfoModelDao.insertOrReplace(UserInfoLab.get().getUserInfoModel());
        Log.d("1111111", "querySuccess: UserInfoSuccess!!!!");
    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
        Log.d("1111111", "queryError: errorForQUERY!!!");
    }

    private void testDataBase(){
        DaoSession daoSession = DaoManager.getDaoInstance().getDaoSession();
        IndiRelationModelDao relationModelDao = daoSession.getIndiRelationModelDao();
        IndiInfoModelDao infoModelDao = daoSession.getIndiInfoModelDao();
        IndiRelationModel relationModel = new IndiRelationModel(null,
                1,2);
        IndiRelationModel relationModel1 = new IndiRelationModel(null,1,3);
        relationModelDao.insertInTx(relationModel,relationModel1);
        IndiInfoModel indiInfoModel = new IndiInfoModel(null,"1","2","3");
        infoModelDao.insert(indiInfoModel);
        indiInfoModel.setUserName("小芳");
        DaoMethod.updateIndiInfo(indiInfoModel);
        List<IndiInfoModel> list = DaoMethod.queryForIndiInfo(1);
        Log.d(TAG, list.toString());
        Log.d(TAG, "AA"  + list.size());
    }
}
