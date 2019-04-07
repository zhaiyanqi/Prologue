package cn.zhaiyanqi.prologue.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;
import cn.zhaiyanqi.prologue.ui.callback.ViewItemHelperCallback;

@SuppressLint("ViewConstructor")
public class ViewPopupView extends BottomPopupView {

    RecyclerView recyclerView;
    private ViewAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    public ViewPopupView(@NonNull Context context, ViewAdapter adapter) {
        super(context);
        this.adapter = adapter;
        mItemTouchHelper = new ItemTouchHelper(new ViewItemHelperCallback(adapter));
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_view_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(new ColorDrawable(getResources().getColor(R.color._xpopup_list_divider)));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .5f);
    }

    @Override
    public int getMinimumHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .3f);
    }
}
