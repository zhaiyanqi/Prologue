package cn.zhaiyanqi.prologue.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;

import static android.app.Activity.RESULT_OK;

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
    private boolean standardMode = true;
    private int stdHp = 5;
    private float guozhanHp = 2.0f;
    private Uri hpUri, halfHpUri;
    private ImageView ivHp1, ivHp2, ivHp3, ivHp4, ivHp5;

    public HpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hp, container, false);
        ButterKnife.bind(this, view);
        return view;
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
    }

    @OnClick(R.id.btn_add)
    void addHp() {
        if (standardMode) {
            stdHp++;
            refreshHp();
        } else {
            guozhanHp += 0.5;
            refreshGuoZhanHp();
        }

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
                refreshGuoZhanHp();
            } else {
                Toast.makeText(this.getContext(), "至少要有半个勾玉", Toast.LENGTH_SHORT).show();
            }
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
    }

    private void refreshHp() {
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
//                        Glide.with(this).load(hpUri).transition(withCrossFade()).into(cmFrame);
                        cbCustomHp.setChecked(true);
                    }
                    break;
                }
                case SELECT_HALF_HP_REQUEST_CODE: {
                    halfHpUri = data.getData();
                    if (halfHpUri != null) {
                        tvHalfHp.setText(halfHpUri.getPath());
//                        Glide.with(this).load(halfHpUri).transition(withCrossFade()).into(cmGroup);
                        cbCustomHalfHp.setChecked(true);
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
            CardMakerActivity activity = (CardMakerActivity) context;
            ivHp1 = activity.getCmHp1();
            ivHp2 = activity.getCmHp2();
            ivHp3 = activity.getCmHp3();
            ivHp4 = activity.getCmHp4();
            ivHp5 = activity.getCmHp5();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "勾玉";
    }
}
