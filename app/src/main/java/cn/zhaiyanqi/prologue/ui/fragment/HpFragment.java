package cn.zhaiyanqi.prologue.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * A simple {@link Fragment} subclass.
 */
public class HpFragment extends Fragment {
    private static final int SELECT_HP_REQUEST_CODE = 1;
    private static final int SELECT_HALF_HP_REQUEST_CODE = 2;
    @BindView(R.id.ll_half_hp)
    LinearLayout halfHpLayout;
    @BindView(R.id.tv_custom_hp)
    TextView tvHp;
    @BindView(R.id.tv_custom_half_hp)
    TextView tvHalfHp;
    @BindView(R.id.cb_custom_hp)
    CheckBox cbCustomHp;
    @BindView(R.id.cb_custom_half_hp)
    CheckBox cbCustomHalfHp;
    @BindView(R.id.tv_cur_hp)
    TextView tvCurHp;
    @BindView(R.id.tv_hp_height)
    TextView tvHpHeight;
    @BindView(R.id.tv_hp_width)
    TextView tvHpWidth;


    private boolean standardMode = true;
    private int stdHp = 5;
    private float guozhanHp = 2.0f;
    private Uri hpUri, halfHpUri;
    private ImageView ivHp1, ivHp2, ivHp3, ivHp4, ivHp5;
    private boolean useCustomHpImage = false;
    private boolean useCustomHalfHpImage = false;
    private CardMakerActivity activity;

