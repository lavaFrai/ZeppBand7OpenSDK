package ru.lavafrai.zeppBand7OpenSDK;

import net.lingala.zip4j.ZipFile;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ZeppPlayer {
    static boolean localZeppPlayer = false;
    private final Process process;


    ZeppPlayer(String[] processArgs) {

        ProcessBuilder processBuilder;
        if (localZeppPlayer) processBuilder = new ProcessBuilder(  Constants.zeppPlayerCachingPath + "/ZeppPlayer.exe", String.join(" ", processArgs));
        else processBuilder = new ProcessBuilder("ZeppPlayer.exe", String.join(" ", processArgs));

        processBuilder.redirectErrorStream(true);
        // processBuilder.inheritIO();

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public void stop() {
        if (process.isAlive()) {
            process.destroy();
        }
    }

    static boolean isAvailableFromCache() {
        ProcessBuilder pb = new ProcessBuilder(Constants.zeppPlayerCachingPath + "/zeppPlayer.exe");
        try {
            Process process = pb.start();
            process.destroy();
        } catch (IOException e) {
            return false;
        }

        Logger.getInstance().info("Running ZeppPlayer portable");
        localZeppPlayer = true;
        return true;
    }

    public static void downloadZeppPlayer() {
        FSHelper.downloadFile(Constants.zeppPlayerDownloadPath, "zeppPlayer.zip");

        try (
                ZipFile zipFile = new ZipFile("zeppPlayer.zip")
        ) {
            zipFile.extractAll(Constants.zeppPlayerCachingPath);
            Files.deleteIfExists(new File("zeppPlayer.zip").toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        localZeppPlayer = true;
    }

    public static void zeppPlayerSelfCheck() {
        java.util.logging.Logger logger = Logger.getInstance();

        logger.info("Looking for zeppPlayer...");
        if (!ZeppPlayer.isAvailableFromCache()) {
            logger.warning("zeppPlayer undetected [ERROR]");
            ZeppPlayer.downloadZeppPlayer();
        } else {
            logger.info("zeppPlayer detected [OK]");
        }
    }

    public static ZeppPlayer runProject() {


        ZeppPlayer zeppPlayer = new ZeppPlayer(new String[] {});
        return null;
    }
}
