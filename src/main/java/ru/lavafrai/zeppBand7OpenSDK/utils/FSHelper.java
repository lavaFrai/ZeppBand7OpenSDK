package ru.lavafrai.zeppBand7OpenSDK.utils;

import java.io.File;

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
}
