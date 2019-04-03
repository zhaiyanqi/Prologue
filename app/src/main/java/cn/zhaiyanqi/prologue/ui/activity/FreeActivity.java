package cn.zhaiyanqi.prologue.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import me.caibou.rockerview.DirectionView;

public class FreeActivity extends AppCompatActivity
        implements DirectionView.DirectionChangeListener {

    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;
    @BindView(R.id.direct_control)
    DirectionView directionView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ViewAdapter adapter;
    private List<ViewBean> views;
    private ViewBean currentView;
    private boolean loadingDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        directionView.setDirectionChangeListener(this);
//        views = new ArrayList<>();
//        adapter = new ViewAdapter(views);
//        recyclerView.setAdapter(adapter);
        addView();
    }

    @OnClick({R.id.iv_width_add, R.id.iv_width_reduce,
            R.id.iv_height_add, R.id.iv_height_reduce})
    void adjustCurView(View view) {
        if (currentView == null) return;
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) currentView.getView().getLayoutParams();
        int step = 10;
        switch (view.getId()) {
            case R.id.iv_width_add: {
                layoutParams.width += step;
                break;
            }
            case R.id.iv_width_reduce: {
                layoutParams.width -= step;
                break;
            }
            case R.id.iv_height_add: {
                layoutParams.height += step;
                break;
            }
            case R.id.iv_height_reduce: {
                layoutParams.height -= step;
                break;
            }
        }
        currentView.getView().requestLayout();
    }

    @OnClick({R.id.iv_add_image_view, R.id.iv_add_text_view})
    void addCustomView(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image_view: {
                addImageView();
                break;
            }
            case R.id.iv_add_text_view: {
                addTextView();
                break;
            }
        }
    }

    private void addTextView() {

    }

    private void addImageView() {
        if (loadingDialog) return;
        loadingDialog = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.layout_add_image_view, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()));
        builder.show();
        loadingDialog = false;
    }

    @OnCheckedChanged(R.id.cb_visible)
    void setViewVisible(boolean checked) {
        if (currentView == null || currentView.getView() == null) return;
        currentView.getView().setVisibility(checked ? View.VISIBLE : View.GONE);
    }

    private void addView() {
        View view = new View(this);
        view.setBackgroundColor(Color.BLUE);
        mainLayout.addView(view);
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = 100;
        layoutParams.height = 100;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        view.requestLayout();
        currentView = new ViewBean("test", view);
    }

    @Override
    public void onDirectChange(DirectionView.Direction direction) {
        if (currentView == null) return;
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) currentView.getView().getLayoutParams();
        int step = 30;
        switch (direction) {
            case UP: {
                layoutParams.topMargin -= step;
                break;
            }
            case UP_AND_LEFT: {
                layoutParams.topMargin -= step;
                layoutParams.leftMargin -= step;
                break;
            }
            case LEFT: {
                layoutParams.leftMargin -= step;
                break;
            }
            case DOWN_AND_LEFT: {
                layoutParams.leftMargin -= step;
                layoutParams.topMargin += step;
                break;
            }
            case DOWN: {
                layoutParams.topMargin += step;
                break;
            }
            case DOWN_AND_RIGHT: {
                layoutParams.topMargin += step;
                layoutParams.leftMargin += step;
                break;
            }
            case RIGHT: {
                layoutParams.leftMargin += step;
                break;
            }
            case UP_AND_RIGHT: {
                layoutParams.topMargin -= step;
                layoutParams.leftMargin += step;
                break;
            }
        }
        currentView.getView().requestLayout();
//        adapter.notifyDataSetChanged();
    }
}