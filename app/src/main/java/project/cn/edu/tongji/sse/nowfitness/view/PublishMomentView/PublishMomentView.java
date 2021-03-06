package project.cn.edu.tongji.sse.nowfitness.view.PublishMomentView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.zhihu.matisse.Matisse;

import java.util.List;
import java.util.Objects;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.PublishMomentPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

/**
 * Created by LK on 2018/11/26.
 */

public class PublishMomentView extends AppCompatActivity implements PublishMomentMethod {
    private ImageView photoSelected;
    private PublishMomentPresenter publishMomentPresenter;
    private AppCompatEditText inputText;
    private List<Uri> imageUri;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_view);
        setToolbar();
        publishMomentPresenter = new PublishMomentPresenter(this);
        initView();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.publish_menu);
    }

    private void initView(){
        photoSelected = findViewById(R.id.display_image);
        inputText = findViewById(R.id.moment_text);
        setListener();
    }

    private void setListener(){
        photoSelected.setOnClickListener(view -> ConstantMethod.userMatisseFromActivity(PublishMomentView.this));
        photoSelected.setOnLongClickListener(view -> {
            Glide.with(getWindow().getDecorView()).load(R.drawable.camera)
                    .transition(new DrawableTransitionOptions().crossFade(1000))
                    .into(photoSelected);
            return true;
        });
    }

    //选择图片后进行回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = Matisse.obtainResult(data);
            //获取Uri,并使用Glide将其添加
            Glide.with(getWindow().getDecorView()).load(imageUri.get(0)).into(photoSelected);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
           finish();
        }else if(item.getItemId() == R.id.publish_button){
            if(imageUri == null && inputText.getText().toString().equals("")){
                ConstantMethod.toastShort(PublishMomentView.this,"提交内容不可为空");
            }else if(imageUri == null){
                publishMomentPresenter.postMoment(inputText.getText().toString(),null);
            }else{
                publishMomentPresenter.postMoment(inputText.getText().toString(),imageUri.get(0));
            }
        }else{
            //DO NOTHING
        }
        return true;
    }

    @Override
    public void postSuccess(ResponseModel responseModel) {
        if(responseModel.getStatus() >= 200 && responseModel.getStatus() < 300){
            //当发表动态发成功时,将个人页面的动态数量更新,然后给出提示Toast,最后才退出该页面
            UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
            userInfoModel.setMomentsNum(userInfoModel.getMomentsNum() + 1);
            ConstantMethod.toastShort(getApplicationContext(),"发表成功!");
            finish();
        }else{
            ConstantMethod.toastShort(PublishMomentView.this,responseModel.getError());
        }
    }

    @Override
    public void postError(Throwable e) {
        ConstantMethod.toastShort(getApplicationContext(),"网络连接错误!");
        Log.d("PublishMomentView", e.toString());
    }

    @Override
    protected void onDestroy() {
        publishMomentPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
