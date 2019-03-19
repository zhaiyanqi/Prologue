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
import android.view.LayoutInflater;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HeroMakerActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CODE = 1;
    private RxPermissions rxPermissions;
    private Typeface titleFont, nameFont, skillNameFont;
    private Bitmap.CompressFormat exportFormat;
    private ExecutorService executorService;

    // hero view
    private ImageView imgHeroGroup;
    private ImageView imgHeroFrame;
    private ImageView imgHeroBaseBoard;
    private ImageView imgHeroSkillBase;
    private ImageView imgRightCloud;
    private ImageView imgHeroImg;
    private ImageView imgHeroOutImg;
    private CardView layout;
    private TextView tvHeroTitle;
    private TextView tvHeroName;
    private CheckBox checkBoxBaseBoard;
    private CheckBox checkBoxFrame;
    private CheckBox checkBoxLogo;
    private CheckBox checkBoxSkill;
    private LinearLayout llSKills;
    private LinearLayout llSkillsAll;

    //others
    private TextView tvSkill1Name;
    private TextView tvSkill1Info;
    private TextView tvSkill2Name;
    private TextView tvSkill2Info;
    private TextView tvSkill3Name;
    private TextView tvSkill3Info;
    private ImageView ivSkill1Bar;
    private ImageView ivSkill2Bar;
    private ImageView ivSkill3Bar;
    private Button skill1Button;
    private Button skill2Button;
    private Button skill3Button;
    private int skillCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_maker);
        rxPermissions = new RxPermissions(this);
        ButterKnife.bind(this);
        executorService = Executors.newCachedThreadPool();
        initTitle();
        initView();
        initListener();
        loadDefaultGroup();
        loadDefaultFormat();
        initFonts();
        initDialog();
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

    @SuppressLint("CheckResult")
    private void initFonts() {
        Observable.just(
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

    private void initView() {
        layout = findViewById(R.id.hero_maker_main_card);

        imgHeroGroup = findViewById(R.id.hero_maker_group);
        imgHeroFrame = findViewById(R.id.hero_maker_frame);
        imgHeroBaseBoard = findViewById(R.id.hero_maker_base_board);
        imgHeroSkillBase = findViewById(R.id.hero_maker_skill_board);
        imgRightCloud = findViewById(R.id.hero_maker_right_cloud);

        imgHeroImg = findViewById(R.id.hero_maker_hero_img);
        imgHeroOutImg = findViewById(R.id.hero_maker_hero_out_img);

        tvHeroTitle = findViewById(R.id.hero_maker_title);
        tvHeroName = findViewById(R.id.hero_maker_name);

        checkBoxBaseBoard = findViewById(R.id.checkbox_hero_base_board);
        checkBoxFrame = findViewById(R.id.checkbox_hero_frame);
        checkBoxLogo = findViewById(R.id.checkbox_hero_logo);
        checkBoxSkill = findViewById(R.id.checkbox_hero_skill);


        llSKills = findViewById(R.id.hero_maker_skills);
        llSkillsAll = findViewById(R.id.hero_maker_skills_ll);

//        llSkillBoardText = findViewById(R.id.hero_maker_skill_board_texts);
//        skillBoardLayout = findViewById(R.id.hero_maker_skill_board_layout);

        tvSkill1Name = findViewById(R.id.hero_maker_skill_1_name);
        tvSkill2Name = findViewById(R.id.hero_maker_skill_2_name);
        tvSkill3Name = findViewById(R.id.hero_maker_skill_3_name);

        tvSkill1Info = findViewById(R.id.hero_maker_skill_1_text);
        tvSkill2Info = findViewById(R.id.hero_maker_skill_2_text);
        tvSkill3Info = findViewById(R.id.hero_maker_skill_3_text);

        ivSkill1Bar = findViewById(R.id.hero_maker_skill_1_bar);
        ivSkill2Bar = findViewById(R.id.hero_maker_skill_2_bar);
        ivSkill3Bar = findViewById(R.id.hero_maker_skill_3_bar);

        skill1Button = findViewById(R.id.edit_skill_button_1);
        skill2Button = findViewById(R.id.edit_skill_button_2);
        skill3Button = findViewById(R.id.edit_skill_button_3);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void initTitle() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        Glide.with(this).load(R.drawable.btn_hero_maker_bg).into((ImageView) findViewById(R.id.hero_maker_img));
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
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        executorService.execute(() -> {
                            if (saveToGallery(String.valueOf(new Date().getTime()), exportFormat, 100)) {
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

    private void initDialog() {
        initTitleDialog();
    }

    private void initTitleDialog() {

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
    }

    public void upSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.height += 1;
        imgHeroSkillBase.setLayoutParams(layoutParams);
    }

    public void downSkillBoard(View view) {
        ViewGroup.LayoutParams layoutParams = imgHeroSkillBase.getLayoutParams();
        layoutParams.height -= 1;
        imgHeroSkillBase.setLayoutParams(layoutParams);
    }

    public void editSkill1Button(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
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
        View dialogView = inflater.inflate(R.layout.layout_edit_skill_info, null);
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
            format, int quality) {
        String subFolderPath = "prologue";
        String fileDescription = "PROLOGUE Save";
        if (quality < 0 || quality > 100)
            quality = 50;

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
        Bitmap returnedBitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = layout.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        layout.draw(canvas);
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
    }
}
