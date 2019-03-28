package cn.zhaiyanqi.prologue.ui.fragment.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;

public class BaseMakerFragment extends Fragment {

    protected CardMakerActivity activity;

    protected CardMakerActivity activity() {
        FragmentActivity activity = getActivity();
        if (activity instanceof CardMakerActivity) {
            return (CardMakerActivity) activity;
        }
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CardMakerActivity) {
            activity = (CardMakerActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (activity != null) {
            activity = null;
        }
    }
}
