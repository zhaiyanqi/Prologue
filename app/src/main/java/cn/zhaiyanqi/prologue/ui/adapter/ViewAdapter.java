package cn.zhaiyanqi.prologue.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import cn.zhaiyanqi.prologue.ui.callback.ItemMoveListener;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>
        implements ItemMoveListener {

    private List<ViewBean> list;
    private OnItemSelectedListener l1;
    private OnSettingsClickListener l2;
    private OnItemRemoved l3;
    private OnItemSwaped l4;

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
        ViewBean bean = list.get(position);
        holder.llRoot.setOnClickListener(v -> {
            for (ViewBean bean1 : list) {
                bean1.setSelected(false);
            }
            if (l1 != null) {
                l1.onClick(bean);
            }
            bean.setSelected(true);
            notifyDataSetChanged();
        });
        holder.ivSetting.setOnClickListener(v -> {
            if (l2 != null) {
                l2.onClick(bean);
            }
        });
        holder.tvName.setText(bean.getName());
        holder.llRoot.setSelected(bean.isSelected());
        holder.cbVisible.setChecked(bean.getView().getVisibility() == View.VISIBLE);
        holder.cbVisible.setOnCheckedChangeListener((buttonView, isChecked) -> {
            bean.getView().setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemSelectedListener(OnItemSelectedListener l) {
        this.l1 = l;
    }

    public void setOnSettingsListener(OnSettingsClickListener l) {
        this.l2 = l;
    }

    public void setOnItemRemoveListener(OnItemRemoved l) {
        this.l3 = l;
    }

    public void setOnItemSwapListener(OnItemSwaped l) {
        this.l4 = l;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        View from = list.get(fromPosition).getView();
        View to = list.get(fromPosition).getView();
//        int fromOrder = list.get(fromPosition).getOrder();
//        int toOrder = list.get(toPosition).getOrder();
//        list.get(fromPosition).setOrder(toOrder);
//        list.get(toPosition).setOrder(fromOrder);
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        if (l4 != null) {
            l4.onSwap(from, to);
        }
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        ViewBean bean = list.remove(position);
        notifyItemRemoved(position);
        if (l3 != null) {
            l3.onRemove(bean);
        }
        return true;
    }

    public interface OnItemSelectedListener {
        void onClick(ViewBean bean);
    }

    public interface OnSettingsClickListener {
        void onClick(ViewBean bean);
    }

    public interface OnItemRemoved {
        void onRemove(ViewBean bean);
    }

    public interface OnItemSwaped {
        void onSwap(View from, View to);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private LinearLayout llRoot;
        private ImageView ivSetting;
        private CheckBox cbVisible;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            llRoot = itemView.findViewById(R.id.ll_root);
            ivSetting = itemView.findViewById(R.id.iv_setting);
            cbVisible = itemView.findViewById(R.id.cb_visible_);
        }
    }
}
