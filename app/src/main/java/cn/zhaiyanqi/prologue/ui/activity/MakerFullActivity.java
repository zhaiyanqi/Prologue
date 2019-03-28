package cn.zhaiyanqi.prologue.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.bean.CardMakerBean;

public class MakerFullActivity extends AppCompatActivity {

    //武将牌的元素
    @BindView(R.id.root_layout)
    ConstraintLayout rootView;
    @BindView(R.id.hero_maker_name)
    TextView cmName;
    @BindView(R.id.hero_maker_title)
    TextView cmTitle;
    @BindView(R.id.hero_maker_hero_img)
    PhotoView cmPhotoView;
    @BindView(R.id.hero_maker_hero_out_img)
    PhotoView cmPhotoViewOut;
    @BindView(R.id.hero_maker_frame)
    ImageView cmFrame;
    @BindView(R.id.hero_maker_group)
    ImageView cmGroup;
    @BindView(R.id.hero_maker_skill_board)
    ImageView cmSkillBoard;
    @BindView(R.id.hero_maker_hp)
    LinearLayout cmHpLayout;
    @BindView(R.id.iv_hp_1)
    ImageView cmHp1;
    @BindView(R.id.iv_hp_2)
    ImageView cmHp2;
    @BindView(R.id.iv_hp_3)
    ImageView cmHp3;
    @BindView(R.id.iv_hp_4)
    ImageView cmHp4;
    @BindView(R.id.iv_hp_5)
    ImageView cmHp5;

    private CardMakerBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_full);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            bean = (CardMakerBean) intent.getSerializableExtra("position");
//            rootView.postDelayed(() -> adjustPosition(bean), 300);
        }
    }

    private void adjustPosition(CardMakerBean bean) {
        if (bean == null) return;
        float scaleX = rootView.getMeasuredWidth() / bean.getWidth();
        float scaleY = rootView.getMeasuredHeight() / bean.getHeight();
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) cmGroup.getLayoutParams();
        params.width = (int) (params.width * scaleX);
        params.height = (int) (params.height * scaleY);
        params.leftMargin = (int) (params.leftMargin * scaleX);
        params.topMargin = (int) (params.topMargin * scaleX);
        cmGroup.requestLayout();
    }
}
