package cn.zhaiyanqi.prologue.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;

import static android.app.Activity.RESULT_OK;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class TemplateFragment extends Fragment {

    private static final int SELECT_FRAME_REQUEST_CODE = 1;
    private static final int SELECT_GROUP_REQUEST_CODE = 2;
    private static final int SELECT_PHOTO_REQUEST_CODE = 3;
    private static final int SELECT_SKILL_BOARD_REQUEST_CODE = 4;
    @BindView(R.id.tv_custom_frame)
    TextView tvCustomFrame;
    @BindView(R.id.tv_custom_group)
    TextView tvCustomGroup;
    @BindView(R.id.tv_custom_photo)
    TextView tvCustomPhoto;
    @BindView(R.id.tv_custom_skill_board)
    TextView tvCustomSkillBoard;

    @BindView(R.id.cb_custom_frame)
    CheckBox cbFrame;
    @BindView(R.id.cb_custom_group)
    CheckBox cbGroup;
    @BindView(R.id.cb_custom_skill_board)
    CheckBox cbSkillBoard;


    private ImageView cmFrame, cmGroup, cmSkillBoard;
    private Uri frameUri, groupUri, photoUri, skillBoardUri;
    private ImageView ivHp1, ivHp2, ivHp3, ivHp4, ivHp5;
    private LinearLayout ivHpLayout;
    private PhotoView photoView, outPhotoView;
    private boolean outFrame;

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

    @OnCheckedChanged({R.id.cb_show_frame, R.id.cb_show_logo,
            R.id.cb_show_skill, R.id.cb_show_hp, R.id.cb_show_img})
    void switchShowAndHide(CompoundButton button, boolean checked) {
        switch (button.getId()) {
            case R.id.cb_show_frame: {
                cmFrame.setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_show_logo: {
                cmGroup.setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_show_skill: {
                cmSkillBoard.setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_show_hp: {
                ivHpLayout.setVisibility(checked ? View.VISIBLE : View.GONE);
                break;
            }
            case R.id.cb_show_img: {
                if (outFrame) {
                    outPhotoView.setVisibility(checked ? View.VISIBLE : View.GONE);
                } else {
                    photoView.setVisibility(checked ? View.VISIBLE : View.GONE);
                }
                break;
            }
        }
    }

    @OnClick({R.id.btn_select_custom_frame, R.id.btn_select_custom_group,
            R.id.btn_select_custom_photo, R.id.btn_select_custom_skill_board})
    void selectCustomImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        switch (view.getId()) {
            case R.id.btn_select_custom_frame: {
                startActivityForResult(intent, SELECT_FRAME_REQUEST_CODE);
                break;
            }
            case R.id.btn_select_custom_group: {
                startActivityForResult(intent, SELECT_GROUP_REQUEST_CODE);
                break;
            }
            case R.id.btn_select_custom_photo: {
                startActivityForResult(intent, SELECT_PHOTO_REQUEST_CODE);
                break;
            }
            case R.id.btn_select_custom_skill_board: {
                startActivityForResult(intent, SELECT_SKILL_BOARD_REQUEST_CODE);
                break;
            }
        }
    }

    @OnCheckedChanged({R.id.cb_custom_frame, R.id.cb_custom_group, R.id.cb_custom_photo_out, R.id.cb_custom_skill_board})
    void switchFrame(CompoundButton button, boolean checked) {
        switch (button.getId()) {
            case R.id.cb_custom_frame: {
                if (checked && frameUri != null) {
                    Glide.with(this).load(frameUri).transition(withCrossFade()).into(cmFrame);
                } else {
                    Glide.with(this).load(R.drawable.wei).transition(withCrossFade()).into(cmFrame);
                }
                break;
            }
            case R.id.cb_custom_group: {
                if (checked && groupUri != null) {
                    Glide.with(this).load(groupUri).transition(withCrossFade()).into(cmGroup);
                } else {
                    Glide.with(this).load(R.drawable.wei_logo).transition(withCrossFade()).into(cmGroup);
                }
                break;
            }
            case R.id.cb_custom_photo_out: {
                outFrame = checked;
                if (checked && photoUri != null) {
                    outPhotoView.setImageURI(photoUri);
                    outPhotoView.setVisibility(View.VISIBLE);
                    photoView.setVisibility(View.GONE);
                } else {
                    photoView.setImageURI(photoUri);
                    photoView.setVisibility(View.VISIBLE);
                    outPhotoView.setVisibility(View.GONE);
                }
                break;
            }
            case R.id.cb_custom_skill_board: {
                if (checked && skillBoardUri != null) {
                    Glide.with(this).load(skillBoardUri).transition(withCrossFade()).into(cmSkillBoard);
                } else {
                    Glide.with(this).load(R.drawable.wei_skill_board).transition(withCrossFade()).into(cmSkillBoard);
                }
                break;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case SELECT_FRAME_REQUEST_CODE: {
                    frameUri = data.getData();
                    if (frameUri != null) {
                        tvCustomFrame.setText(frameUri.getPath());
                        Glide.with(this).load(frameUri).transition(withCrossFade()).into(cmFrame);
                        cbFrame.setChecked(true);
                    }
                    break;
                }
                case SELECT_GROUP_REQUEST_CODE: {
                    groupUri = data.getData();
                    if (groupUri != null) {
                        tvCustomGroup.setText(groupUri.getPath());
                        Glide.with(this).load(groupUri).transition(withCrossFade()).into(cmGroup);
                        cbGroup.setChecked(true);
                    }
                    break;
                }
                case SELECT_PHOTO_REQUEST_CODE: {
                    photoUri = data.getData();
                    if (photoUri != null) {
                        tvCustomPhoto.setText(photoUri.getPath());
                        photoView.setImageURI(photoUri);
                        outPhotoView.setImageURI(photoUri);
                    }
                    break;
                }
                case SELECT_SKILL_BOARD_REQUEST_CODE: {
                    skillBoardUri = data.getData();
                    if (skillBoardUri != null) {
                        tvCustomSkillBoard.setText(skillBoardUri.getPath());
                        Glide.with(this).load(skillBoardUri).transition(withCrossFade()).into(cmSkillBoard);
                        cbSkillBoard.setChecked(true);
                    }
                    break;
                }
            }
        }
    }

    @OnClick(R.id.rb_wei)
    void loadWei() {
        Glide.with(this).load(R.drawable.wei).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.wei_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.wei_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.wei_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp1);
        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp2);
        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp3);
        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp4);
        Glide.with(this).load(R.drawable.wei_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_shu)
    void loadShu() {
        Glide.with(this).load(R.drawable.shu).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.shu_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.shu_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.shu_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp1);
        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp2);
        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp3);
        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp4);
        Glide.with(this).load(R.drawable.shu_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_wu)
    void loadWu() {
        Glide.with(this).load(R.drawable.wu).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.wu_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.wu_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.wu_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp1);
        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp2);
        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp3);
        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp4);
        Glide.with(this).load(R.drawable.wu_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_qun)
    void loadQun() {
        Glide.with(this).load(R.drawable.qun).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.qun_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.qun_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.qun_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp1);
        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp2);
        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp3);
        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp4);
        Glide.with(this).load(R.drawable.qun_hp).transition(withCrossFade()).into(ivHp5);
    }

    @OnClick(R.id.rb_god)
    void loadGod() {
        Glide.with(this).load(R.drawable.god).transition(withCrossFade()).into(cmFrame);
        Glide.with(this).load(R.drawable.god_logo).transition(withCrossFade()).into(cmGroup);
        Glide.with(this).load(R.drawable.god_skill_board).transition(withCrossFade()).into(cmSkillBoard);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill1Bar);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill2Bar);
//        Glide.with(this).load(R.drawable.god_skill_bar).transition(withCrossFade()).into(ivSkill3Bar);
        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp1);
        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp2);
        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp3);
        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp4);
        Glide.with(this).load(R.drawable.god_hp).transition(withCrossFade()).into(ivHp5);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CardMakerActivity) {
            CardMakerActivity activity = (CardMakerActivity) context;
            cmFrame = activity.getCmFrame();
            cmGroup = activity.getCmGroup();
            cmSkillBoard = activity.getCmSkillBoard();
            ivHpLayout = activity.getCmHpLayout();
            photoView = activity.getCmPhotoView();
            outPhotoView = activity.getCmPhotoViewOut();
            ivHp1 = activity.getCmHp1();
            ivHp2 = activity.getCmHp2();
            ivHp3 = activity.getCmHp3();
            ivHp4 = activity.getCmHp4();
            ivHp5 = activity.getCmHp5();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cmFrame = null;
        cmGroup = null;
        cmSkillBoard = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "模板";
    }
}
