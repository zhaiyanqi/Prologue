package cn.zhaiyanqi.prologue.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class FileUtil {


    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath = null;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        try (Cursor cursor = contentResolver.query(selectedVideoUri,
                filePathColumn, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
            }
        }
        return filePath;
    }
}
