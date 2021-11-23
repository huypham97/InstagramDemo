package com.huypham.instagramdemo.utils.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Pair;

import com.mindorks.paracamera.Camera;
import com.mindorks.paracamera.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils implements FileHelper {
    @Override
    public File makeFile(String path) {
        return new File(path);
    }

    @Override
    public File getDirectory(Context context, String dirName) {
        File file = new File(context.getFilesDir().getPath() + File.separator + dirName);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    @Override
    public File saveInputStreamToFile(InputStream input, File directory, String imageName, int height) {
        File temp = new File(directory.getPath() + File.separator + "temp\\$file\\$for\\$processing");
        File finalFile = new File(directory.getPath() + File.separator + imageName + ".${Camera.IMAGE_JPG}");
        byte[] buffer = new byte[4 * 1024];
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            int read = input.read(buffer);
            while (read != -1) {
                output.write(buffer, 0, read);
                read = input.read(buffer);
            }
            output.flush();
            Utils.saveBitmap(Utils.decodeFile(temp, height), finalFile.getPath(), Camera.IMAGE_JPEG, 80);
            return finalFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp.delete();
        }

        return null;
    }

    @Override
    public Pair<Integer, Integer> getImageSize(File file) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);
            return new Pair<>(o.outWidth, o.outHeight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
