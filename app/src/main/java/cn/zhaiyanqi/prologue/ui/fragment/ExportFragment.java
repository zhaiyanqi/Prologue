package cn.zhaiyanqi.prologue.ui.fragment;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhaiyanqi.prologue.R;
import cn.zhaiyanqi.prologue.ui.activity.CardMakerActivity;
import cn.zhaiyanqi.prologue.ui.fragment.base.BaseMakerFragment;

public class ExportFragment extends BaseMakerFragment {

    private RxPermissions rxPermissions;
    private Bitmap.CompressFormat exportFormat = Bitmap.CompressFormat.PNG;

    public ExportFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        ButterKnife.bind(this, view);
        rxPermissions = new RxPermissions(this);
        return view;
    }

    @OnClick({R.id.rb_format_png, R.id.rb_format_jpg, R.id.rb_format_webp, R.id.export_photo})
    void exportImage(View view) {
        switch (view.getId()) {
            case R.id.rb_format_png: {
                exportFormat = Bitmap.CompressFormat.PNG;
                break;
            }
            case R.id.rb_format_jpg: {
                exportFormat = Bitmap.CompressFormat.JPEG;
                break;
            }
            case R.id.rb_format_webp: {
                exportFormat = Bitmap.CompressFormat.WEBP;
                break;
            }
            case R.id.export_photo: {
                FragmentActivity activity = getActivity();
                if (activity instanceof CardMakerActivity) {
                    ((CardMakerActivity) activity).startMakerFull();
                }
//                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .subscribe(granted -> {
//                            if (granted) {
//                                new Thread(() -> {
//                                    if (saveToGallery(String.valueOf(new Date().getTime()), exportFormat)) {
//                                        activity().runOnUiThread(() -> Toast.makeText(this.getContext(), R.string.export_done, Toast.LENGTH_SHORT).show());
//                                    } else {
//                                        activity().runOnUiThread(() -> Toast.makeText(this.getContext(), R.string.export_fail, Toast.LENGTH_SHORT).show());
//                                    }
//                                }).start();
//                            } else {
//                                Toast.makeText(this.getContext(), R.string.no_permission, Toast.LENGTH_SHORT).show();
//                            }
//                        });
                break;
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "导出";
    }

    private boolean saveToGallery(String fileName, Bitmap.CompressFormat
            format) {
        String subFolderPath = "三国杀制图";
        String fileDescription = "Sanguosha Save";
        int quality = 100;

        long currentTime = System.currentTimeMillis();

        File extBaseDir = Environment.getExternalStorageDirectory();
        File file = new File(extBaseDir.getAbsolutePath() + "/DCIM/" + subFolderPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }

        String mimeType;
        switch (format) {
            case PNG:
                mimeType = "image/png";
                if (!fileName.endsWith(".png"))
                    fileName += ".png";
                break;
            case WEBP:
                mimeType = "image/webp";
                if (!fileName.endsWith(".webp"))
                    fileName += ".webp";
                break;
            case JPEG:
            default:
                mimeType = "image/jpeg";
                if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")))
                    fileName += ".jpg";
                break;
        }

        String filePath = file.getAbsolutePath() + "/" + fileName;
        FileOutputStream out;
        try {
            out = new FileOutputStream(filePath);

            Bitmap b = activity().getChartBitmap();
            b.compress(format, quality, out);

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        long size = new File(filePath).length();

        ContentValues values = new ContentValues(8);

        // store the details
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.DESCRIPTION, fileDescription);
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.DATA, filePath);
        values.put(MediaStore.Images.Media.SIZE, size);

        return activity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) != null;
    }
}
