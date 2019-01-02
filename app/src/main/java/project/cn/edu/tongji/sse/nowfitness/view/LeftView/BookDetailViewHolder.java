package project.cn.edu.tongji.sse.nowfitness.view.LeftView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.DoubanBookModel;

/**
 * Created by a on 2018/12/21.
 */

public class BookDetailViewHolder {
    private TextView title,author,rating,ratesNum,tag,description,pubDate;
    private ImageView bookImg;
    private Context mContext;
    private final int TAG_NUMS = 3;
    public  BookDetailViewHolder(Context context,View view){
        mContext = context;
        title = view.findViewById(R.id.tv_book_title);
        author = view.findViewById(R.id.tv_book_author);
        ratesNum = view.findViewById(R.id.tv_rating_num);
        tag = view.findViewById(R.id.tv_book_type);
        description =view.findViewById(R.id.tv_description);
        pubDate = view.findViewById(R.id.tv_pub_time);
        rating = view.findViewById(R.id.tv_rating);
        bookImg = view.findViewById(R.id.iv_book);
    }
    public void onBindData(DoubanBookModel bookModel){
        title.setText(bookModel.getTitle());
        author.setText("作者:"+bookModel.getAuthor().get(0));
        ratesNum.setText(String.valueOf(bookModel.getRating().getNumRaters())+"人评分");
        rating.setText("评分"+String.valueOf(bookModel.getRating().getAverage()));
        description.setText(bookModel.getSummary());
        pubDate.setText(bookModel.getPubdate()+"出版");
        String tagList="";
        int count = 0;
        for(DoubanBookModel.TagsBean t :bookModel.getTags()){
            tagList = tagList +  t.getName();
            count++;
            if(count==TAG_NUMS) {
                break;
            }
        }
        tag.setText(tagList);
        Glide.with(mContext)
                .load(bookModel.getImage())
                .into(bookImg);
    }
}
