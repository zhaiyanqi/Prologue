package cn.zhaiyanqi.prologue.ui.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;

@SuppressLint("ViewConstructor")
public class BottomListPopup extends BottomPopupView {

    RecyclerView recyclerView;
    private ViewAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;
    private TextView tvTitle;
    private String title;
    private ImageView ivHelp;

    public BottomListPopup(@NonNull Context context, ViewAdapter adapter) {
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
        tvTitle = findViewById(R.id.tv_title);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(new ColorDrawable(getResources().getColor(R.color._xpopup_list_divider)));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        ivHelp = findViewById(R.id.iv_help);
        ivHelp.setOnClickListener(v -> {
            String info = "\r\t1、点击每条记录前的图标可控制控件显示/隐藏。" +
                    "\r\n\t2、长按控件可以拖拽移动控件，越靠下的控件图层越高。" +
                    "\r\n\t3、点击控件可选中为当前操作控件，被选择的控件可使用界面上的“手柄”进行控制。" +
                    "\r\n\t4、左划或右划可删除控件。";
            new XPopup.Builder(getContext()).asConfirm("帮助", info, null).hideCancelBtn().show();
        });
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .5f);
    }

    @Override
    public int getMinimumHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .3f);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTvTitle(String text) {
        if (!TextUtils.isEmpty(text) && tvTitle != null) {
            String string = "当前选中控件:" + text;
            tvTitle.setText(string);
        }
    }

    public interface ItemMoveListener {
        boolean onItemMove(int fromPosition, int toPosition);

        void onItemRemove(int position);
    }

    public static class ViewItemHelperCallback extends ItemTouchHelper.Callback {


        private ItemMoveListener mItemMoveListener;

        private ViewItemHelperCallback(ItemMoveListener itemMoveListener) {
            mItemMoveListener = itemMoveListener;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return mItemMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mItemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    }
}
