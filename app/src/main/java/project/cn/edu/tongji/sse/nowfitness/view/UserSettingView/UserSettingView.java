package project.cn.edu.tongji.sse.nowfitness.view.UserSettingView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import project.cn.edu.tongji.sse.nowfitness.R;

public class UserSettingView extends AppCompatActivity {
    private CardView userInfoSetting;
    private CardView passWordSetting;
    private AlertDialog passWordDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting_view);
        initView();
    }

    private void initView(){
        userInfoSetting = findViewById(R.id.userinfo_setting);
        passWordSetting = findViewById(R.id.password_setting);
        setUpDialog();
        setListener();
        setToolbar();
    }

    private void setUpDialog(){
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

    private void setListener(){
        passWordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passWordDialog.show();
            }
        });
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.userinfo_setting);
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
}
