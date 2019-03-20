package cn.zhaiyanqi.prologue.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HeroMakerActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CODE = 1;
    private RxPermissions rxPermissions;
    private Typeface titleFont, nameFont, skillNameFont;
    private Bitmap.CompressFormat exportFormat;
    private ExecutorService executorService;
    @BindView(R.id.menu_bar_adjust_position_arrow)
    ImageView menuAdjustPositionArrow;
    @BindView(R.id.layout_adjust_position)
    ConstraintLayout adjustPositionLayout;
    @BindView(R.id.menu_bar_basic_info_arrow)
    ImageView basicInfoArrow;

    // hero view
    @BindView(R.id.hero_maker_main_card)
    CardView cardView;
    @BindView(R.id.hero_maker_group)
    ImageView imgHeroGroup;
    @BindView(R.id.hero_maker_frame)
    ImageView imgHeroFrame;
    @BindView(R.id.hero_maker_base_board)
    ImageView imgHeroBaseBoard;
    @BindView(R.id.hero_maker_skill_board)
    ImageView imgHeroSkillBase;
    @BindView(R.id.hero_maker_right_cloud)
    ImageView imgRightCloud;
    @BindView(R.id.hero_maker_hero_img)
    ImageView imgHeroImg;
    @BindView(R.id.hero_maker_hero_out_img)
    ImageView imgHeroOutImg;
    @BindView(R.id.hero_maker_title)
    TextView tvHeroTitle;
    @BindView(R.id.hero_maker_name)
    TextView tvHeroName;
    @BindView(R.id.checkbox_hero_base_board)
    CheckBox checkBoxBaseBoard;
    @BindView(R.id.checkbox_hero_frame)
    CheckBox checkBoxFrame;
    @BindView(R.id.checkbox_hero_logo)
    CheckBox checkBoxLogo;
    @BindView(R.id.checkbox_hero_skill)
    CheckBox checkBoxSkill;
    @BindView(R.id.hero_maker_skills)
    LinearLayout llSKills;
    @BindView(R.id.hero_maker_skills_ll)
    LinearLayout llSkillsAll;
    @BindView(R.id.hero_maker_hp)
    LinearLayout heroHpLayout;

    //others
    @BindView(R.id.hero_maker_skill_1_name)
    TextView tvSkill1Name;
    @BindView(R.id.hero_maker_skill_1_text)
    TextView tvSkill1Info;
    @BindView(R.id.hero_maker_skill_2_name)
    TextView tvSkill2Name;
    @BindView(R.id.hero_maker_skill_2_text)
    TextView tvSkill2Info;
    @BindView(R.id.hero_maker_skill_3_name)
    TextView tvSkill3Name;
    @BindView(R.id.hero_maker_skill_3_text)
    TextView tvSkill3Info;
    @BindView(R.id.hero_maker_skill_1_bar)
    ImageView ivSkill1Bar;
    @BindView(R.id.hero_maker_skill_2_bar)
    ImageView ivSkill2Bar;
    @BindView(R.id.hero_maker_skill_3_bar)
    ImageView ivSkill3Bar;
    @BindView(R.id.edit_skill_button_1)
    Button skill1Button;
    @BindView(R.id.edit_skill_button_2)
    Button skill2Button;
    @BindView(R.id.edit_skill_button_3)
    Button skill3Button;
    @BindView(R.id.layout_basic_info)
    LinearLayout basicInfoLayout;
    @BindView(R.id.menu_bar_advance_option_arrow)
    ImageView advanceOptionArrow;
    @BindView(R.id.layout_advance_option)
    LinearLayout advanceOptionLayout;
    private int skillCount = 0;
    private View currentView;
    private int moveStepOffset = 1;

    @BindView(R.id.rg_move_position_1)
    RadioGroup nameTitleGroup;
    @BindView(R.id.rg_move_position_skill_1)
    RadioGroup skill1Group;
    @BindView(R.id.rg_move_position_skill_2)
    RadioGroup skill2Group;
    @BindView(R.id.rg_move_position_skill_3)
    RadioGroup skill3Group;
    @BindView(R.id.iv_hp_1)
    ImageView ivHp1;
    @BindView(R.id.iv_hp_2)
    ImageView ivHp2;
    @BindView(R.id.iv_hp_3)
    ImageView ivHp3;
    @BindView(R.id.iv_hp_4)
    ImageView ivHp4;
    @BindView(R.id.iv_hp_5)
    ImageView ivHp5;
    @BindView(R.id.tv_hp_text)
    TextView tvHpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_maker);
        rxPermissions = new RxPermissions(this);
        ButterKnife.bind(this);
        executorService = Executors.newCachedThreadPool();

        initTitle();
        initListener();
        loadDefaultGroup();
        loadDefaultFormat();
        initFonts();
    }

    @OnTextChanged(R.id.edit_text_step_length)
    void changeMoveStep(CharSequence text) {
        String str = text.toString();
        if (!TextUtils.isEmpty(str)) {
            moveStepOffset = Integer.parseInt(str);
        }
    }

    @OnTextChanged(R.id.edit_text_maxhp)
    void changeMaxHp(CharSequence text) {
        String str = text.toString();
        if (!TextUtils.isEmpty(str)) {
            int hp = Integer.parseInt(str);
            ivHp1.setVisibility(View.GONE);
            ivHp2.setVisibility(View.GONE);
            ivHp3.setVisibility(View.GONE);
            ivHp4.setVisibility(View.GONE);
            ivHp5.setVisibility(View.GONE);
            tvHpText.setVisibility(View.GONE);
            switch (hp) {
                case 5:
                    ivHp5.setVisibility(View.VISIBLE);
                case 4:
                    ivHp4.setVisibility(View.VISIBLE);
                case 3:
                    ivHp3.setVisibility(View.VISIBLE);
                case 2:
                    ivHp2.setVisibility(View.VISIBLE);
                case 1:
                    ivHp1.setVisibility(View.VISIBLE);
                case 0:
                    break;
                default: {
                    tvHpText.setVisibility(View.VISIBLE);
                    tvHpText.setText(hp + "/" + hp);
                }
            }
        }
    }

    @OnClick(R.id.menu_bar_advance_option)
    void switchMenuAdvanceOption() {
        if (advanceOptionLayout.getVisibility() == View.GONE) {
            advanceOptionLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.ic_expend).into(advanceOptionArrow);
        } else {
            advanceOptionLayout.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.ic_close).into(advanceOptionArrow);
        }
    }

    @OnClick(R.id.menu_bar_adjust_position)
    void switchMenuAdjustPosition() {
        if (adjustPositionLayout.getVisibility() == View.GONE) {
            adjustPositionLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.ic_expend).into(menuAdjustPositionArrow);
        } else {
            adjustPositionLayout.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.ic_close).into(menuAdjustPositionArrow);
        }
    }

    @OnClick(R.id.menu_bar_basic_info)
    void switchMenuBasicInfo() {
        if (basicInfoLayout.getVisibility() == View.GONE) {
            basicInfoLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.ic_expend).into(basicInfoArrow);
        } else {
            basicInfoLayout.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.ic_close).into(basicInfoArrow);
        }
    }

    @OnClick(R.id.up_arrow)
    void moveUp() {
        if (currentView != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) currentView.getLayoutParams();
            layoutParams.topMargin -= moveStepOffset;
            currentView.requestLayout();
        }
    }

    @OnClick(R.id.down_arrow)
    void moveDown() {
        if (currentView != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) currentView.getLayoutParams();
            layoutParams.topMargin += moveStepOffset;
            currentView.requestLayout();
        }
    }

    @OnClick(R.id.left_arrow)
    void moveLeft() {
        if (currentView != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) currentView.getLayoutParams();
            layoutParams.leftMargin -= moveStepOffset;
            currentView.requestLayout();
        }
    }

    @OnClick(R.id.right_arrow)
    void moveRight() {
        if (currentView != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) currentView.getLayoutParams();
            layoutParams.leftMargin += moveStepOffset;
            currentView.requestLayout();
        }
    }

    private void loadDefaultFormat() {
        RadioGroup radioGroup = findViewById(R.id.hero_maker_rg_select_format);
        radioGroup.check(R.id.hero_maker_rb_format_png);
        exportFormat = Bitmap.CompressFormat.PNG;
    }

    private void loadDefaultGroup() {
        RadioGroup radioGroup = findViewById(R.id.hero_maker_group_radio_group);
        radioGroup.check(R.id.hero_maker_radio_btn_wei);
        loadWei(null);
    }


    private Disposable fontSubscribe;

    private void initFonts() {
        fontSubscribe = Observable.just(
                titleFont = Typeface.createFromAsset(getAssets(), "fonts/DFPNewChuan-B5.ttf"),
                nameFont = Typeface.createFromAsset(getAssets(), "fonts/jmmcsgsfix.ttf"),
                skillNameFont = Typeface.createFromAsset(getAssets(), "fonts/fzlsft.ttf")
        ).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    tvHeroTitle.setTypeface(titleFont);
                    tvHeroName.setTypeface(nameFont);
                    tvSkill1Name.setTypeface(skillNameFont);
                    tvSkill2Name.setTypeface(skillNameFont);
                    tvSkill3Name.setTypeface(skillNameFont);
                }, e -> Toast.makeText(HeroMakerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTitle() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        Glide.with(this)
                .load(R.drawable.btn_hero_maker_bg)
                .into((ImageView) findViewById(R.id.hero_maker_img));
    }

    private void initListener() {
        checkBoxBaseBoard.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroBaseBoard.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxFrame.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroFrame.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxLogo.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxSkill.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroSkillBase.setVisibility(isChecked ? View.VISIBLE : View.GONE));

    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.hero_maker_export_photo)
    void exportPhoto() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        executorService.execute(() -> {
                            if (saveToGallery(String.valueOf(new Date().getTime()), exportFormat)) {
                                runOnUiThread(() -> Toast.makeText(this, R.string.export_done, Toast.LENGTH_SHORT).show());
                            } else {
                                runOnUiThread(() -> Toast.makeText(this, R.string.export_fail, Toast.LENGTH_SHORT).show());
                            }
                        });
                    } else {
                        Toast.makeText(this, R.string.no_permission, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.hero_maker_import_pic)
    void importPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_IMAGE_CODE);
    }

    @OnClick(R.id.hero_maker_add_skill)
    void addSkillButton() {
        if (skillCount >= 3) {
            Toast.makeText(this, "不能再多了", Toast.LENGTH_SHORT).show();
            return;
        }
        skillCount++;
        llSkillsAll.setVisibility(View.VISIBLE);
        switch (skillCount) {
            case 1: {
                ivSkill1Bar.setVisibility(View.VISIBLE);
                tvSkill1Info.setVisibility(View.VISIBLE);
                tvSkill1Name.setVisibility(View.VISIBLE);
                skill1Button.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                ivSkill2Bar.setVisibility(View.VISIBLE);
                tvSkill2Info.setVisibility(View.VISIBLE);
                tvSkill2Name.setVisibility(View.VISIBLE);
                skill2Button.setVisibility(View.VISIBLE);
                break;
            }
            case 3: {
                ivSkill3Bar.setVisibility(View.VISIBLE);
                tvSkill3Info.setVisibility(View.VISIBLE);
                tvSkill3Name.setVisibility(View.VISIBLE);
                skill3Button.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void removeSkillButton(View view) {
        if (skillCount == 0) {
            llSkillsAll.setVisibility(View.VISIBLE);
            return;
        }
        skillCount--;
        switch (skillCount) {
            case 0: {
                ivSkill1Bar.setVisibility(View.GONE);
                tvSkill1Info.setVisibility(View.GONE);
                tvSkill1Name.setVisibility(View.GONE);
                skill1Button.setVisibility(View.GONE);
                llSkillsAll.setVisibility(View.GONE);
                break;
            }
            case 1: {
                ivSkill2Bar.setVisibility(View.GONE);
                tvSkill2Info.setVisibility(View.GONE);
                tvSkill2Name.setVisibility(View.GONE);
                skill2Button.setVisibility(View.GONE);
                break;
            }
            case 2: {
                ivSkill3Bar.setVisibility(View.GONE);
                tvSkill3Info.setVisibility(View.GONE);
                tvSkill3Name.setVisibility(View.GONE);
                skill3Button.setVisibility(View.GONE);
                break;
            }
        }
    }

    public void loadWei(View view) {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wei_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.wei).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.wei_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.wei_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.wei_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.wei_skill_bar).into(ivSkill1Bar);
        Glide.with(this).load(R.drawable.wei_skill_bar).into(ivSkill2Bar);
        Glide.with(this).load(R.drawable.wei_skill_bar).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.wei_hp).into(ivHp1);
        Glide.with(this).load(R.drawable.wei_hp).into(ivHp2);
        Glide.with(this).load(R.drawable.wei_hp).into(ivHp3);
        Glide.with(this).load(R.drawable.wei_hp).into(ivHp4);
        Glide.with(this).load(R.drawable.wei_hp).into(ivHp5);
    }

    public void loadShu(View view) {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.shu_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.shu).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.shu_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.shu_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.shu_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.shu_skill_bar).into(ivSkill1Bar);
        Glide.with(this).load(R.drawable.shu_skill_bar).into(ivSkill2Bar);
        Glide.with(this).load(R.drawable.shu_skill_bar).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.shu_hp).into(ivHp1);
        Glide.with(this).load(R.drawable.shu_hp).into(ivHp2);
        Glide.with(this).load(R.drawable.shu_hp).into(ivHp3);
        Glide.with(this).load(R.drawable.shu_hp).into(ivHp4);
        Glide.with(this).load(R.drawable.shu_hp).into(ivHp5);
    }

    public void loadWu(View view) {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wu_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.wu).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.wu_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.wu_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.wu_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.wu_skill_bar).into(ivSkill1Bar);
        Glide.with(this).load(R.drawable.wu_skill_bar).into(ivSkill2Bar);
        Glide.with(this).load(R.drawable.wu_skill_bar).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.wu_hp).into(ivHp1);
        Glide.with(this).load(R.drawable.wu_hp).into(ivHp2);
        Glide.with(this).load(R.drawable.wu_hp).into(ivHp3);
        Glide.with(this).load(R.drawable.wu_hp).into(ivHp4);
        Glide.with(this).load(R.drawable.wu_hp).into(ivHp5);
    }

    public void loadQun(View view) {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.qun_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.qun).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.qun_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.qun_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.qun_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.qun_skill_bar).into(ivSkill1Bar);
        Glide.with(this).load(R.drawable.qun_skill_bar).into(ivSkill2Bar);
        Glide.with(this).load(R.drawable.qun_skill_bar).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.qun_hp).into(ivHp1);
        Glide.with(this).load(R.drawable.qun_hp).into(ivHp2);
        Glide.with(this).load(R.drawable.qun_hp).into(ivHp3);
        Glide.with(this).load(R.drawable.qun_hp).into(ivHp4);
        Glide.with(this).load(R.drawable.qun_hp).into(ivHp5);
    }

    public void loadGod(View view) {
        imgHeroBaseBoard.setVisibility(View.GONE);
        imgRightCloud.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.god).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.god_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.god_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.god_skill_bar).into(ivSkill1Bar);
        Glide.with(this).load(R.drawable.god_skill_bar).into(ivSkill2Bar);
        Glide.with(this).load(R.drawable.god_skill_bar).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.god_hp).into(ivHp1);
        Glide.with(this).load(R.drawable.god_hp).into(ivHp2);
        Glide.with(this).load(R.drawable.god_hp).into(ivHp3);
        Glide.with(this).load(R.drawable.god_hp).into(ivHp4);
        Glide.with(this).load(R.drawable.god_hp).into(ivHp5);
    }

    public void upSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.height += 1;
        imgHeroSkillBase.requestLayout();
    }

    public void downSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.height -= 1;
        imgHeroSkillBase.requestLayout();
    }

    public void fatSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.width += 1;
        imgHeroSkillBase.requestLayout();
    }

    public void slimSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.width -= 1;
        imgHeroSkillBase.requestLayout();
    }

    public void editSkill1Button(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.layout_edit_skill_info, null, false);
        AlertDialog.Builder addSkillDialog = new AlertDialog.Builder(this);
        addSkillDialog.setTitle("请输入技能信息").setView(dialogView);
        EditText addSkillDialogInfo = dialogView.findViewById(R.id.edit_skill_info);
        EditText addSkillDialogName = dialogView.findViewById(R.id.edit_skill_name);
        addSkillDialogInfo.setText(tvSkill1Info.getText().toString());
        addSkillDialogName.setText(tvSkill1Name.getText().toString());
        addSkillDialog.setPositiveButton("确定", (dialog, which) -> {
            tvSkill1Name.setText(addSkillDialogName.getText().toString());
            skill1Button.setText(addSkillDialogName.getText().toString());
            tvSkill1Info.setText(addSkillDialogInfo.getText().toString());
        });
        addSkillDialog.show();
    }

    public void editSkill2Button(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.layout_edit_skill_info, null, false);
        AlertDialog.Builder addSkillDialog = new AlertDialog.Builder(this);
        addSkillDialog.setTitle("请输入技能信息").setView(dialogView);
        EditText addSkillDialogInfo = dialogView.findViewById(R.id.edit_skill_info);
        EditText addSkillDialogName = dialogView.findViewById(R.id.edit_skill_name);
        addSkillDialogInfo.setText(tvSkill2Info.getText().toString());
        addSkillDialogName.setText(tvSkill2Name.getText().toString());
        addSkillDialog.setPositiveButton("确定", (dialog, which) -> {
            tvSkill2Name.setText(addSkillDialogName.getText().toString());
            skill2Button.setText(addSkillDialogName.getText().toString());
            tvSkill2Info.setText(addSkillDialogInfo.getText().toString());
        });
        addSkillDialog.show();
    }

    public void editSkill3Button(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.layout_edit_skill_info, null);
        AlertDialog.Builder addSkillDialog = new AlertDialog.Builder(this);
        addSkillDialog.setTitle("请输入技能信息").setView(dialogView);
        EditText addSkillDialogInfo = dialogView.findViewById(R.id.edit_skill_info);
        EditText addSkillDialogName = dialogView.findViewById(R.id.edit_skill_name);
        addSkillDialogInfo.setText(tvSkill3Info.getText().toString());
        addSkillDialogName.setText(tvSkill3Name.getText().toString());
        addSkillDialog.setPositiveButton("确定", (dialog, which) -> {
            tvSkill3Name.setText(addSkillDialogName.getText().toString());
            skill3Button.setText(addSkillDialogName.getText().toString());
            tvSkill3Info.setText(addSkillDialogInfo.getText().toString());
        });
        addSkillDialog.show();
    }

    @OnClick(R.id.hero_maker_btn_title)
    void showTitleInputDialog() {
        final EditText editText = new EditText(this);
        editText.setText(tvHeroTitle.getText().toString());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入武将称号").setView(editText);
        inputDialog.setPositiveButton("确定", (dialog, which) ->
                tvHeroTitle.setText(editText.getText().toString())).show();
    }

    @OnClick(R.id.hero_maker_btn_name)
    void showNameInputDialog() {
        final EditText editText = new EditText(this);
        editText.setText(tvHeroName.getText().toString());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入武将名").setView(editText);
        inputDialog.setPositiveButton("确定", (dialog, which) ->
                tvHeroName.setText(editText.getText().toString())).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CODE && data != null) {
                Uri uri = data.getData();
                Glide.with(this).load(uri).into(imgHeroImg);
            }
        }
    }


    private boolean saveToGallery(String fileName, Bitmap.CompressFormat
            format) {
        String subFolderPath = "prologue";
        String fileDescription = "PROLOGUE Save";
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

            Bitmap b = getChartBitmap();
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

        return this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) != null;
    }

    public Bitmap getChartBitmap() {
        Bitmap returnedBitmap = Bitmap.createBitmap(cardView.getWidth(), cardView.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = cardView.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        cardView.draw(canvas);
        return returnedBitmap;
    }

    public void setExportFormatPNG(View view) {
        exportFormat = Bitmap.CompressFormat.PNG;
    }

    public void setExportFormatJPG(View view) {
        exportFormat = Bitmap.CompressFormat.JPEG;
    }

    public void setExportFormatWEBP(View view) {
        exportFormat = Bitmap.CompressFormat.WEBP;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }

        if (fontSubscribe != null && !fontSubscribe.isDisposed()) {
            fontSubscribe.dispose();
        }
    }


    //选中移动控件按钮时的监听事件
    public void rbNameClicked(View view) {
        checkNameTitleGroup(tvHeroName);
    }

    public void rbTitleClicked(View view) {
        checkNameTitleGroup(tvHeroTitle);
    }

    public void rbHpClicked(View view) {
        checkNameTitleGroup(heroHpLayout);
    }

    public void rbSkillBoardClicked(View view) {
        checkNameTitleGroup(imgHeroSkillBase);
    }

    private void checkNameTitleGroup(View view) {
        currentView = view;
        skill1Group.clearCheck();
        skill2Group.clearCheck();
        skill3Group.clearCheck();
    }

    public void rbSkill1BarClicked(View view) {
        checkSkill1Group(ivSkill1Bar);
    }

    public void rbSkill1NameClicked(View view) {
        checkSkill1Group(tvSkill1Name);
    }

    public void rbSkill1InfoClicked(View view) {
        checkSkill1Group(tvSkill1Info);
    }

    private void checkSkill1Group(View view) {
        currentView = view;
        nameTitleGroup.clearCheck();
        skill2Group.clearCheck();
        skill3Group.clearCheck();
    }

    public void rbSkill2BarClicked(View view) {
        checkSkill2Group(ivSkill2Bar);
    }

    public void rbSkill2NameClicked(View view) {
        checkSkill2Group(tvSkill2Name);
    }

    public void rbSkill2InfoClicked(View view) {
        checkSkill2Group(tvSkill2Info);
    }

    private void checkSkill2Group(View view) {
        currentView = view;
        skill1Group.clearCheck();
        nameTitleGroup.clearCheck();
        skill3Group.clearCheck();
    }

    public void rbSkill3BarClicked(View view) {
        checkSkill3Group(ivSkill3Bar);
    }

    public void rbSkill3NameClicked(View view) {
        checkSkill3Group(tvSkill3Name);
    }

    public void rbSkill3InfoClicked(View view) {
        checkSkill3Group(tvSkill3Info);
    }

    public void checkSkill3Group(View view) {
        currentView = view;
        skill1Group.clearCheck();
        skill2Group.clearCheck();
        nameTitleGroup.clearCheck();
    }


}
