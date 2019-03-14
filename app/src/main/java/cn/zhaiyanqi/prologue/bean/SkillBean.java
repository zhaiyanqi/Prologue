package cn.zhaiyanqi.prologue.bean;

import org.litepal.crud.LitePalSupport;

public class SkillBean extends LitePalSupport {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
