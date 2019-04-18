package cn.zhaiyanqi.prologue.ui.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.adapter.ViewAdapter;
import cn.zhaiyanqi.prologue.ui.bean.IllustrationPopupBean;
import cn.zhaiyanqi.prologue.ui.bean.PadSettingBean;
import cn.zhaiyanqi.prologue.ui.bean.ViewBean;
import cn.zhaiyanqi.prologue.ui.popup.AddImagePopup;
import cn.zhaiyanqi.prologue.ui.popup.AddTextPopup;
import cn.zhaiyanqi.prologue.ui.popup.BottomListPopup;
import cn.zhaiyanqi.prologue.ui.popup.ConfigImagePopup;
import cn.zhaiyanqi.prologue.ui.popup.ConfigTextPopup;
import cn.zhaiyanqi.prologue.ui.popup.IllustrationPopup;
import cn.zhaiyanqi.prologue.ui.popup.LayoutSettingPopup;
import cn.zhaiyanqi.prologue.ui.popup.NameTextPopup;
import cn.zhaiyanqi.prologue.ui.popup.SkillInfoPopup;
import cn.zhaiyanqi.prologue.ui.popup.SkillNamePopup;
import cn.zhaiyanqi.prologue.ui.popup.TitleTextPopup;
import cn.zhaiyanqi.prologue.ui.widget.DragableLayout;
import cn.zhaiyanqi.prologue.ui.widget.PinchImageView;
import cn.zhaiyanqi.prologue.utils.HawkKey;
import me.caibou.rockerview.DirectionView;

