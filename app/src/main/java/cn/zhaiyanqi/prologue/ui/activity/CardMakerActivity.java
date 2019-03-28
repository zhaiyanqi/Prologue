package cn.zhaiyanqi.prologue.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.enums.HeroGroup;
import cn.zhaiyanqi.prologue.ui.fragment.AdjustFragment;
import cn.zhaiyanqi.prologue.ui.fragment.ExportFragment;
import cn.zhaiyanqi.prologue.ui.fragment.HpFragment;
import cn.zhaiyanqi.prologue.ui.fragment.NameFragment;
import cn.zhaiyanqi.prologue.ui.fragment.TemplateFragment;
import cn.zhaiyanqi.prologue.ui.fragment.TitleFragment;

public class CardMakerActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.card_maker_view_pager)
    ViewPager viewPager;

    //武将牌的元素
    @BindView(R.id.root_layout)
    ConstraintLayout cardWrapper;
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

    private ViewPagerAdapter adapter;
    private long exitTime;
    private HeroGroup group;

    @BindView(R.id.hero_card_wrapper)
    ConstraintLayout cardLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_maker);
        ButterKnife.bind(this);
        initViewPager();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private void initViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new TemplateFragment());
        adapter.addItem(new TitleFragment());
        adapter.addItem(new NameFragment());
        adapter.addItem(new HpFragment());
        adapter.addItem(new AdjustFragment());
        adapter.addItem(new ExportFragment());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
    }

    public Bitmap getCardBitmap() throws InterruptedException {
        int w = cardWrapper.getWidth();
        int h = cardWrapper.getHeight();
        Bitmap returnedBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        cardWrapper.layout(0, 0, w, h);
        cardWrapper.draw(canvas);
        Thread.sleep(3000);
        return returnedBitmap;
    }
// public Bitmap getCardBitmap() {
//     cardWrapper.setDrawingCacheEnabled(true);
//     cardWrapper.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//             View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//     cardWrapper.layout(0, 0, cardWrapper.getMeasuredWidth(), cardWrapper.getMeasuredHeight());
//     cardWrapper.buildDrawingCache();
//     Bitmap bitmap = Bitmap.createBitmap(cardWrapper.getDrawingCache());
//     cardWrapper.setDrawingCacheEnabled(false);
//     return bitmap;
// }

    public HeroGroup getGroup() {
        return group;
    }

    public void setGroup(HeroGroup group) {
        this.group = group;
    }

    public TextView getCmName() {
        return cmName;
    }

    public TextView getCmTitle() {
        return cmTitle;
    }

    public PhotoView getCmPhotoView() {
        return cmPhotoView;
    }

    public PhotoView getCmPhotoViewOut() {
        return cmPhotoViewOut;
    }

    public ImageView getCmFrame() {
        return cmFrame;
    }

    public ImageView getCmGroup() {
        return cmGroup;
    }

    public ImageView getCmSkillBoard() {
        return cmSkillBoard;
    }

    public LinearLayout getCmHpLayout() {
        return cmHpLayout;
    }

    public ImageView getCmHp1() {
        return cmHp1;
    }

    public ImageView getCmHp2() {
        return cmHp2;
    }

    public ImageView getCmHp3() {
        return cmHp3;
    }

    public ImageView getCmHp4() {
        return cmHp4;
    }

    public ImageView getCmHp5() {
        return cmHp5;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            list = new ArrayList<>();
        }

        void addItem(Fragment fragment) {
            list.add(fragment);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).toString();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}




