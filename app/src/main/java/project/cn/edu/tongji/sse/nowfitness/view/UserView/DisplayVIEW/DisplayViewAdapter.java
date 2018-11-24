package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;

public class DisplayViewAdapter extends RecyclerView.Adapter<DisplayViewVHolder> {
    private List<IndividualModel> individualModels;
    public DisplayViewAdapter(List<IndividualModel> list){individualModels = list;}



    @NonNull
    @Override
    public DisplayViewVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new DisplayViewVHolder(layoutInflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewVHolder holder, int position) {
        IndividualModel individualModel = individualModels.get(position);
        holder.bind(individualModel);
    }

    @Override
    public int getItemCount() {
        return individualModels.size();
    }
}
