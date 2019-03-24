package cn.zhaiyanqi.prologue.cardmaker;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaiyanqi.prologue.R;

public class CardMakerActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.card_maker_view_pager)
    ViewPager viewPager;

    //武将牌的元素
    @BindView(R.id.hero_maker_name)
    TextView cmName;
    @BindView(R.id.hero_maker_title)
    TextView cmTitle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_maker);
        ButterKnife.bind(this);

        initViewPager();
    }

    private void initViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addItem(new TemplateFragment());
        adapter.addItem(new TitleFragment());
        adapter.addItem(new NameFragment());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            list = new ArrayList<>();
        }

        public void addItem(Fragment fragment) {
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




