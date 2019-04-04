package cn.zhaiyanqi.prologue.ui.callback;

public interface ItemMoveListener {
    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemove(int position);
}
