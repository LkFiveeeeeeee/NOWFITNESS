package project.cn.edu.tongji.sse.nowfitness.view.LeftView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;

 /**
  *
  * @Package:
  * @ClassName:      BookViewPagerAdapter.java
  * @Description:    书籍viewpager的适配器
  * @Author:         omf
  * @UpdateDate:     2019/1/4 23:17
  */
public class BookViewPagerAdapter extends PagerAdapter {
    private LinkedList<View> mViewCache = null;//为viewpager设定的一个缓存器
    private Context mContext ;//场景
    private LayoutInflater mLayoutInflater = null;//该viewpager每个page的布局加载器
    private  List<DoubanBookModel> bookModelList;//书籍数据列表
    public BookViewPagerAdapter(List<DoubanBookModel> bookModelList, Context context){
        super();
        this.mContext = context ;
        this.bookModelList = bookModelList;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
    }
    @Override
    public int getCount() {
        return bookModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
    /**
     * @Author: omf
     * @Description: view被销毁回收时调用
     * @Param container
     * @Param position
     * @Param object
     * @Return: void
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);//将不用的view加入到缓存器中，以便复用该view
    }

    /**
     * @Author: omf
     * @Description: 初始化view
     * @Param container
     * @Param position
     * @Return: java.lang.Object
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BookDetailViewHolder viewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){//缓存器中没有可复用的view
            convertView = this.mLayoutInflater.inflate(R.layout.book_detai_item ,
                        null ,false);//创建一个新view
            viewHolder = new BookDetailViewHolder(mContext,convertView);
            convertView.setTag(viewHolder);
        }else {
            convertView = mViewCache.removeFirst();//直接复用的缓存器中的view
            viewHolder = (BookDetailViewHolder) convertView.getTag();
        }
        viewHolder.onBindData(bookModelList.get(position));//根据位置绑定对应的书籍对象
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }
}