    public HpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hp, container, false);
        ButterKnife.bind(this, view);
        refreshWHText();
        return view;
    }

    @OnClick({R.id.btn_add_height, R.id.btn_add_width, R.id.btn_reduce_height, R.id.btn_reduce_width})
    void changeHpSize(View view) {
        switch (view.getId()) {
            case R.id.btn_add_height: {
                addHeight(ivHp1);
                addHeight(ivHp2);
                addHeight(ivHp3);
                addHeight(ivHp4);
                addHeight(ivHp5);
                break;
            }
            case R.id.btn_add_width: {
                addWidth(ivHp1);
                addWidth(ivHp2);
                addWidth(ivHp3);
                addWidth(ivHp4);
                addWidth(ivHp5);
                break;
            }
            case R.id.btn_reduce_height: {
                reduceHeight(ivHp1);
                reduceHeight(ivHp2);
                reduceHeight(ivHp3);
                reduceHeight(ivHp4);
                reduceHeight(ivHp5);
                break;
            }
            case R.id.btn_reduce_width: {
                reduceWidth(ivHp1);
                reduceWidth(ivHp2);
                reduceWidth(ivHp3);
                reduceWidth(ivHp4);
                reduceWidth(ivHp5);
                break;
            }
        }
        refreshWHText();
    }

    @OnClick({R.id.rb_standard, R.id.rb_guozhan})
    void switchMode(View view) {
        switch (view.getId()) {
            case R.id.rb_standard: {
                standardMode = true;
                halfHpLayout.setVisibility(View.GONE);
                tvCurHp.setText(String.valueOf(stdHp));
                break;
            }
            case R.id.rb_guozhan: {
                standardMode = false;
                halfHpLayout.setVisibility(View.VISIBLE);
                tvCurHp.setText(String.valueOf(guozhanHp));
                break;
            }
        }
        refreshHp();
    }

    @OnClick(R.id.btn_add)
    void addHp() {
        if (standardMode) {
            stdHp++;
        } else {
            guozhanHp += 0.5;
        }
        refreshHp();
    }

    @OnClick(R.id.btn_reduce)
    void reduceHp() {
        if (standardMode) {
            if (stdHp > 1) {
                stdHp--;
                refreshHp();
            } else {
                Toast.makeText(this.getContext(), "至少要有一个勾玉", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (guozhanHp > 0.5) {
                guozhanHp -= 0.5;
                refreshHp();
            } else {
                Toast.makeText(this.getContext(), "至少要有半个勾玉", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnCheckedChanged({R.id.cb_custom_hp, R.id.cb_custom_half_hp})
    void switchCustomHpImage(CompoundButton view, boolean checked) {
        switch (view.getId()) {
            case R.id.cb_custom_hp: {
                useCustomHpImage = checked;
                refreshHp();
                break;
            }
            case R.id.cb_custom_half_hp: {
                useCustomHalfHpImage = checked;
                refreshHp();
                break;
            }
        }

    }

    @OnClick({R.id.btn_select_custom_hp, R.id.btn_select_custom_half_hp})
    void selectCustomImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        switch (view.getId()) {
            case R.id.btn_select_custom_hp: {
                startActivityForResult(intent, SELECT_HP_REQUEST_CODE);
                break;
            }
            case R.id.btn_select_custom_half_hp: {
                startActivityForResult(intent, SELECT_HALF_HP_REQUEST_CODE);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case SELECT_HP_REQUEST_CODE: {
                    hpUri = data.getData();
                    if (hpUri != null) {
                        tvHp.setText(hpUri.getPath());
                        useCustomHpImage = true;
                        cbCustomHp.setChecked(true);
                        refreshHp();
                    }
                    break;
                }
                case SELECT_HALF_HP_REQUEST_CODE: {
                    halfHpUri = data.getData();
                    if (halfHpUri != null) {
                        tvHalfHp.setText(halfHpUri.getPath());
                        useCustomHalfHpImage = true;
                        cbCustomHalfHp.setChecked(true);
                        refreshHp();
                    }
                    break;
                }
            }
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CardMakerActivity) {
            activity = (CardMakerActivity) context;
            ivHp1 = activity.getCmHp1();
            ivHp2 = activity.getCmHp2();
            ivHp3 = activity.getCmHp3();
            ivHp4 = activity.getCmHp4();
            ivHp5 = activity.getCmHp5();

        }
    }

    private void refreshWHText() {
        tvHpWidth.setText(String.valueOf(ivHp1.getWidth()));
        tvHpHeight.setText(String.valueOf(ivHp1.getHeight()));
    }

    @NonNull
    @Override
    public String toString() {
        return "勾玉";
    }

    private void refreshHp() {
        if (standardMode) {
            refreshStdHp();
        } else {
            refreshGuoZhanHp();
        }
    }

    private void refreshGuoZhanHp() {
        ivHp1.setVisibility(View.GONE);
        ivHp2.setVisibility(View.GONE);
        ivHp3.setVisibility(View.GONE);
        ivHp4.setVisibility(View.GONE);
        ivHp5.setVisibility(View.GONE);
        tvCurHp.setText(String.valueOf(guozhanHp));
        switch ((int) guozhanHp) {
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
        }
        RequestBuilder<Drawable> rb;
        switch (activity.getGroup()) {
            case Wei:
                loadWei();
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.wei_hp_half).transition(withCrossFade());
                }
                break;
            case Shu:
                loadShu();
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.shu_hp_half).transition(withCrossFade());
                }
                break;
            case Wu:
                loadWu();
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.wu_hp_half).transition(withCrossFade());
                }
                break;
            case Qun:
                loadQun();
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.qun_hp_half).transition(withCrossFade());
                }
                break;
            case God:
                loadGod();
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.god_hp_half).transition(withCrossFade());
                }
                break;
            default:
                if (useCustomHalfHpImage && halfHpUri != null) {
                    rb = Glide.with(this).load(halfHpUri).transition(withCrossFade());
                } else {
                    rb = Glide.with(this).load(R.drawable.wei_hp_half).transition(withCrossFade());
                }

        }
        switch (String.valueOf(guozhanHp)) {
            case "4.5":
                ivHp5.setVisibility(View.VISIBLE);
                rb.into(ivHp5);
                break;
            case "3.5":
                ivHp4.setVisibility(View.VISIBLE);
                rb.into(ivHp4);
                break;
            case "2.5":
                ivHp3.setVisibility(View.VISIBLE);
                rb.into(ivHp3);
                break;
            case "1.5":
                ivHp2.setVisibility(View.VISIBLE);
                rb.into(ivHp2);
                break;
            case "0.5":
                ivHp1.setVisibility(View.VISIBLE);
                rb.into(ivHp1);
                break;
        }
    }

    private void refreshStdHp() {
        ivHp1.setVisibility(View.GONE);
        ivHp2.setVisibility(View.GONE);
        ivHp3.setVisibility(View.GONE);
        ivHp4.setVisibility(View.GONE);
        ivHp5.setVisibility(View.GONE);
        tvCurHp.setText(String.valueOf(stdHp));
        switch (stdHp) {
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
        }
        switch (activity.getGroup()) {
            case Wei:
                loadWei();
                break;
            case Shu:
                loadShu();
                break;
            case Wu:
                loadWu();
                break;
            case Qun:
                loadQun();
                break;
            case God:
                loadGod();
                break;
        }
    }

    private void loadWei() {
        RequestBuilder<Drawable> rb;
        if (useCustomHpImage && hpUri != null) {
            rb = Glide.with(this).load(hpUri).transition(withCrossFade());
        } else {
            rb = Glide.with(this).load(standardMode ? R.drawable.wei_hp : R.drawable.wei_hp_double).transition(withCrossFade());
        }
        rb.into(ivHp1);
        rb.into(ivHp2);
        rb.into(ivHp3);
        rb.into(ivHp4);
        rb.into(ivHp5);
    }

    private void loadShu() {
        RequestBuilder<Drawable> rb;
        if (useCustomHpImage && hpUri != null) {
            rb = Glide.with(this).load(hpUri).transition(withCrossFade());
        } else {
            rb = Glide.with(this).load(standardMode ? R.drawable.shu_hp : R.drawable.shu_hp_double).transition(withCrossFade());
        }
        rb.into(ivHp1);
        rb.into(ivHp2);
        rb.into(ivHp3);
        rb.into(ivHp4);
        rb.into(ivHp5);
    }

    private void loadWu() {
        RequestBuilder<Drawable> rb;
        if (useCustomHpImage && hpUri != null) {
            rb = Glide.with(this).load(hpUri).transition(withCrossFade());
        } else {
            rb = Glide.with(this).load(standardMode ? R.drawable.wu_hp : R.drawable.wu_hp_double).transition(withCrossFade());
        }
        rb.into(ivHp1);
        rb.into(ivHp2);
        rb.into(ivHp3);
        rb.into(ivHp4);
        rb.into(ivHp5);
    }

    private void loadQun() {
        RequestBuilder<Drawable> rb;
        if (useCustomHpImage && hpUri != null) {
            rb = Glide.with(this).load(hpUri).transition(withCrossFade());
        } else {
            rb = Glide.with(this).load(standardMode ? R.drawable.qun_hp : R.drawable.qun_hp_double).transition(withCrossFade());
        }
        rb.into(ivHp1);
        rb.into(ivHp2);
        rb.into(ivHp3);
        rb.into(ivHp4);
        rb.into(ivHp5);
    }

    private void loadGod() {
        RequestBuilder<Drawable> rb;
        if (useCustomHpImage && hpUri != null) {
            rb = Glide.with(this).load(hpUri).transition(withCrossFade());
        } else {
            rb = Glide.with(this).load(standardMode ? R.drawable.god_hp : R.drawable.god_hp_double).transition(withCrossFade());
        }
        rb.into(ivHp1);
        rb.into(ivHp2);
        rb.into(ivHp3);
        rb.into(ivHp4);
        rb.into(ivHp5);
    }

    private void addHeight(ImageView view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height += 1;
        view.requestLayout();
    }

    private void addWidth(ImageView view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width += 1;
        view.requestLayout();
    }

    private void reduceHeight(ImageView view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height -= 1;
        view.requestLayout();
    }

    private void reduceWidth(ImageView view) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width -= 1;
        view.requestLayout();
    }

}
