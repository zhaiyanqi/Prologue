package cn.zhaiyanqi.prologue.ui.bean;

public class PadSettingBean {

    private int moveStep;
    private int scaleStep;
    private int mainLayoutWidth;
    private int mainLayoutHeight;
    private boolean aspectRatio;

    public int getMoveStep() {
        return moveStep;
    }

    public void setMoveStep(int moveStep) {
        this.moveStep = moveStep;
    }

    public int getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(int scaleStep) {
        this.scaleStep = scaleStep;
    }

    public int getMainLayoutWidth() {
        return mainLayoutWidth;
    }

    public void setMainLayoutWidth(int mainLayoutWidth) {
        this.mainLayoutWidth = mainLayoutWidth;
    }

    public int getMainLayoutHeight() {
        return mainLayoutHeight;
    }

    public void setMainLayoutHeight(int mainLayoutHeight) {
        this.mainLayoutHeight = mainLayoutHeight;
    }

    public boolean isAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(boolean aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}
