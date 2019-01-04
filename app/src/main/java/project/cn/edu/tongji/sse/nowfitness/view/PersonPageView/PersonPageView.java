package project.cn.edu.tongji.sse.nowfitness.view.PersonPageView;

import android.app.Activity;
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
import android.util.Log;
import android.view.MenuItem;
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
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.FollowingRelation;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.PersonPagePresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsRecyclerAdapter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MyQzoneShare;

public class PersonPageView extends AppCompatActivity implements MomentsMethod, MyQzoneShare,PersonPageViewMethod {


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
    private Tencent mTencent;

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
        personPagePresenter = new PersonPagePresenter(this,this,this,this);
        personPagePresenter.intiView();
        if(personId!=(int)UserInfoLab.get().getUserInfoModel().getId()) {
            personPagePresenter.getUserInfo(userName);
            personPagePresenter.getFollowingInfo(personId);
        }
        else {
            bindPersonInfo(UserInfoLab.get().getUserInfoModel());
        }
        personPagePresenter.getUserMoments(personId,1);
        mTencent = Tencent.createInstance(Constant.APP_ID, this.getApplicationContext());
    }

    public void initView() {
        refreshLayout = (RefreshLayout)findViewById(R.id.person_refreshLayout);
        personToolbar = (Toolbar)findViewById(R.id.person_toolbar);
        setSupportActionBar(personToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        nameView = (TextView) findViewById(R.id.person_name);
        if (nickName!=null) {
            nameView.setText(nickName);
        }
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
        if(personId==(int) UserInfoLab.get().getUserInfoModel().getId()){
            followingButton.setVisibility(View.GONE);
        }
        ageTab.setEnabled(false);
        sexTab.setEnabled(false);
        nestedScrollView = (NestedScrollView)findViewById(R.id.person_scroll);
        momentsRecyclerView = (RecyclerView)findViewById(R.id.moments_recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
                PersonPageView.this,LinearLayout.VERTICAL,false);
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
        //不设置，默认3秒后关刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                personPagePresenter.getUserMoments(personId,1);
                if(personId!=(int)UserInfoLab.get().getUserInfoModel().getId()){
                    personPagePresenter.getUserInfo(userName);
                    personPagePresenter.getFollowingInfo(personId);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                    personPagePresenter.getUserMoments(personId,personPagePresenter.getNextPage());
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
                            UserInfoLab.get().getUserInfoModel().
                                    setFollowingNum(UserInfoLab.get().getUserInfoModel().getFollowingNum()-1);
                            personPagePresenter.deleteFollowingInfo(
                                    (int) UserInfoLab.get().getUserInfoModel().getId(),personId);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //back to view nothing to do
                        }
                    });
                    dialog.show();
                } else {
                    followingButton.setText("已关注");
                    followingButton.setBackgroundColor(Color.parseColor("#80D3D3D3"));
                    Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                    UserInfoLab.get().getUserInfoModel().
                            setFollowingNum(UserInfoLab.get().getUserInfoModel().getFollowingNum()+1);
                    personPagePresenter.postFollowingInfo((int) UserInfoLab.get().getUserInfoModel().getId(),personId);
                }
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {//appbar滑动只有原高度一半时显示title
                    if(nickName!=null) {
                        mCollapsingToolbarLayout.setTitle(nickName);
                    }
                    else {
                        mCollapsingToolbarLayout.setTitle("无名氏");
                    }
                } else {
                   // //another method personToolbar.setVisibility(View.VISIBLE);x
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });
    }

    /**
     * @Author: omf
     * @Description: 网络请求获取服务端用户个人动态成功后的回调
     * @Param momentsModelListResponseModel
     * @Return: void
     */
    @Override
    public void querySuccess(ResponseModel<MomentsModelList> momentsModelListResponseModel) {

        if(momentsModelListResponseModel.getStatus() >= Constant.NET_CODE_200
                || momentsModelListResponseModel.getStatus() < Constant.NET_CODE_300){
            if(momentsModelListResponseModel.getData().getTotal()==0) {
                personPagePresenter.setAdapterStates(MomentsRecyclerAdapter.NO_CONTENT);
            }
            else if(momentsModelListResponseModel.getData().getSize()==0) {
                refreshLayout.finishLoadMoreWithNoMoreData();//设置底部无更多数据
            }else {
                personPagePresenter.setPages(momentsModelListResponseModel.getData().getPages());
                personPagePresenter.setPageNum(momentsModelListResponseModel.getData().getPageNum());
                personPagePresenter.setTotalMoments(momentsModelListResponseModel.getData().getTotal());
                if(momentsModelListResponseModel.getData().getPageNum()==1) {
                    personPagePresenter.resetMomentsList(momentsModelListResponseModel.getData().getList());
                    personPagePresenter.setAdapterStates(MomentsRecyclerAdapter.NORMAL);
                    refreshLayout.setNoMoreData(false);//重置底部load的状态
                    refreshLayout.finishRefresh();//结束顶部刷新动画
                }
                else {
                    personPagePresenter.addMomentsList(momentsModelListResponseModel.getData().getList());
                    refreshLayout.finishLoadMore();//结束底部加载动画
                }

            }
        }
    }
    /**
     * @Author: omf
     * @Description: 网络请求获取服务端用户个人信息成功后的回调
     * @Param userInfoModelResponseModel
     * @Return: void
     */
    @Override
    public void queryInfoSuccess(ResponseModel<UserInfoModel> userInfoModelResponseModel) {
        if(userInfoModelResponseModel.getStatus() >= Constant.NET_CODE_200
                && userInfoModelResponseModel.getStatus() < Constant.NET_CODE_300){
            UserInfoModel userInfoModel = userInfoModelResponseModel.getData();
            bindPersonInfo(userInfoModel);
        }
    }

    /**
     * @Author: omf
     * @Description: 网络请求失败回调
     * @Param e
     * @Return: void
     */
    @Override
    public void queryError(Throwable e) {
        Toast.makeText(this,"网络连接开小差了...",Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    public void bindPersonInfo(UserInfoModel userInfoModel){
        Glide.with(this).load(userInfoModel.getPictureUrl()).into(personPhoto);
        followsView.setText("关注 "+String.valueOf(userInfoModel.getFollowingNum()));
        fansView.setText("粉丝 "+String.valueOf(userInfoModel.getFansNum()));
        ageTab.setText(String.valueOf(userInfoModel.getAge())+"岁");
        sexTab.setText(userInfoModel.getSex());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== MomentsPresenter.RESULT_CODE&&resultCode== Activity.RESULT_OK){
            int position = data.getIntExtra("position",0);
            int commentsNum = data.getIntExtra("commentsNum",0);
            Log.d("momentsview", "onActivityResult: "+String.valueOf(position)+"  "+String.valueOf(commentsNum));
           personPagePresenter.notifyCommentsNumChange(position,commentsNum);
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, null);//QZONE分享后的回调
    }

    @Override
    public void shareToQZone(Bundle params) {
        mTencent.shareToQzone(PersonPageView.this, params,null);
    }


    @Override
    public void queryRelationSuccess(ResponseModel<FollowingRelation> followingRelationResponseModel) {
        if(followingRelationResponseModel.getStatus() >= Constant.NET_CODE_200
                && followingRelationResponseModel.getStatus() < Constant.NET_CODE_300) {
            if(followingRelationResponseModel.getData().getStates()) {
                followingButton.setText("已关注");
                followingButton.setBackgroundColor(Color.parseColor("#80D3D3D3"));
            }else{
                followingButton.setText("+ 关注");
                followingButton.setBackgroundColor(Color.parseColor("#90FF0000"));
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        personPagePresenter.onViewDestroyed();
        super.onDestroy();
    }
}
