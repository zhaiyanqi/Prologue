package cn.zhaiyanqi.prologue.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import cn.zhaiyanqi.prologue.ui.callback.ViewItemHelperCallback;
import cn.zhaiyanqi.prologue.ui.widget.AddImageViewDialog;
import me.caibou.rockerview.DirectionView;

public class FreeActivity extends AppCompatActivity
        implements DirectionView.DirectionChangeListener {

    private static final int SELECT_IMAGE_REQUEST_CODE = 1;
    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;
    @BindView(R.id.direct_control)
    DirectionView directionView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.cb_visible)
    CheckBox cbCurViewVisible;

    private ViewAdapter adapter;
    private List<ViewBean> views;
    private ViewBean currentView;
    private AddImageViewDialog imageViewDialog = null;
    private int viewCount = 0;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        directionView.setDirectionChangeListener(this);
        views = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ViewAdapter(views);
        recyclerView.setAdapter(adapter);
        adapter.setItemSelectedListener(bean -> {
            currentView = bean;
            cbCurViewVisible.setChecked(currentView.getView().getVisibility() != View.GONE);
        });
        adapter.setOnSettingsListener(this::showViewSettings);
        mItemTouchHelper = new ItemTouchHelper(new ViewItemHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
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
                if (layoutParams.width < 0) {
                    layoutParams.width = currentView.getView().getWidth() + step;
                } else {
                    layoutParams.width += step;
                }
                break;
            }
            case R.id.iv_width_reduce: {
                if (layoutParams.width <= 0) return;
                layoutParams.width -= step;
                break;
            }
            case R.id.iv_height_add: {
                if (layoutParams.height < 0) {
                    layoutParams.height = currentView.getView().getHeight() + step;
                } else {
                    layoutParams.height += step;
                }

                break;
            }
            case R.id.iv_height_reduce: {
                if (layoutParams.height <= 0) return;
                layoutParams.height -= step;
                break;
            }
        }
        currentView.getView().requestLayout();
    }

    @OnClick({R.id.iv_add_image_view, R.id.iv_add_text_view, R.id.iv_settings, R.id.tv_full_screen})
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
            case R.id.iv_settings: {
                changeSettings();
                break;
            }
            case R.id.tv_full_screen: {
//                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//                requestWindowFeature(Window.FEATURE_NO_TITLE);
                break;
            }
        }
    }

    private void changeSettings() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.layout_main_layout_settings, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setTitle("请设置主布局参数");
        final EditText etWidth = view.findViewById(R.id.et_width);
        final EditText etHeight = view.findViewById(R.id.et_height);
        builder.setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()));
        builder.setPositiveButton("确定", (dialog, which) -> {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
            layoutParams.width = Integer.parseInt(etWidth.getText().toString());
            layoutParams.height = Integer.parseInt(etHeight.getText().toString());
            mainLayout.requestLayout();
            addTemplate();
        });
        builder.show();
    }

    private void addTextView() {

    }

    private void addImageView() {
        imageViewDialog = new AddImageViewDialog(this);
        imageViewDialog.setImportListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra("crop", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
        });
        imageViewDialog.setPositiveListener((dialog, which) ->
                createImageView(imageViewDialog.getUri(),
                        imageViewDialog.getWidth(), imageViewDialog.getHeight()));
        imageViewDialog.show();

    }

    private void createImageView(Uri uri, int width, int height) {
        addView(new ViewBean()
                .setView(new ImageView(this))
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setWidth(width)
                .setHeight(height)
                .setName("自定义View")
                .setUri(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case SELECT_IMAGE_REQUEST_CODE: {
                    if (imageViewDialog != null) {
                        imageViewDialog.setUri(data.getData());
                    }
                    break;
                }
            }
        }
    }

    @OnCheckedChanged(R.id.cb_visible)
    void setViewVisible(boolean checked) {
        if (currentView == null || currentView.getView() == null) return;
        currentView.getView().setVisibility(checked ? View.VISIBLE : View.GONE);
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

    public void addTemplate() {
        ViewBean bean = new ViewBean()
                .setView(new ImageView(this))
                .setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT)
                .setHeight(ConstraintLayout.LayoutParams.MATCH_PARENT)
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setUri(R.drawable.wei)
                .setOrder(viewCount++)
                .setName("边框");
        addView(bean);
        ViewBean bean2 = new ViewBean()
                .setView(new ImageView(this))
                .setWidth(Hawk.get("势力_width", 206))
                .setHeight(Hawk.get("势力_height", 255))
                .setUri(R.drawable.wei_logo)
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setOrder(viewCount++)
                .setName("势力");
        addView(bean2);
        ViewBean bean3 = new ViewBean()
                .setView(new ImageView(this))
                .setWidth(Hawk.get("技能背板_width", 699))
                .setHeight(Hawk.get("技能背板_height", 247))
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setUri(R.drawable.wei_skill_board)
                .setOrder(viewCount++)
                .setName("技能背板");
        addView(bean3);
    }

    private void addView(@NonNull ViewBean bean) {
        views.add(bean);
        adapter.notifyDataSetChanged();
        View view1 = bean.getView();
        if (view1 instanceof ImageView) {
            ImageView view = (ImageView) view1;
            mainLayout.addView(bean.getView());
            ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.leftMargin = Hawk.get(bean.getName() + "_leftMargin", 0);
            layoutParams.topMargin = Hawk.get(bean.getName() + "_topMargin", 0);
            layoutParams.width = bean.getWidth();
            layoutParams.height = bean.getHeight();
            if (bean.getScaleType() != null) {
                view.setScaleType(bean.getScaleType());
            }
            view.requestLayout();
            Glide.with(this)
                    .load(bean.getUri() != null ?
                            bean.getUri() : bean.getResId())
                    .into(view);
        }
    }


    private void showViewSettings(ViewBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.layout_view_settings, null);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setTitle(bean.getName() + "参数");
        builder.setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()));
        builder.setPositiveButton("确定", (dialog, which) -> {

        });
        builder.show();
    }

}
