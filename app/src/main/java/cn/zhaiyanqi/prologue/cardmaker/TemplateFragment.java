package cn.zhaiyanqi.prologue.cardmaker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TemplateFragment extends Fragment {

    private ImageView cmBaseBoard, cmFrame, cmCloud, cmGroup, cmSkillBoard;

    public TemplateFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template, container, false);
        ButterKnife.bind(this, view);
        loadWei();
        return view;
    }

    @OnClick(R.id.rb_wei)
    void loadWei() {
        cmBaseBoard.setVisibility(View.VISIBLE);
        cmCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wei_base_board).transition(withCrossFade()).into(cmBaseBoard);
        Glide.with(this).load(R.drawable.wei).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.wei_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.wei_right_cloud).transition(withCrossFade()).into(cmCloud);
        Glide.with(this).load(R.drawable.wei_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
//        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp1);
//        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp2);
//        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp3);
//        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp4);
//        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_shu)
    void loadShu() {
        cmBaseBoard.setVisibility(View.VISIBLE);
        cmCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.shu_base_board).transition(withCrossFade()).into(cmBaseBoard);
        Glide.with(this).load(R.drawable.shu).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.shu_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.shu_right_cloud).transition(withCrossFade()).into(cmCloud);
        Glide.with(this).load(R.drawable.shu_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
//        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp1);
//        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp2);
//        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp3);
//        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp4);
//        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_wu)
    void loadWu() {
        cmBaseBoard.setVisibility(View.VISIBLE);
        cmCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.wu_base_board).transition(withCrossFade()).into(cmBaseBoard);
        Glide.with(this).load(R.drawable.wu).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.wu_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.wu_right_cloud).transition(withCrossFade()).into(cmCloud);
        Glide.with(this).load(R.drawable.wu_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
//        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp1);
//        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp2);
//        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp3);
//        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp4);
//        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_qun)
    void loadQun() {
        cmBaseBoard.setVisibility(View.VISIBLE);
        cmCloud.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.drawable.qun_base_board).transition(withCrossFade()).into(cmBaseBoard);
        Glide.with(this).load(R.drawable.qun).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.qun_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.qun_right_cloud).transition(withCrossFade()).into(cmCloud);
        Glide.with(this).load(R.drawable.qun_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
//        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp1);
//        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp2);
//        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp3);
//        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp4);
//        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_god)
    void loadGod() {
        cmBaseBoard.setVisibility(View.GONE);
        cmCloud.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.god).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.god_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.god_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
//        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp1);
//        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp2);
//        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp3);
//        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp4);
//        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp5);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CardMakerActivity) {
            CardMakerActivity activity = (CardMakerActivity) context;
            cmBaseBoard = activity.cmBaseBoard;
            cmFrame = activity.cmFrame;
            cmCloud = activity.cmCloud;
            cmGroup = activity.cmGroup;
            cmSkillBoard = activity.cmSkillBoard;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cmBaseBoard = null;
        cmFrame = null;
        cmCloud = null;
        cmGroup = null;
        cmSkillBoard = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "模板";
    }
}
