package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;
import project.cn.edu.tongji.sse.nowfitness.view.LeftView.BookMethod;
import project.cn.edu.tongji.sse.nowfitness.view.LeftView.BooksDetailView;

/**
 * Created by a on 2018/11/23.
 */

public class BookPresenter extends BasePresenter{
    private Banner bookBanner;
    private List<DoubanBookModel> booksLab;
    private List<String> title;
    private List<String> images;
    private Context mContext;
    private BookMethod bookMethod;
    public void setBookBanner(Banner banner){
        this.bookBanner=banner;
    }
    public void setBooksLab(List<DoubanBookModel> bookModelList){this.booksLab = bookModelList;}
    public BookPresenter(Context context,BookMethod bookMethod){
        mContext = context;
        title = new ArrayList<>();
        images = new ArrayList<>();
        booksLab = new ArrayList<>();
        this.bookMethod = bookMethod;
    }
    public int getBooNum(){
        return booksLab.size();
    }
    public void initBanner(){
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
    public void jumpToBookDetail(int position){
        Intent intent = new Intent(mContext,BooksDetailView.class);
        intent.putExtra("books",(Serializable) booksLab);
        intent.putExtra("position",position);
        mContext.startActivity(intent);

    }
    public void resetBanner(){
        title = new ArrayList<>();
        images = new ArrayList<>();
        for(DoubanBookModel book : booksLab){
            title.add(book.getTitle());
            images.add(book.getImage());
        }
        initBanner();
    }
    private class MyImageLoader extends ImageLoader {//轮播图的图片加载类重写
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
    public void getBookInfo(String tag, int start, int count){
        subscriptions.add(douBanAPIRepository.getBookInfo(tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookMethod::querySuccess,bookMethod::queryError)
        );
    }
}
