package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;

/**
 * Created by a on 2018/11/23.
 */

public class BookPresenter {
    private Banner bookBanner;
    private List<String> title;
    private List<Integer> images;
    public void setBookBanner(Banner banner){
        this.bookBanner=banner;
    }
    public BookPresenter(){
        title = new ArrayList<>();
        images = new ArrayList<>();
        title.add("fuck1");
        title.add("fuck2");
        title.add("fuck3");
        images.add(R.drawable.test);
        images.add(R.drawable.test);
        images.add(R.drawable.test);
    }
    public void initBannner(){
        bookBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片集合
        bookBanner.setImageLoader(new MyImageLoader());
        bookBanner.setImages(images);
        //设置bookBanner动画效果
        bookBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当bookBanner样式有显示title时）
        bookBanner.setBannerTitles(title);
        //设置自动轮播，默认为true
        bookBanner.isAutoPlay(true);
        //设置轮播时间
        bookBanner.setDelayTime(1500);
        //设置指示器位置（当bookBanner模式中有指示器时）
        bookBanner.setIndicatorGravity(BannerConfig.LEFT);
        bookBanner.start();
    }
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
}
