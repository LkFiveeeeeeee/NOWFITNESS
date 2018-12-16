package project.cn.edu.tongji.sse.nowfitness.view.UserSettingView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication;

public class UserSettingView extends AppCompatActivity {
    private CardView userInfoSetting;
    private CardView passWordSetting;
    private AlertDialog passWordDialog;
    private AlertDialog userInfoDialog;
    private Toolbar toolbar;


    private String sex = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_view);
        initView();
    }

    private void initView(){
        userInfoSetting = findViewById(R.id.userinfo_setting);
        passWordSetting = findViewById(R.id.password_setting);
        setUpPassWordDialog();
        setUpUserInfoDialog();
        setListener();
        setToolbar();
    }

    private void setUpPassWordDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View passWordView = inflater.inflate(R.layout.changepassword_view,(ViewGroup) findViewById(R.id.password_dialog));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        passWordDialog = builder.setTitle("修改密码")
            .setView(passWordView)
            .setIcon(R.drawable.password)
            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    passWordDialog.hide();
                }
            })
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO
                    //确定之后的网络与验证操作
                }
            })
            .create();
    }

    private void setUpUserInfoDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View userInfoView = inflater.inflate(R.layout.changeuserinfo_view,(ViewGroup) findViewById(R.id.userinfo_dialog));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout dateChoose = (LinearLayout) userInfoView.findViewById(R.id.date_choose);
        TextView dateInfo = (TextView) userInfoView.findViewById(R.id.date_text);
        RadioGroup sexChoose = (RadioGroup) userInfoView.findViewById(R.id.sex_button);
        TextInputEditText userNameText = (TextInputEditText) userInfoView.findViewById(R.id.username_text);

        sexChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton sexButton = (RadioButton) userInfoView.findViewById(i);
                sex = sexButton.getText().toString();
                Toast.makeText(getApplicationContext(),"你选择了" + sex,Toast.LENGTH_SHORT).show();
            }
        });


        dateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(userInfoDialog.getContext()
                        , new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        Toast.makeText(getApplicationContext(),dateInfo.getText().toString(),Toast.LENGTH_SHORT).show();
                        dateInfo.setText(dateDesc);
                    }
                }).textConfirm("确定")
                        .textCancel("取消")
                        .btnTextSize(16)
                        .viewTextSize(25)
                        .colorCancel(Color.parseColor("#999999"))
                        .colorConfirm(Color.parseColor("#b794f6"))
                        .minYear(1930)
                        .maxYear(2015)
                        .dateChose("1998-11-11")
                        .build();
                //TODO 研究显示
                pickerPopWin.showAtLocation(userInfoDialog.getCurrentFocus(),Gravity.NO_GRAVITY,-200,-500);

            }
        });
        userInfoDialog = builder.setTitle("修改个人信息")
                .setView(userInfoView)
                .setIcon(R.mipmap.ic_add)
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        passWordDialog.hide();
                    }
                })
                .setPositiveButton("确定",null)
                .create();
        userInfoDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positive = userInfoDialog.getButton(dialogInterface.BUTTON_POSITIVE);
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO
                        //确定之后的网络与验证操作
                        if(vertifyInfo(userNameText.getText().toString(),dateInfo.getText().toString(),
                                sex)){
                            userInfoDialog.dismiss();
                        }
                    }
                });
            }
        });

    }


    private void setListener(){
        passWordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passWordDialog.show();
            }
        });
        userInfoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfoDialog.show();
            }
        });
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.userinfo_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    public boolean vertifyInfo(String userName,String birthDay,String sex){
        if(userName.equals("")){
            showToast("用户名不能为空");
            return false;
        }else if(userName.length() > 12){
            showToast("用户名的长度不可以超过12");
            return false;
        }
        if(birthDay.equals("")){
            showToast("生日还没有进行选择");
            return false;
        }
        if(sex.equals("")){
            showToast("性别还没有进行选择");
            return false;
        }
        return true;
    }

    public void showToast(String string){
        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
    }
}
