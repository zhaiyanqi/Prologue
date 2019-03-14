package cn.zhaiyanqi.prologue.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.zhaiyanqi.prologue.R;

public class HeroMakerActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final int REQUEST_IMAGE_CODE = 1;

    private ImageView imgHeroGroup, imgHeroFrame,
            imgHeroBaseBoard, imgHeroSkillBase, imgHeroSkillBar, imgRightCloud,
            imgHeroImg, imgHeroOutImg;
    private ConstraintLayout layout;
    private TextView tvHeroTitle, tvHeroName;
    private Typeface titleFont, nameFont;
    private CheckBox checkBoxBaseBoard, checkBoxFrame, checkBoxLogo, checkBoxSkill;
    private int yourChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_maker);
        initTitle();
        initView();
        initListener();
        loadWei();
        initFonts();
    }

    private void initFonts() {
        new Thread(() -> {
            titleFont = Typeface.createFromAsset(getAssets(), "fonts/DFPNewChuan-B5.ttf");
            nameFont = Typeface.createFromAsset(getAssets(), "fonts/jmmcsgsfix.ttf");
            runOnUiThread(() -> {
                tvHeroTitle.setTypeface(titleFont);
                tvHeroName.setTypeface(nameFont);
            });
        }).start();

    }

    private void initView() {
        layout = findViewById(R.id.hero_maker_main_card);

        imgHeroGroup = findViewById(R.id.hero_maker_group);
        imgHeroFrame = findViewById(R.id.hero_maker_frame);
        imgHeroBaseBoard = findViewById(R.id.hero_maker_base_board);
        imgHeroSkillBase = findViewById(R.id.hero_maker_skill_board);
        imgHeroSkillBar = findViewById(R.id.hero_maker_skill_bar);
        imgRightCloud = findViewById(R.id.hero_maker_right_cloud);

        imgHeroImg = findViewById(R.id.hero_maker_hero_img);
        imgHeroOutImg = findViewById(R.id.hero_maker_hero_out_img);

        tvHeroTitle = findViewById(R.id.hero_maker_title);
        tvHeroName = findViewById(R.id.hero_maker_name);

        checkBoxBaseBoard = findViewById(R.id.checkbox_hero_base_board);
        checkBoxFrame = findViewById(R.id.checkbox_hero_frame);
        checkBoxLogo = findViewById(R.id.checkbox_hero_logo);
        checkBoxSkill = findViewById(R.id.checkbox_hero_skill);

    }

    private void initListener() {
        imgHeroGroup.setOnClickListener(this);
        tvHeroTitle.setOnClickListener(this);
        tvHeroName.setOnClickListener(this);

        findViewById(R.id.hero_maker_import_pic).setOnClickListener(this);

        checkBoxBaseBoard.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroBaseBoard.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxFrame.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroFrame.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        checkBoxLogo.setOnCheckedChangeListener((buttonView, isChecked) ->
                imgHeroGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        checkBoxSkill.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra("crop", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_IMAGE_CODE);
        });

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(R.drawable.btn_hero_maker_bg).into((ImageView) findViewById(R.id.hero_maker_img));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hero_maker_group: {
                showSingleChoiceDialog();
                break;
            }
            case R.id.hero_maker_title: {
                showTitleInputDialog();
                break;
            }
            case R.id.hero_maker_name: {
                showNameInputDialog();
                break;
            }
            case R.id.hero_maker_import_pic: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, REQUEST_IMAGE_CODE);
                break;
            }
        }
    }

    private void showSingleChoiceDialog() {
        final String[] items = {"魏", "蜀", "吴", "群", "神"};
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(this);
        singleChoiceDialog.setTitle("选择一个势力");
        singleChoiceDialog.setSingleChoiceItems(items, yourChoice,
                (dialog, which) -> yourChoice = which);
        singleChoiceDialog.setPositiveButton("确定", (dialog, which) -> {
            switch (yourChoice) {
                case 0: {
                    loadWei();
                    break;
                }
                case 1: {
                    loadShu();
                    break;
                }
                case 2: {
                    loadWu();
                    break;
                }
                case 3: {
                    loadQun();
                    break;
                }
                case 4: {
                    loadGod();
                    break;
                }
            }
        });
        singleChoiceDialog.show();
    }


    private void loadWei() {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wei_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.wei).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.wei_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.wei_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.wei_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.wei_skill_bar).into(imgHeroSkillBar);
    }

    private void loadShu() {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.shu_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.shu).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.shu_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.shu_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.shu_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.shu_skill_bar).into(imgHeroSkillBar);
    }

    private void loadWu() {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wu_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.wu).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.wu_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.wu_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.wu_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.wu_skill_bar).into(imgHeroSkillBar);
    }

    private void loadQun() {
        imgHeroBaseBoard.setVisibility(View.VISIBLE);
        imgRightCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.qun_base_board).into(imgHeroBaseBoard);
        Glide.with(this).load(R.drawable.qun).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.qun_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.qun_right_cloud).into(imgRightCloud);
        Glide.with(this).load(R.drawable.qun_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.qun_skill_bar).into(imgHeroSkillBar);
    }

    private void loadGod() {
//        Glide.with(this).load(R.drawable.god_base_board).into(imgHeroBaseBoard);
        imgHeroBaseBoard.setVisibility(View.GONE);
        imgRightCloud.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.god).into(imgHeroFrame);
        Glide.with(this).load(R.drawable.god_logo).into(imgHeroGroup);
        Glide.with(this).load(R.drawable.god_skill_board).into(imgHeroSkillBase);
        Glide.with(this).load(R.drawable.god_skill_bar).into(imgHeroSkillBar);
    }

    private void showTitleInputDialog() {
        final EditText editText = new EditText(this);
        editText.setText(tvHeroTitle.getText().toString());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入武将称号").setView(editText);
        inputDialog.setPositiveButton("确定", (dialog, which) -> {
            tvHeroTitle.setText(editText.getText().toString());
        }).show();
    }

    private void showNameInputDialog() {
        final EditText editText = new EditText(this);
        editText.setText(tvHeroName.getText().toString());
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入武将名").setView(editText);
        inputDialog.setPositiveButton("确定", (dialog, which) -> {
            tvHeroName.setText(editText.getText().toString());
        }).show();
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
}
