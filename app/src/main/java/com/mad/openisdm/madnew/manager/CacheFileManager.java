package com.mad.openisdm.madnew.manager;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.Future;


/**
 * Created by aming on 2015/9/13.
 */
public class CacheFileManager {
    private File mFile;
    private String mCacheFilePath;
    private static CacheFileManager mCacheFileManager;

    public static synchronized CacheFileManager getInstance() {
        if (mCacheFileManager == null) {
            mCacheFileManager = new CacheFileManager();
        }

        return mCacheFileManager;
    }

    private CacheFileManager() {}

    public void setActivity(Activity activity) {
        mFile = activity.getCacheDir();
        mCacheFilePath = mFile.getAbsolutePath();
    }

    public boolean mkdir(String path) {
        File file = new File(mCacheFilePath + "/" + path);

        if (mFile.canWrite()) {
            return (file.mkdir() && file.isDirectory());
        }

        return false;
    }

    public boolean dirExists(String path) {
        File file = new File(mCacheFilePath + "/" + path);
        return file.isDirectory();
    }

    public boolean fileExists(String path) {
        File file = new File(mCacheFilePath + "/" + path);
        return file.exists();
    }

    public void write(String path, String content) throws IOException, FileNotFoundException {
        // Check if the directory can be write
        if (mFile.canWrite()) {
            boolean appendFile = false;
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;

            try {
                fileWriter = new FileWriter(mCacheFilePath + "/" + path, appendFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content);
            } finally {
                if (fileWriter != null) {
                    fileWriter.close();
                }

                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
        }
        else {
            Log.v(this.toString(), "Cannot write on cache directory");
            throw new IOException("Cannot write on cache directory");
        }
    }

    public String read(String path) throws IOException, FileNotFoundException {
        if (mFile.canRead()) {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                fileReader = new FileReader(mCacheFilePath + "/" + path);
                bufferedReader = new BufferedReader(fileReader);
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
            } finally {
                if (fileReader != null) {
                    fileReader.close();
                }

                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }

            return stringBuilder.toString();
        }
        else {
            Log.v(this.toString(), "Cannot read on cache directory");
            throw new IOException("Cannot read on cache directory");
        }
    }
}
