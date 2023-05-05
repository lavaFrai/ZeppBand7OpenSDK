package ru.lavafrai.zeppBand7OpenSDK;

import net.lingala.zip4j.ZipFile;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ZeppPlayer {
    static boolean localZeppPlayer = false;
    private final Process process;


    ZeppPlayer(String[] processArgs) {

        ProcessBuilder processBuilder;
        if (localZeppPlayer) processBuilder = new ProcessBuilder(  Constants.zeppPlayerCachingPath + "/ZeppPlayer.exe", String.join(" ", processArgs));
        else processBuilder = new ProcessBuilder("ZeppPlayer.exe", String.join(" ", processArgs));

        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public static void stopIfRunning() {
        Logger.getInstance().info("Stopping zepp player");

        try {
            Runtime.getRuntime().exec("taskkill /f /im ZeppPlayer.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void copyProjectBuildToZeppPlayerPortable(String projectPath) {
        String zeppPlayerPortableStoragePath = Constants.zeppPlayerCachingPath + "/projects/";
        String projectBuildPath = projectPath + "/build/";

        File directory = new File(zeppPlayerPortableStoragePath);
        if (directory.exists()) FSHelper.clearDirectory(directory);

        FSHelper.copyDirectory(new File(projectBuildPath), new File(zeppPlayerPortableStoragePath + Paths.get(projectPath).getFileName()));
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

    public static ZeppPlayer runProject(String projectPath) {
        ZeppPlayer.copyProjectBuildToZeppPlayerPortable(projectPath);

        ZeppPlayer zeppPlayer = new ZeppPlayer(new String[] {});

        /*
        try {
            synchronized (zeppPlayer.process) {
                zeppPlayer.process.waitFor();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            zeppPlayer.process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        */


        return zeppPlayer;
    }
}
