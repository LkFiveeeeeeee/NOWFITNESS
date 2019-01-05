package project.cn.edu.tongji.sse.nowfitness.view.UserSettingView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoMethod;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.SaltModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.UserSettingPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.LoginAndRegisterView.LoginView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.Encryption;

/**
 * Create by LK on 2018/12/15.
 */

public class UserSettingView extends AppCompatActivity
        implements UserSettingMethod{
    /**
     * Presenter param
     */
    private UserSettingPresenter userSettingPresenter;

    /**
     * UI param
     */
    private CardView userInfoSetting;
    private CardView passWordSetting;
    private AlertDialog passWordDialog;
    private AlertDialog userInfoDialog;
    private AppCompatButton logoutButton;
    private EditText ageInfo;
    private TextInputEditText nickNameText;
    private TextInputEditText oldPassword;
    private TextInputEditText newPassword;
    private TextInputEditText repeatPassword;




    private String sex = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_view);
        userSettingPresenter = new UserSettingPresenter(this);
        initView();
    }

    private void initView(){
        userInfoSetting = findViewById(R.id.userinfo_setting);
        passWordSetting = findViewById(R.id.password_setting);
        logoutButton = findViewById(R.id.logout_button);
        setUpPassWordDialog();
        setUpUserInfoDialog();
        setListener();
        setToolbar();
    }

    //初始化设置密码dialog
    private void setUpPassWordDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View passWordView = inflater.inflate
                (R.layout.changepassword_view, findViewById(R.id.password_dialog));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        oldPassword = passWordView.findViewById(R.id.old_password);
        newPassword = passWordView.findViewById(R.id.new_password);
        repeatPassword = passWordView.findViewById(R.id.repeat_password);
        passWordDialog = builder.setTitle("修改密码")
            .setView(passWordView)
            .setIcon(R.drawable.password)
            .setNeutralButton("取消", (dialogInterface, i) -> passWordDialog.hide())
            .setPositiveButton("确定",null)
            .create();
        passWordDialog.setOnShowListener(dialogInterface -> {
            Button positive = passWordDialog.getButton(dialogInterface.BUTTON_POSITIVE);
            positive.setOnClickListener(view -> {
                //当用户点击完成时,先验证其输入是否满足条件
                if(verifyPassword(oldPassword.getText().toString(),
                        newPassword.getText().toString(),
                        repeatPassword.getText().toString())){
                    userSettingPresenter.changePassword(newPassword.getText().toString());
                }
            });
        });

    }

    //初始化设置个人信息dialog
    private void setUpUserInfoDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View userInfoView = inflater.inflate
                (R.layout.changeuserinfo_view, findViewById(R.id.userinfo_dialog));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ageInfo = userInfoView.findViewById(R.id.age_text);
        RadioGroup sexChoose = userInfoView.findViewById(R.id.sex_button);
        nickNameText = userInfoView.findViewById(R.id.username_text);
        nickNameText.setText(UserInfoLab.get().getUserInfoModel().getNickName());
        sex = UserInfoLab.get().getUserInfoModel().getSex();
        switch (sex) {
            case "男":
                sexChoose.check(R.id.male_sex);
                break;
            case "女":
                sexChoose.check(R.id.female_sex);
                break;
            default:
                //DO NOTHING
                break;
        }
        ageInfo.setText(String.valueOf(UserInfoLab.get().getUserInfoModel().getAge()));
        sexChoose.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton sexButton = userInfoView.findViewById(i);
            sex = sexButton.getText().toString();
        });

        userInfoDialog = builder.setTitle("修改个人信息")
                .setView(userInfoView)
                .setIcon(R.mipmap.ic_add)
                .setNeutralButton("取消", (dialogInterface, i) -> passWordDialog.hide())
                .setPositiveButton("确定",null)
                .create();
        //当用户点击确认时,先判断其输入是否合法
        userInfoDialog.setOnShowListener(dialogInterface -> {
            Button positive = userInfoDialog.getButton(dialogInterface.BUTTON_POSITIVE);
            positive.setOnClickListener(view -> {
                if(verifyInfo(nickNameText.getText().toString(),ageInfo.getText().toString(),
                        sex)){
                    UserInfoModel putModel = new UserInfoModel(UserInfoLab.get().getUserInfoModel());
                    putModel.setAge(Integer.valueOf(ageInfo.getText().toString()));
                    putModel.setNickName(nickNameText.getText().toString());
                    putModel.setSex(sex);
                    putModel.setPictureUrl("");
                    userSettingPresenter.putUserInfo(putModel);
                }
            });
        });

    }


    private void setListener(){
        passWordSetting.setOnClickListener(view -> passWordDialog.show());
        userInfoSetting.setOnClickListener(view -> userInfoDialog.show());
        logoutButton.setOnClickListener(view -> {
            DaoMethod.deleteToken();
            Intent intent = new Intent(UserSettingView.this,LoginView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.userinfo_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    private boolean verifyInfo(String userName, String age, String sex){
        if(userName.equals("")){
            ConstantMethod.toastShort(getApplicationContext(),"用户名不能为空");
            return false;
        }else if(userName.length() > 12){
            ConstantMethod.toastShort(getApplicationContext(),"用户名的长度不可以超过12");
            return false;
        }else{
            //DO NOTHING
        }
        if(age.equals("")){
            ConstantMethod.toastShort(getApplicationContext(),"生日还没有进行选择");
            return false;
        }
        if(sex.equals("")){
            ConstantMethod.toastShort(getApplicationContext(),"性别还没有进行选择");
            return false;
        }
        return true;
    }

    private boolean verifyPassword(String oldPassword,String newPassword,String repeatWord){
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        if(oldPassword.length() < Constant.MIN_LENGTH
               || oldPassword.length() > Constant.MAX_LENGTH
               || newPassword.length() < Constant.MIN_LENGTH
               || newPassword.length() > Constant.MAX_LENGTH
               || repeatWord.length() < Constant.MIN_LENGTH
               || repeatWord.length() > Constant.MAX_LENGTH){
            ConstantMethod.toastShort(getApplicationContext(),"密码长度应在6-15位之间");
            return false;
        }
        if(!userInfoModel.getPassword().equals(Encryption.MD5(oldPassword+userInfoModel.getSalt()))){
            ConstantMethod.toastShort(getApplicationContext(),"原密码错误");
            return false;
        }
        if(!newPassword.equals(repeatWord)){
            ConstantMethod.toastShort(getApplicationContext(),"两次输入的密码不一致");
            return false;
        }
        return true;
    }



    @Override
    public void putSuccess(ResponseModel responseModel) {
        if(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300){
            userInfoDialog.dismiss();
            UserInfoLab.get().getUserInfoModel().setAge(Integer.valueOf(ageInfo.getText().toString()));
            UserInfoLab.get().getUserInfoModel().setNickName(nickNameText.getText().toString());
            UserInfoLab.get().getUserInfoModel().setSex(sex);
            DaoManager.getDaoInstance().getDaoSession().
                    getUserInfoModelDao().insertOrReplace(UserInfoLab.get().getUserInfoModel());
            ConstantMethod.toastShort(UserSettingView.this,"更改成功!");
        }else{
            ConstantMethod.toastShort(UserSettingView.this,responseModel.getError());
        }
    }

    @Override
    public void putError(Throwable e) {
        String classTag = "UserSettingView";
        Log.d(classTag, e.toString());
        ConstantMethod.toastShort(getApplicationContext(),"网络错误!");
    }

    @Override
    public void changeSuccess(ResponseModel<SaltModel> saltModelResponseModel) {
        if(saltModelResponseModel.getStatus() >= 200 && saltModelResponseModel.getStatus() < 300){
            UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
            userInfoModel.setSalt(saltModelResponseModel.getData().getSalt());
            userInfoModel.setPassword(Encryption.MD5(newPassword.getText().toString()+userInfoModel.getSalt()));
            DaoManager.getDaoInstance().getDaoSession().
                    getUserInfoModelDao().insertOrReplace(UserInfoLab.get().getUserInfoModel());
            oldPassword.setText("");
            newPassword.setText("");
            repeatPassword.setText("");
            passWordDialog.dismiss();
            ConstantMethod.toastShort(getApplicationContext(),"更改成功!");
        }else{
            ConstantMethod.toastShort(getApplicationContext(),"网络错误!");
        }
    }

    @Override
    protected void onDestroy() {
        userSettingPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
