package ru.lavafrai.zeppBand7OpenSDK;

import java.io.IOException;

public class ZMake {
    static boolean isAvailable() {
        ProcessBuilder pb = new ProcessBuilder("zmake");
        try {
            Process process = pb.start();
            process.destroy();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    static void downloadZMake() {

    }
}
