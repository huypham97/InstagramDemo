package com.huypham.instagramdemo.utils.common;

import android.content.Context;
import android.util.Pair;

import java.io.File;
import java.io.InputStream;

public interface FileHelper {

    public File makeFile(String path);

    public File getDirectory(Context context, String dirName);

    public File saveInputStreamToFile(
            InputStream input,
            File directory,
            String imageName,
            int height
    );

    public Pair<Integer, Integer> getImageSize(File file);

}
