package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


import com.youth.banner.Banner;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.BookPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.LeftFragmentPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsView;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by a on 2018/11/22.
 */

public class LeftFragment extends Fragment {


    private Banner bookBanner;
    private AppBarLayout   appBarLayout;
    private Toolbar mToolbar;
    private ViewPager newsFeedViewPager;
    private TabLayout tabBar;

    private LeftFragmentPresenter leftFragmentPresenter;
    private BookPresenter bookPresenter;
    private View myView;
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leftFragmentPresenter = new LeftFragmentPresenter(this);
        bookPresenter = new BookPresenter();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.left_fragment,container,false);
        mAppCompatActivity=(AppCompatActivity)getActivity();
        leftFragmentPresenter.initView();
        bookPresenter.initBannner();
        return myView;
    }

    public void initView(){

        mToolbar=(Toolbar)myView.findViewById(R.id.leftfragment_toolbar);
        mAppCompatActivity.setSupportActionBar(mToolbar);
        mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);

        bookBanner= (Banner)myView.findViewById(R.id.book_banner);
        bookPresenter.setBookBanner(bookBanner);

        appBarLayout = (AppBarLayout) myView.findViewById(R.id.leftfragment_app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) myView.findViewById(R.id.leftfragment_collapsing_toolBar);
        mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorAccent));

        newsFeedViewPager = myView.findViewById(R.id.news_feed_viewPager);

        tabBar = myView.findViewById(R.id.tab_bar);

        FragmentManager fragmentManager = getChildFragmentManager();
        newsFeedViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {

                return MomentsView.newInstance("following");
            }
            @Override
            public int getCount() {
                return 2;
            }
        });
        initEvent();
    }

    private void initEvent(){
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset<=-bookBanner.getHeight()/2){
                    mCollapsingToolbarLayout.setTitle("动态");
                }else{
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });
        newsFeedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabBar));
        tabBar.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(newsFeedViewPager));//到交互双向联动，也就是点击tab，viewpager就会去变动，滑动viewpager，tab也会自动变,只有在仅仅文字颜色变动时才可以使用法该种方法
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e(TAG, "onCreateOptionsMenu()");
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }



}
