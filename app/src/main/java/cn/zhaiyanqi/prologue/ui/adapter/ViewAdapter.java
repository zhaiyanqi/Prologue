package cn.zhaiyanqi.prologue.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private List<ViewBean> list;
    private int curPosition = -1;
    private OnItemSelectedListener l;

    public ViewAdapter(@NonNull List<ViewBean> data) {
        list = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.llRoot.setOnClickListener(v -> {
            curPosition = position;
            if (l != null) {
                l.onClick(list.get(position));
            }
            notifyDataSetChanged();
        });
        holder.tvName.setText(list.get(position).getName());
        if (position == curPosition) {
            holder.llRoot.setSelected(true);
        } else {
            holder.llRoot.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemSelectedListener(OnItemSelectedListener l) {
        this.l = l;
    }

    public interface OnItemSelectedListener {
        void onClick(ViewBean bean);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private LinearLayout llRoot;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            llRoot = itemView.findViewById(R.id.ll_root);
        }
    }
}
