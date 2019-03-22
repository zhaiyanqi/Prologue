package cn.zhaiyanqi.prologue.utils;

public class ColorUtil {


    public static String int2HexColor(long color) {
        try {
            StringBuilder sb = new StringBuilder(9);
            sb.append("#");
            String str = Long.toHexString(color);
            sb.append(str.length() <= 8 ? str : str.substring(8));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
