package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.DisplayPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DisplayView extends AppCompatActivity implements DisplayViewMethod,SwipeRefreshLayout.OnRefreshListener{
    public static final int STARS_TYPE = 1;
    public static final int FANS_TYPE = 0;

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
        displayRecyclerView = findViewById(R.id.individual_recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        LinearLayoutManager manager = new LinearLayoutManager(getApplication());
        displayRecyclerView.setLayoutManager(manager);
        setDisplayText();
        initSwipeLayout();
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
                DisplayViewAdapter displayViewAdapter = new DisplayViewAdapter(modelList.getData().getIndividualModels());
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
}
