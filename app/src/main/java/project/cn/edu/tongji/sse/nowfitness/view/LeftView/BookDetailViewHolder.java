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
    private ImageView bookImg;//书籍封面
    private Context mContext;//场景
    private final int TAG_NUMS = 3;//书籍标签数量
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

    /**
     * @Author: omf
     * @Description: 将一本书的数据绑定到这个viewholder上
     * @Param bookModel 书籍对象
     * @Return: void
     */
    public void onBindData(DoubanBookModel bookModel){
        title.setText(bookModel.getTitle());
        if(bookModel.getAuthor().size()>0) {
            author.setText("作者:" + bookModel.getAuthor().get(0));
        }else{
            author.setText("作者:" + "暂无");
        }
        ratesNum.setText(String.valueOf(bookModel.getRating().getNumRaters())+"人评分");
        rating.setText("评分"+String.valueOf(bookModel.getRating().getAverage()));
        description.setText(bookModel.getSummary());
        pubDate.setText(bookModel.getPubdate()+"出版");
        String tagList="";
        int count = 0;
        //设置书籍的标签，上限三个
        for(DoubanBookModel.TagsBean t :bookModel.getTags()){
            tagList = tagList +  t.getName();
            count++;
            if(count==TAG_NUMS) {
                break;
            }
        }
        tag.setText(tagList);
        //加载书籍封面
        Glide.with(mContext)
                .load(bookModel.getImage())
                .into(bookImg);
    }
}
