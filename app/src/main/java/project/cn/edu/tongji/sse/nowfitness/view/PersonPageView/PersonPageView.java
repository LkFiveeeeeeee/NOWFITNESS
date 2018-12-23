package project.cn.edu.tongji.sse.nowfitness.view.PersonPageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.lai.library.ButtonStyle;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.PersonPagePresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;

public class PersonPageView extends AppCompatActivity implements MomentsMethod {

    private int personId;
    private String nickName;
    private String photo;
    private String userName;

    private TextView nameView, followsView, fansView;
    private ButtonStyle ageTab, sexTab;
    private Button followingButton;
    private CircleImageView personPhoto;
    private LinearLayout headLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar personToolbar;
    private RecyclerView momentsRecyclerView;
    private NestedScrollView nestedScrollView;
    private RefreshLayout refreshLayout;

    private PersonPagePresenter personPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_homepage);
        Intent intent = getIntent();
        if(intent!=null) {
            personId = intent.getIntExtra("userId", -1);
            nickName = intent.getStringExtra("nickName");
            userName = intent.getStringExtra("userName");
            photo = intent.getStringExtra("photo");
        }
        personPagePresenter = new PersonPagePresenter(this,this,this);
        personPagePresenter.intiView();
        personPagePresenter.quertForPersonInfo(personId);
       // personPagePresenter.queryForInfo(personId,1);


    }

    public void initView() {
        refreshLayout = (RefreshLayout)findViewById(R.id.person_refreshLayout);
        //refreshLayout.setRefreshHeader(new ClassicsHeader(this).setEnableLastTime(false));
       // refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        personToolbar = (Toolbar)findViewById(R.id.person_toolbar);
        nameView = (TextView) findViewById(R.id.person_name);
        if (nickName!=null)
            nameView.setText(nickName);
        followsView = (TextView) findViewById(R.id.follow);
        fansView = (TextView) findViewById(R.id.fan);
        ageTab = (ButtonStyle) findViewById(R.id.age_button);
        sexTab = (ButtonStyle) findViewById(R.id.sex_button);
        personPhoto = (CircleImageView) findViewById(R.id.person_photo);
        headLayout = (LinearLayout) findViewById(R.id.head_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        followingButton = (Button) findViewById(R.id.person_following);
        ageTab.setEnabled(false);
        sexTab.setEnabled(false);
       // personToolbar.setVisibility(View.GONE);
        nestedScrollView = (NestedScrollView)findViewById(R.id.person_scroll);
        momentsRecyclerView = (RecyclerView)findViewById(R.id.moments_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(PersonPageView.this,LinearLayout.VERTICAL,false);
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        momentsRecyclerView.setHasFixedSize(false);
        momentsRecyclerView.setNestedScrollingEnabled(false);
        momentsRecyclerView.setLayoutManager(mLinearLayoutManager);
        personPagePresenter.setMomentsRecyclerView(momentsRecyclerView);
        personPagePresenter.setAdapter();
        personToolbar.setVisibility(View.GONE);
        personToolbar.setVisibility(View.VISIBLE);
        initEvent();
    }

    public void initEvent() {
        //followingButton.setPressedColor("#80FF0000");
        //不设置，默认3秒后关刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(50/*,false*/);//传入false表示刷新失败
                //finishRefresh(delayed);
                personPagePresenter.queryForInfo(personId,1);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                java.util.Random r= new java.util.Random();
                if(r.nextBoolean())
                    refreshlayout.finishLoadMoreWithNoMoreData();
                else {
                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                    //finishLoadMore(delayed);
                    personPagePresenter.queryForInfo(personId,1);
                }
            }
        });
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (followingButton.getText() == "已关注") {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PersonPageView.this);
                    dialog.setTitle("确认不再关注该用户？");
                    dialog.setMessage("取关后该用户的所有动态将不会出现在你的好友动态中");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            followingButton.setText("+ 关注");
                            followingButton.setBackgroundColor(Color.parseColor("#90FF0000"));
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();
                } else {
                    followingButton.setText("已关注");
                    followingButton.setBackgroundColor(Color.parseColor("#80D3D3D3"));
                    Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
                   // personToolbar.setVisibility(View.VISIBLE);
                    if(nickName!=null)
                        mCollapsingToolbarLayout.setTitle(nickName);
                    else
                        mCollapsingToolbarLayout.setTitle("刘欣然_");
                } else {
                   // personToolbar.setVisibility(View.VISIBLE);
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });
    }

    @Override
    public void querySuccess(ResponseModel<MomentsModelList> momentsModelListResponseModel) {
    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
    }

    public void bindPersonInfo(UserInfoModel userInfoModel){
        Glide.with(this).load(userInfoModel.getPictureUrl()).into(personPhoto);
        followsView.setText("");
        fansView.setText("");
        ageTab.setText(0);
        sexTab.setText("男");
    }
}
