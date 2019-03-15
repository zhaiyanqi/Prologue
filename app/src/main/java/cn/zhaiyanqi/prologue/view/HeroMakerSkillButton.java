package cn.zhaiyanqi.prologue.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

public class HeroMakerSkillButton extends AppCompatButton {

    private TextView skillName, skillInfo;

    public HeroMakerSkillButton(Context context) {
        super(context);
    }

    public HeroMakerSkillButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeroMakerSkillButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView getSkillName() {
        return skillName;
    }

    public void setSkillName(TextView skillName) {
        this.skillName = skillName;
    }

    public TextView getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(TextView skillInfo) {
        this.skillInfo = skillInfo;
    }
}
