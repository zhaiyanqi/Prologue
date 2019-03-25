package cn.zhaiyanqi.prologue.cardmaker;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.zhaiyanqi.prologue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HpFragment extends Fragment {


    public HpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hp, container, false);
    }


    @NonNull
    @Override
    public String toString() {
        return "勾玉";
    }
}
