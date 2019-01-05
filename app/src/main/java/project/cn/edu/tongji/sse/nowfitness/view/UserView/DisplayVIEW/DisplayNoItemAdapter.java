package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by LK on 2018/11/28.
 */

public class DisplayNoItemAdapter extends RecyclerView.Adapter<DisplayViewNoItemHolder> {


    @NonNull
    @Override
    public DisplayViewNoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DisplayViewNoItemHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewNoItemHolder holder, int position) {
        //DO NOTHING
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
