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
 * Created by a on 2018/12/21.
 */

public class BookViewPagerAdapter extends PagerAdapter {
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private  List<DoubanBookModel> bookModelList;
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BookDetailViewHolder viewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.book_detai_item ,
                        null ,false);
            viewHolder = new BookDetailViewHolder(mContext,convertView);
            convertView.setTag(viewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            viewHolder = (BookDetailViewHolder) convertView.getTag();
        }
        viewHolder.onBindData(bookModelList.get(position));
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }
}
