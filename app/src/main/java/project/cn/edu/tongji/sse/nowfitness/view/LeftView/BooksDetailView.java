package project.cn.edu.tongji.sse.nowfitness.view.LeftView;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;

public class BooksDetailView extends AppCompatActivity {

    private List<DoubanBookModel> bookList;
    private ViewPager booksViewPager;
    private int initPos;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bookList = (List<DoubanBookModel>)intent.getSerializableExtra("books");
        initPos = intent.getIntExtra("position",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_detail_view);
        mToolbar = (Toolbar)findViewById(R.id.book_toolbar);
        booksViewPager = (ViewPager)findViewById(R.id.book_viewpager);
        booksViewPager.setAdapter(new BookViewPagerAdapter(bookList,this));
        booksViewPager.setCurrentItem(initPos);
    }

}
