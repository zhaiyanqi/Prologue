package cn.zhaiyanqi.prologue.ui.fragment.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;

public class BaseMakerFragment extends Fragment {


    protected CardMakerActivity activity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof CardMakerActivity) {
            return (CardMakerActivity) activity;
        }
        return null;
    }
}
