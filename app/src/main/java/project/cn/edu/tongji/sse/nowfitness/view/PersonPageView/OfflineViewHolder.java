package project.cn.edu.tongji.sse.nowfitness.view.PersonPageView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;

/**
 * Created by a on 2018/12/14.
 */

public class OfflineViewHolder  extends RecyclerView.ViewHolder{
    private TextView textView;

    public OfflineViewHolder(View itemview){
        super(itemview);
        textView = (TextView) itemview.findViewById(R.id.network_text);
    }
    public void setTextView(String message){
        textView.setText(message);
    }
}
