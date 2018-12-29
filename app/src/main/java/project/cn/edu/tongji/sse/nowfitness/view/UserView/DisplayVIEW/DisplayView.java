package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.DisplayPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.ToPersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DisplayView extends AppCompatActivity implements DisplayViewMethod,SwipeRefreshLayout.OnRefreshListener,ToPersonPageView {
    public static final int STARS_TYPE = 1;
    public static final int FANS_TYPE = 0;

    private Toolbar myToolbar;
    private RecyclerView displayRecyclerView;
    private DisplayPresenter displayPresenter;
    private TextView displayText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int type;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_view);
        Intent intent = getIntent();
        String typeString = intent.getStringExtra(ConstantMethod.type_Key);
        if(typeString.equals(ConstantMethod.fans_Type)){
            type = FANS_TYPE;
        }else{
            type = STARS_TYPE;
        }

        displayPresenter = new DisplayPresenter(this,this);
        displayPresenter.initView();

        onRefresh();
        //TODO 本地数据库信息获取

    }


    public void initView(){
        setToolbar();
        displayRecyclerView = findViewById(R.id.individual_recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        LinearLayoutManager manager = new LinearLayoutManager(getApplication());
        displayRecyclerView.setLayoutManager(manager);
        setDisplayText();
        initSwipeLayout();
    }

    private void setToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setDisplayText(){
        displayText = findViewById(R.id.display_text);
        if(type == STARS_TYPE){
            displayText.setText("关注列表");
        }else{
            displayText.setText("粉丝列表");
        }
    }

    private void initSwipeLayout(){
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void queryForSuccess(ResponseModel<IndividualsList> modelList) {
        //TODO 本地数据库信息更新
        Log.d("DisplayView", "querySuccess: ");
        isRefresh = false;
        swipeRefreshLayout.setRefreshing(false);
        if(modelList.getStatus() >= 200 && modelList.getStatus() < 300){
            if(modelList.getData().getTotalNum() == 0){
                DisplayNoItemAdapter displayNoItemAdapter = new DisplayNoItemAdapter();
                displayRecyclerView.setAdapter(displayNoItemAdapter);
                displayNoItemAdapter.notifyDataSetChanged();
            }else{
                //如果请求的是关注列表,则将关注状态全部更改为true;
                if(type == STARS_TYPE){
                    modelList.getData().setTrueForAll();
                }
                DisplayViewAdapter displayViewAdapter = new DisplayViewAdapter(modelList.getData().getIndividualModels(),displayPresenter);
                displayRecyclerView.setAdapter(displayViewAdapter);
                displayViewAdapter.notifyDataSetChanged();
            }
        }
        else{
            ConstantMethod.toastShort(DisplayView.this,modelList.getError());
        }
    }

    @Override
    public void queryError(Throwable e) {
        Log.d("DisplayView", "queryError: ");
        isRefresh = false;
        e.printStackTrace();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        if(!isRefresh){
            isRefresh = true;
            swipeRefreshLayout.setRefreshing(true);
            displayPresenter.queryForFollowingOrFansInfo(type);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    public void jumpToPersonPage(int id, String userName, String nickName, String personPhoto) {
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.putExtra("userName",userName);
        intent.setClass(this, PersonPageView.class);
        startActivity(intent);
    }
}