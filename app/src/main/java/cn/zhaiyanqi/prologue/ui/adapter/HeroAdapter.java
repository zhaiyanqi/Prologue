package cn.zhaiyanqi.prologue.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.bean.HeroBean;

public class HeroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HeroBean> mList;

    public HeroAdapter(@NonNull List<HeroBean> pList) {
        mList = pList;
    }

    public void setData(@NonNull List<HeroBean> pList) {
        mList = pList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_hero_bean, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HeroBean heroBean = mList.get(position);
        ((ViewHolder)holder).textViewName.setText(heroBean.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewInfo;
        private ImageView imgHeader;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.tv_name);
            textViewInfo = view.findViewById(R.id.tv_info);
            imgHeader = view.findViewById(R.id.iv_top_photo);
            cardView = view.findViewById(R.id.cv_root);
        }
    }
}
