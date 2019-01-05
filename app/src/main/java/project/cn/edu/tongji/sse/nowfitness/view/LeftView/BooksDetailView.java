package project.cn.edu.tongji.sse.nowfitness.view.LeftView;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;

public class BooksDetailView extends AppCompatActivity {

    private List<DoubanBookModel> bookList;//书籍数据的列表
    private ViewPager booksViewPager;//展示书籍的viewpager
    private int initPos;//viewpager显示时的初始位置
    private Toolbar mToolbar;//自设的toolbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bookList = (List<DoubanBookModel>)intent.getSerializableExtra("books");//通过intent获得书籍列表数据
        initPos = intent.getIntExtra("position",0);//获得viewpager初始位置
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_detail_view);
        mToolbar = (Toolbar)findViewById(R.id.book_toolbar);
        booksViewPager = (ViewPager)findViewById(R.id.book_viewpager);
        booksViewPager.setAdapter(new BookViewPagerAdapter(bookList,this));
        booksViewPager.setCurrentItem(initPos);//设置viewPager初始位置
        setToolbar();

    }
    /**
     * @Author: omf
     * @Description: 设定toolbar
     * @Param
     * @Return: void
     */
    private void setToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /**
     * @Author: omf
     * @Description: toolbar上的返回菜单按钮被触发后的处理事件
     * @Param item
     * @Return: boolean 点击事件是否会再向下一个事件处理方法传递了
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();//结束该activity
        }
        return true;
    }
}
