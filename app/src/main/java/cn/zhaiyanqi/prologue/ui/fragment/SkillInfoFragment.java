package cn.zhaiyanqi.prologue.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillInfoFragment extends BaseMakerFragment {


    public SkillInfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public String toString() {
        return "技能信息";
    }
}
