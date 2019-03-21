package cn.zhaiyanqi.prologue.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

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
import butterknife.OnCheckedChanged;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import cn.zhaiyanqi.prologue.App;
import cn.zhaiyanqi.prologue.R;

public class CardMakerActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.card_maker_view_pager)
    ViewPager viewPager;
    //武将牌的元素
    @BindView(R.id.hero_maker_name)
    TextView cmName;
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
        for (int i = 0; i < 20; i++) {
            adapter.addItem(new NameFragment());
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class NameFragment extends Fragment {

        private CardMakerActivity activity;
        private TextView nameView;
        private boolean autoTrans2T;

        public NameFragment() {
        }

        @OnItemSelected(R.id.spinner_font)
        void setFont(int position) {
            if (position < App.fontNameArray.length) {
                String font = App.fontNameArray[position];
                Typeface typeface = App.fonts.get(font);
                nameView.setTypeface(typeface);
            }
        }

        @OnTextChanged(R.id.et_name)
        void setName(CharSequence name) {
            if (nameView != null) {
                if (!TextUtils.isEmpty(name)) {
                    String str = autoTrans2T ?
                            ChineseConverter.convert(name.toString(), ConversionType.S2T, activity)
                            : name.toString();
                    nameView.setText(str);
                } else {
                    nameView.setText("");
                }
            }
        }

        @OnCheckedChanged(R.id.cb_auto_transfer)
        void changeTransferState(boolean checked) {
            if (autoTrans2T != checked) {
                autoTrans2T = checked;
                String str;
                if (nameView != null && !TextUtils.isEmpty(str = nameView.getText().toString())) {
                    str = autoTrans2T ?
                            ChineseConverter.convert(str, ConversionType.S2T, activity)
                            : ChineseConverter.convert(str, ConversionType.T2S, activity);
                    nameView.setText(str);
                }
            }
        }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_name, container, false);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            if (context instanceof CardMakerActivity) {
                activity = (CardMakerActivity) context;
                nameView = activity.cmName;
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            if (activity != null) {
                activity = null;
            }
            if (nameView != null) {
                nameView = null;
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "武将名";
        }
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




