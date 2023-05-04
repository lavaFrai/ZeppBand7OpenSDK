package ru.lavafrai.zeppBand7OpenSDK.utils;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CountingInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FSHelper {
    public static boolean isDirectoryEmpty(String path) {
        File directory = new File(path);
        if (directory.isDirectory()) {
            String[] files = directory.list();
            return files == null || files.length == 0;
        } else {
            throw new IllegalArgumentException("Путь не является директорией");
        }
    }

    public static void downloadFile(String _url, String targetPath) {
        try {

            URL url = new URL(_url);

            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            try(
                    ProgressBar pb = new ProgressBar("Download ZMake", Math.floorDiv(completeFileSize, 1000));
                    FileOutputStream fileOS = new FileOutputStream(targetPath);
                    CountingInputStream cis = new CountingInputStream(url.openStream());
            ) {
                pb.setExtraMessage("Downloading...");

                new Thread(() -> {
                    try {
                        IOUtils.copyLarge(cis, fileOS);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                while (cis.getByteCount() < completeFileSize) {
                    pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
                }
                pb.stepTo(Math.floorDiv(cis.getByteCount(), 1000));
                System.out.println("File downloaded successfully!");
            };

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
