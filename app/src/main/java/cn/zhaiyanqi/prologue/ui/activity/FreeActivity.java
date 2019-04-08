package cn.zhaiyanqi.prologue.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;
import cn.zhaiyanqi.prologue.ui.bean.PadSettingBean;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import cn.zhaiyanqi.prologue.ui.widget.AddImageViewDialog;
import cn.zhaiyanqi.prologue.ui.widget.PadSettingPopup;
import cn.zhaiyanqi.prologue.ui.widget.ViewPopupView;
import cn.zhaiyanqi.prologue.utils.HawkKey;
import me.caibou.rockerview.DirectionView;

public class FreeActivity extends AppCompatActivity
        implements DirectionView.DirectionChangeListener {

    private static final int SELECT_IMAGE_REQUEST_CODE = 1;
    @BindView(R.id.main_layout)
    ConstraintLayout mainLayout;
    @BindView(R.id.direct_control)
    DirectionView directionView;

    private ViewAdapter adapter;
    private List<ViewBean> views;
    private ViewBean currentView;
    private AddImageViewDialog imageViewDialog = null;
    private int viewCount = 0;
    private int mStep = 5;
    private int sStep = 5;

    //popup view
    private ViewPopupView popupView;
    private PadSettingPopup padSettingPopup;
    private CenterListPopupView groupSelectPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mStep = Hawk.get(HawkKey.MOVE_STEP_LENGTH, 5);
        sStep = Hawk.get(HawkKey.SCALE_STEP_LENGTH, 5);
    }

    private void initView() {
        directionView.setDirectionChangeListener(this);
        views = new ArrayList<>();
        adapter = new ViewAdapter(views);
        adapter.setItemSelectedListener(bean -> {
            currentView = bean;
            if (popupView != null) {
                popupView.setTvTitle(currentView.getName());
            }
        });
        adapter.setOnSettingsListener(this::showViewSettings);
        adapter.setOnItemRemoveListener(this::removeView);

        //popup view
        padSettingPopup = new PadSettingPopup(this)
                .setMoveStep(mStep)
                .setScaleStep(sStep)
                .setLayoutWidth(mainLayout.getWidth())
                .setLayoutHeight(mainLayout.getHeight())
                .setConfirmListener(this::setPadSettings);

        groupSelectPopup = new XPopup.Builder(this)
                .asCenterList("请选择一个势力模板:", new String[]{"魏", "蜀", "吴", "群", "神"},
                        (position, text) -> {
                            groupSelectPopup.dismissWith(this::addHeroTemplete);
                        });
    }

    private void setPadSettings(PadSettingBean bean) {
        this.mStep = bean.getMoveStep();
        this.sStep = bean.getScaleStep();
        Hawk.put(HawkKey.MOVE_STEP_LENGTH, mStep);
        Hawk.put(HawkKey.SCALE_STEP_LENGTH, sStep);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
        layoutParams.width = bean.getMainLayoutWidth();
        layoutParams.height = bean.getMainLayoutHeight();
        mainLayout.requestLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_free_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_hero_template: {
                addTemplate();
                break;
            }
            case R.id.action_card_template: {
                break;
            }
            case R.id.action_about: {
                break;
            }
        }
        return true;
    }

    @OnClick({R.id.iv_width_add, R.id.iv_width_reduce,
            R.id.iv_height_add, R.id.iv_height_reduce})
    void adjustCurView(View view) {
        if (currentView == null) return;
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) currentView.getView().getLayoutParams();
        switch (view.getId()) {
            case R.id.iv_width_add: {
                if (layoutParams.width < 0) {
                    layoutParams.width = currentView.getView().getWidth() + sStep;
                } else {
                    layoutParams.width += sStep;
                }
                break;
            }
            case R.id.iv_width_reduce: {
                if (layoutParams.width <= 0) return;
                layoutParams.width -= sStep;
                break;
            }
            case R.id.iv_height_add: {
                if (layoutParams.height < 0) {
                    layoutParams.height = currentView.getView().getHeight() + sStep;
                } else {
                    layoutParams.height += sStep;
                }

                break;
            }
            case R.id.iv_height_reduce: {
                if (layoutParams.height <= 0) return;
                layoutParams.height -= sStep;
                break;
            }
        }
        currentView.getView().requestLayout();
    }

    @OnClick({R.id.iv_add_image_view, R.id.iv_add_text_view, R.id.iv_move_setting, R.id.iv_show_list})
    void addCustomView(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image_view: {
                selectViewType();
                break;
            }
            case R.id.iv_add_text_view: {
                addTextView();
                break;
            }
            case R.id.iv_move_setting: {
                changePadSettings();
                break;
            }
            case R.id.iv_show_list: {
                popupView = new ViewPopupView(this, adapter);
                popupView.setTitle("当前选中控件:" + (currentView == null ? "无" : currentView.getName()));
                new XPopup.Builder(this)
                        .autoDismiss(true)
                        .dismissOnTouchOutside(true)
                        .enableDrag(false)
                        .asCustom(popupView)
                        .show();
                break;
            }
        }
    }

    private void changePadSettings() {
        new XPopup.Builder(this)
                .asCustom(padSettingPopup.setLayoutWidth(mainLayout.getWidth())
                        .setLayoutHeight(mainLayout.getHeight()))
                .show();
    }

    private void addTextView() {

    }


    private void selectViewType() {
        new XPopup.Builder(this)
                .asCenterList("请选择一项",
                        new String[]{"条目1", "条目2", "条目3", "条目4"},
                        (position, text) -> {
                            addImageView();
                        })
                .show();
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

    @Override
    public void onDirectChange(DirectionView.Direction direction) {
        if (currentView == null) return;
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) currentView.getView().getLayoutParams();
        switch (direction) {
            case UP: {
                layoutParams.topMargin -= mStep;
                break;
            }
            case UP_AND_LEFT: {
                layoutParams.topMargin -= mStep;
                layoutParams.leftMargin -= mStep;
                break;
            }
            case LEFT: {
                layoutParams.leftMargin -= mStep;
                break;
            }
            case DOWN_AND_LEFT: {
                layoutParams.leftMargin -= mStep;
                layoutParams.topMargin += mStep;
                break;
            }
            case DOWN: {
                layoutParams.topMargin += mStep;
                break;
            }
            case DOWN_AND_RIGHT: {
                layoutParams.topMargin += mStep;
                layoutParams.leftMargin += mStep;
                break;
            }
            case RIGHT: {
                layoutParams.leftMargin += mStep;
                break;
            }
            case UP_AND_RIGHT: {
                layoutParams.topMargin -= mStep;
                layoutParams.leftMargin += mStep;
                break;
            }
        }
        currentView.getView().requestLayout();
    }

    public void addTemplate() {
        groupSelectPopup.show();
    }

    private void addHeroTemplete() {
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

    private void removeView(ViewBean bean) {
        mainLayout.removeView(bean.getView());
        if (currentView == bean) {
            currentView = null;
            popupView.setTvTitle("无");

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
        if (popupView != null) {
            popupView.dismissWith(builder::show);
        }
    }
}