public class FreeActivity extends AppCompatActivity
        implements DirectionView.DirectionChangeListener, ColorPickerDialogListener {

    private static final String[] groups = {"魏", "蜀", "吴", "群", "神"};
    private static final String[] perfabList = {"武将模板", "卡牌模板", "边框", "势力", "称号", "武将名", "插画", "勾玉(身份)", "勾玉x1(国战)", "勾玉x0.5(国战)", "技能名", "技能名背板", "技能描述", "技能描述背板"};
    private static final int[] groupResIds = {R.drawable.wei, R.drawable.shu, R.drawable.wu, R.drawable.qun, R.drawable.god};
    private static final int[] logoResIds = {R.drawable.wei_logo, R.drawable.shu_logo, R.drawable.wu_logo, R.drawable.qun_logo, R.drawable.god_logo};
    private static final int[] hpStdResIds = {R.drawable.wei_hp, R.drawable.shu_hp, R.drawable.wu_hp, R.drawable.qun_hp, R.drawable.god_hp};
    private static final int[] skillBoardResIds = {R.drawable.wei_skill_board, R.drawable.shu_skill_board, R.drawable.wu_skill_board, R.drawable.qun_skill_board, R.drawable.god_skill_board};
    private static final int[] hpGuozhanResIds = {R.drawable.wei_hp_double, R.drawable.shu_hp_double, R.drawable.wu_hp_double, R.drawable.qun_hp_double, R.drawable.god_hp_double};
    private static final int[] hpGuozhanHalfResIds = {R.drawable.wei_hp_half, R.drawable.shu_hp_half, R.drawable.wu_hp_half, R.drawable.qun_hp_half, R.drawable.god_hp_half};
    private static final int[] skillBarResIds = {R.drawable.wei_skill_bar, R.drawable.shu_skill_bar, R.drawable.wu_skill_bar, R.drawable.qun_skill_bar, R.drawable.god_skill_bar};


    private static final int SELECT_ADD_IMAGE_REQUEST_CODE = 1;
    private static final int SELECT_ILLUSTRATION_IMAGE_REQUEST_CODE = 2;
    private static final int SELECT_CONFIG_IMAGE_REQUEST_CODE = 3;
    @BindView(R.id.main_layout)
    DragableLayout mainLayout;
    @BindView(R.id.direct_control)
    DirectionView directionView;
    @BindView(R.id.tv_cur_view)
    TextView tvCurView;
    @BindView(R.id.ll_scale)
    LinearLayout llScale;
    @BindView(R.id.switch_scale)
    Switch switchScale;

    private ViewAdapter adapter;
    private List<ViewBean> views;
    private ViewBean currentView;
    private int mStep = 5;
    private int sStep = 5;

    //popup view
    private BottomListPopup popupView;
    private LayoutSettingPopup layoutSettingPopup;
    private CenterListPopupView groupSelectPopup;
    private CenterListPopupView customViewPopup;
    private IllustrationPopup illustrationPopup;
    private TitleTextPopup titleTextPopup;
    private NameTextPopup nameTextPopup;
    private SkillNamePopup skillNamePopup;
    private SkillInfoPopup skillInfoPopup;
    private AddImagePopup addImagePopup;
    private AddTextPopup addTextPopup;
    //non-inited
    private ConfigTextPopup configTextPopup;
    private ConfigImagePopup configImagePopup;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        initData();
        initView();
        initPopup();
    }

    private void initData() {
        views = new ArrayList<>();
        mStep = Hawk.get(HawkKey.MOVE_STEP_LENGTH, 5);
        sStep = Hawk.get(HawkKey.SCALE_STEP_LENGTH, 5);
    }

    private void initView() {
        directionView.setDirectionChangeListener(this);
        adapter = new ViewAdapter(views);
        adapter.setItemSelectedListener(this::onViewBeanSelected);
        adapter.setOnSettingsListener(this::showViewSettings);
        adapter.setOnItemRemoveListener(this::removeView);
        adapter.setOnItemSwapListener(this::swapView);
        mainLayout.setViewSelectedListener(this::onViewSelected);
    }

    private void initPopup() {
        layoutSettingPopup = new LayoutSettingPopup(this)
                .setMoveStep(mStep)
                .setScaleStep(sStep)
                .setLayoutWidth(mainLayout.getWidth())
                .setLayoutHeight(mainLayout.getHeight())
                .setConfirmListener(this::setPadSettings);

        groupSelectPopup = new XPopup.Builder(this).asCenterList("请选择一个势力模板:", groups,
                (position, text) -> {
                    if (position >= 0) {
                        groupSelectPopup.dismissWith(() -> addHeroTemplate(position, text));
                    }
                });
        illustrationPopup = new IllustrationPopup(this).setSelectImageListener(() -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra("crop", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, SELECT_ILLUSTRATION_IMAGE_REQUEST_CODE);
        }).setConfirmListener(this::createIllustration);

        titleTextPopup = new TitleTextPopup(this)
                .setConfirmListener(this::addTextView);
        nameTextPopup = new NameTextPopup(this)
                .setConfirmListener(this::addTextView);
        skillNamePopup = new SkillNamePopup(this)
                .setConfirmListener(this::addTextView);
        skillInfoPopup = new SkillInfoPopup(this)
                .setConfirmListener(this::addTextView);
        addImagePopup = new AddImagePopup(this)
                .setSelectImageListener(() -> {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, SELECT_ADD_IMAGE_REQUEST_CODE);
                }).setConfirmListener(this::addView);
        addTextPopup = new AddTextPopup(this)
                .setConfirmListener(this::addView);

    }

    private void addTextView(TextView textView) {
        String string = textView.getText().toString();
        final String name = string.length() > 4 ? string.substring(0, 4) : string;
        runOnUiThread(() -> addView(new ViewBean()
                .setName("文字:" + name)
                .setView(textView)));
    }

    private void onViewBeanSelected(ViewBean bean) {
        currentView = bean;
        tvCurView.setText(bean.getName());
        View view = bean.getView();
        if (view instanceof PinchImageView) {
            llScale.setVisibility(View.VISIBLE);
            switchScale.setChecked(view.isEnabled());
            switchScale.setOnCheckedChangeListener((buttonView, isChecked) ->
                    view.setEnabled(isChecked)
            );
        } else {
            llScale.setVisibility(View.GONE);
        }
        if (popupView != null) {
            popupView.setTvTitle(currentView.getName());
        }
    }

    private void createIllustration(IllustrationPopupBean bean) {
        PinchImageView imageView = new PinchImageView(this);
        imageView.setImageURI(bean.getUri());
        imageView.setEnabled(false);
        addView(new ViewBean()
                .setView(imageView)
                .setWidth(ConstraintLayout.LayoutParams.WRAP_CONTENT)
                .setHeight(ConstraintLayout.LayoutParams.WRAP_CONTENT)
                .setUri(bean.getUri())
                .setName("插画"));
    }

    @OnClick({R.id.iv_pad_setting, R.id.iv_show_list})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_show_list: {
                popupView = new BottomListPopup(this, adapter);
                popupView.setTitle("当前选中控件:" + (currentView == null ? "无" : currentView.getName()));
                new XPopup.Builder(this)
                        .autoDismiss(true)
                        .dismissOnTouchOutside(true)
                        .enableDrag(false)
                        .asCustom(popupView)
                        .show();
                break;
            }
            case R.id.iv_pad_setting: {
                showCurViewSettings();
                break;
            }
        }
    }

    private void showCurViewSettings() {
        if (currentView != null) {
            if (currentView.getView() instanceof ImageView) {
                configImagePopup = new ConfigImagePopup(this, currentView)
                        .setSelectImageListener(() -> {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.putExtra("crop", true);
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, SELECT_CONFIG_IMAGE_REQUEST_CODE);
                        });
                new XPopup.Builder(this)
                        .asCustom(configImagePopup).show();
            } else if (currentView.getView() instanceof TextView) {
                configTextPopup = new ConfigTextPopup(this, currentView);
                new XPopup.Builder(this)
                        .asCustom(configTextPopup).show();
            } else {
                Toast.makeText(this, "除图片以外的其他控件暂时不支持修改", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "未选择控件", Toast.LENGTH_SHORT).show();
        }

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

    private void addCustomView(String text) {
        switch (text) {
            case "图片": {
                new XPopup.Builder(this)
                        .asCustom(addImagePopup)
                        .show();
                break;
            }
            case "文字": {
                new XPopup.Builder(this)
                        .asCustom(addTextPopup)
                        .show();
                break;
            }
        }
    }

    private void addHeroTemplate(int position, String text) {
        if (position < 0 || position >= groupResIds.length) return;
        addView(new ViewBean()
                .setView(new ImageView(this))
                .setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT)
                .setHeight(ConstraintLayout.LayoutParams.MATCH_PARENT)
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setUri(groupResIds[position])
                .setName(text + "·边框"));
        addView(new ViewBean()
                .setView(new ImageView(this))
                .setWidth(Hawk.get(text + "·势力_width", 206))
                .setHeight(Hawk.get(text + "·势力_height", 255))
                .setUri(logoResIds[position])
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setName(text + "·势力"));
        addView(new ViewBean()
                .setView(new ImageView(this))
                .setWidth(Hawk.get(text + "·技能背板_width", 699))
                .setHeight(Hawk.get(text + "·技能背板_height", 247))
                .setScaleType(ImageView.ScaleType.FIT_XY)
                .setUri(skillBoardResIds[position])
                .setName(text + "·技能背板"));
    }

    private void showViewSettings(ViewBean bean) {
        if (popupView != null && bean != null) {
            if (bean.getView() instanceof ImageView) {
                configImagePopup = new ConfigImagePopup(this, bean)
                        .setSelectImageListener(() -> {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.putExtra("crop", true);
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, SELECT_CONFIG_IMAGE_REQUEST_CODE);
                        });
                BasePopupView basePopupView = new XPopup.Builder(this)
                        .asCustom(configImagePopup);
                popupView.dismissWith(basePopupView::show);
            } else if (bean.getView() instanceof TextView) {
                configTextPopup = new ConfigTextPopup(this, bean);
                BasePopupView basePopupView = new XPopup.Builder(this)
                        .asCustom(configTextPopup);
                popupView.dismissWith(basePopupView::show);
            } else {
                Toast.makeText(this, "控件暂时不支持修改", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "未选择控件", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPadSettings() {
        new XPopup.Builder(this)
                .asCustom(layoutSettingPopup.setLayoutWidth(mainLayout.getWidth())
                        .setLayoutHeight(mainLayout.getHeight()))
                .show();
    }

    private void addView(@NonNull ViewBean bean) {
        views.add(bean);
        onViewBeanSelected(bean);
        adapter.notifyDataSetChanged();
        if (bean.getView() instanceof ImageView) {
            ImageView view = (ImageView) bean.getView();
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
                    .load(bean.getUri() != null ? bean.getUri() : bean.getResId())
                    .into(view);
        } else if (bean.getView() instanceof TextView) {
            TextView view = (TextView) bean.getView();
            mainLayout.addView(view);
            ConstraintLayout.LayoutParams layoutParams =
                    (ConstraintLayout.LayoutParams) view.getLayoutParams();
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.leftMargin = Hawk.get(bean.getName() + "_leftMargin", 0);
            layoutParams.topMargin = Hawk.get(bean.getName() + "_topMargin", 0);
            view.requestLayout();
        }
    }

    private void removeView(ViewBean bean) {
        mainLayout.removeView(bean.getView());
        if (currentView == bean) {
            currentView = null;
            popupView.setTvTitle("无");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_free_activity, menu);
        return true;
    }

    private void addPerfabView(String text) {
        switch (text) {
            case "武将模板": {
                groupSelectPopup.setCheckedPosition(-1);
                groupSelectPopup.show();
                break;
            }
            case "卡牌模板": {
                break;
            }
            case "边框": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(ConstraintLayout.LayoutParams.MATCH_PARENT)
                                .setHeight(ConstraintLayout.LayoutParams.MATCH_PARENT)
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setUri(groupResIds[position])
                                .setName(name + "·边框"));
                    }
                }).show();
                break;
            }
            case "势力": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·势力_width", 206))
                                .setHeight(Hawk.get(name + "·势力_height", 255))
                                .setUri(logoResIds[position])
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setName(name + "·势力"));
                    }
                }).show();
                break;
            }
            case "称号": {
                new XPopup.Builder(this).asCustom(titleTextPopup)
                        .show();
                break;
            }
            case "武将名": {
                new XPopup.Builder(this).asCustom(nameTextPopup)
                        .show();
                break;
            }
            case "插画": {
                new XPopup.Builder(this)
                        .asCustom(illustrationPopup).show();
                break;
            }
            case "勾玉(身份)": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·勾玉(身份)_width", 53))
                                .setHeight(Hawk.get(name + "·勾玉(身份)_height", 64))
                                .setUri(hpStdResIds[position])
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setName(name + "·勾玉(身份)"));
                    }
                }).show();
                break;
            }
            case "勾玉x1(国战)": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·勾玉(国战)_width", 48))
                                .setHeight(Hawk.get(name + "·勾玉(国战)_height", 42))
                                .setUri(hpGuozhanResIds[position])
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setName(name + "·勾玉(国战)"));
                    }
                }).show();
                break;
            }
            case "勾玉x0.5(国战)": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·勾玉x0.5(国战)_width", 48))
                                .setHeight(Hawk.get(name + "·勾玉x0.5(国战)_height", 42))
                                .setUri(hpGuozhanHalfResIds[position])
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setName(name + "·勾玉x0.5(国战)"));
                    }
                }).show();
                break;
            }
            case "技能名": {
                new XPopup.Builder(this).asCustom(skillNamePopup)
                        .show();
                break;
            }
            case "技能名背板": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·技能名背板_width", 207))
                                .setHeight(Hawk.get(name + "·技能名背板_height", 79))
                                .setUri(skillBarResIds[position])
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setName(name + "·技能名背板"));
                    }
                }).show();
                break;
            }
            case "技能描述背板": {
                new XPopup.Builder(this).asCenterList("请选择一个势力:", groups, (position, t) -> {
                    if (position >= 0) {
                        String name = groups[position];
                        addView(new ViewBean()
                                .setView(new ImageView(this))
                                .setWidth(Hawk.get(name + "·技能背板_width", 697))
                                .setHeight(Hawk.get(name + "·技能背板_height", 245))
                                .setScaleType(ImageView.ScaleType.FIT_XY)
                                .setUri(skillBoardResIds[position])
                                .setName(name + "·技能背板"));
                    }
                }).show();
                break;
            }
            case "技能描述": {
                new XPopup.Builder(this).asCustom(skillInfoPopup)
                        .show();
                break;
            }
        }
    }

    private void swapView() {
        for (ViewBean bean : views) {
            mainLayout.bringChildToFront(bean.getView());
        }
        mainLayout.invalidate();
    }

    private void onViewSelected(View view) {
        for (ViewBean bean : views) {
            bean.setSelected(false);
            if (bean.getView() == view) {
                currentView = bean;
                currentView.setSelected(true);
                tvCurView.setText(bean.getName());
            }
        }
        if (view instanceof PinchImageView) {
            llScale.setVisibility(View.VISIBLE);
            switchScale.setChecked(view.isEnabled());
            switchScale.setOnCheckedChangeListener((buttonView, isChecked) ->
                    view.setEnabled(isChecked));
        } else {
            llScale.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about: {
                break;
            }
            case R.id.action_custom_view: {
                customViewPopup = new XPopup.Builder(this)
                        .asCenterList("请选择一个控件类型:", new String[]{"图片", "文字"},
                                (position, text) -> {
                                    if (position >= 0) {
                                        customViewPopup.dismissWith(() -> addCustomView(text));
                                    }
                                });
                customViewPopup.show();
                break;
            }
            case R.id.action_perfab: {
                new XPopup.Builder(this)
                        .asCenterList("导入一个预制组件:", perfabList,
                                (position, text) -> {
                                    if (position >= 0) {
                                        addPerfabView(text);
                                    }
                                }).show();
                break;
            }
            case R.id.action_history: {
                break;
            }
            case R.id.action_layout_setting: {
                showPadSettings();
                break;
            }
            case R.id.action_export: {
                exportImage();
                break;
            }
        }
        return true;
    }

    private void exportImage() {
        BasePopupView loading = new XPopup.Builder(this)
                .asLoading("图片生成中...")
                .show();
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        App.executeTask(() -> {
                            if (saveToGallery(String.valueOf(new Date().getTime()), Bitmap.CompressFormat.PNG)) {
                                runOnUiThread(() -> Toast.makeText(FreeActivity.this, R.string.export_done, Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(FreeActivity.this, R.string.export_fail, Toast.LENGTH_SHORT).show());
                            }
                            runOnUiThread(loading::dismiss);
                        });
                    } else {
                        loading.dismiss();
                        Toast.makeText(FreeActivity.this, R.string.no_permission, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private boolean saveToGallery(String fileName, Bitmap.CompressFormat
            format) {
        String subFolderPath = "三国杀制图";
        String fileDescription = "Sanguosha Save";
        int quality = 100;

        long currentTime = System.currentTimeMillis();

        File extBaseDir = Environment.getExternalStorageDirectory();
        File file = new File(extBaseDir.getAbsolutePath() + "/DCIM/" + subFolderPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }

        String mimeType;
        switch (format) {
            case PNG:
                mimeType = "image/png";
                if (!fileName.endsWith(".png"))
                    fileName += ".png";
                break;
            case WEBP:
                mimeType = "image/webp";
                if (!fileName.endsWith(".webp"))
                    fileName += ".webp";
                break;
            case JPEG:
            default:
                mimeType = "image/jpeg";
                if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")))
                    fileName += ".jpg";
                break;
        }

        String filePath = file.getAbsolutePath() + "/" + fileName;
        FileOutputStream out;
        try {
            out = new FileOutputStream(filePath);

            Bitmap b = getCardBitmap();
            b.compress(format, quality, out);

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        long size = new File(filePath).length();

        ContentValues values = new ContentValues(8);

        // store the details
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.DESCRIPTION, fileDescription);
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.DATA, filePath);
        values.put(MediaStore.Images.Media.SIZE, size);

        return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) != null;
    }

    private Bitmap getCardBitmap() {
        int w = mainLayout.getMeasuredWidth();
        int h = mainLayout.getMeasuredHeight();
        Bitmap returnedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        mainLayout.draw(canvas);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnedBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case SELECT_ADD_IMAGE_REQUEST_CODE: {
                    if (addImagePopup != null) {
                        addImagePopup.setUri(data.getData());
                    }
                    break;
                }
                case SELECT_ILLUSTRATION_IMAGE_REQUEST_CODE: {
                    if (illustrationPopup != null) {
                        illustrationPopup.setUri(data.getData());
                    }
                    break;
                }
                case SELECT_CONFIG_IMAGE_REQUEST_CODE: {
                    if (configImagePopup != null) {
                        configImagePopup.setUri(data.getData());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onDirectChange(DirectionView.Direction direction) {
        if (currentView == null) return;
        View view = currentView.getView();
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) view.getLayoutParams();

        switch (direction) {
            case UP: {
                params.topMargin -= mStep;
                break;
            }
            case UP_AND_LEFT: {
                params.topMargin -= mStep;
                params.leftMargin -= mStep;
                break;
            }
            case LEFT: {
                params.leftMargin -= mStep;
                break;
            }
            case DOWN_AND_LEFT: {
                params.topMargin += mStep;
                params.leftMargin -= mStep;
                break;
            }
            case DOWN: {
                params.topMargin += mStep;
                break;
            }
            case DOWN_AND_RIGHT: {
                params.topMargin += mStep;
                params.leftMargin += mStep;
                break;
            }
            case RIGHT: {
                params.leftMargin += mStep;
                break;
            }
            case UP_AND_RIGHT: {
                params.topMargin -= mStep;
                params.leftMargin += mStep;
                break;
            }
        }
        view.requestLayout();
    }

    @OnClick({R.id.iv_width_add, R.id.iv_width_reduce,
            R.id.iv_height_add, R.id.iv_height_reduce})
    void adjustCurView(View view) {
        if (currentView == null) return;
        if (currentView.getView() instanceof TextView) {
            adjustTextView(view.getId(), (TextView) currentView.getView());
        } else {
            adjustImageView(view.getId(), (ImageView) currentView.getView());
        }
    }

    private void adjustTextView(int id, TextView textView) {
        float size = textView.getTextSize();
        switch (id) {
            case R.id.iv_height_add:
            case R.id.iv_width_add: {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + sStep);
                break;
            }
            case R.id.iv_height_reduce:
            case R.id.iv_width_reduce: {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - sStep);
                break;
            }
        }
    }

    private void adjustImageView(int id, ImageView imageView) {
        if (imageView == null) return;
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        boolean adjustViewBounds = imageView.getAdjustViewBounds();
        if (adjustViewBounds) {
            adjustImageViewSpecial(id, imageView);
            return;
        }
        int matchParent = ConstraintLayout.LayoutParams.MATCH_PARENT;
        if (matchParent == params.width || matchParent == params.height) {
            Toast.makeText(this, "控件缩放类型不支持调整大小", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (id) {
            case R.id.iv_width_add: {
                if (params.width < 0) {
                    params.width = imageView.getWidth() + sStep;
                    params.height = imageView.getHeight();
                } else {
                    params.width += sStep;
                }
                break;
            }
            case R.id.iv_width_reduce: {
                if (params.width <= 0) return;
                params.width -= sStep;
                break;
            }
            case R.id.iv_height_add: {
                if (params.height < 0) {
                    params.height = imageView.getHeight() + sStep;
                    params.width = imageView.getWidth();
                } else {
                    params.height += sStep;
                }
                break;
            }
            case R.id.iv_height_reduce: {
                if (params.height <= 0) return;
                params.height -= sStep;
                break;
            }
        }
        currentView.getView().requestLayout();
    }

    private void adjustImageViewSpecial(int id, ImageView imageView) {
        if (imageView == null) return;
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        switch (id) {
            case R.id.iv_height_add:
            case R.id.iv_width_add: {
                if (params.width == ConstraintLayout.LayoutParams.WRAP_CONTENT) {
                    params.height += mStep;
                } else {
                    params.width += mStep;
                }
                break;
            }
            case R.id.iv_height_reduce:
            case R.id.iv_width_reduce: {
                if (params.width == ConstraintLayout.LayoutParams.WRAP_CONTENT) {
                    params.height -= mStep;
                } else {
                    params.width -= mStep;
                }
                break;
            }
        }
        currentView.getView().requestLayout();
    }


    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case HawkKey.TEXT_COLOR_DIALOG_ID: {
                if (configTextPopup != null) {
                    configTextPopup.setColor(color);
                }
                break;
            }
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }
}
