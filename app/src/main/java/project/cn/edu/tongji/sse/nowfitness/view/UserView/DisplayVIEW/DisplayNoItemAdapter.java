package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DisplayNoItemAdapter extends RecyclerView.Adapter<DisplayViewNoItemHolder> {


    @NonNull
    @Override
    public DisplayViewNoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DisplayViewNoItemHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewNoItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
