package ru.lavafrai.zeppBand7OpenSDK;

import net.lingala.zip4j.ZipFile;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.*;
import java.nio.file.Files;

public class ZMake {
    static boolean localZMake = false;
    private final Process process;
    BufferedReader reader;
    OutputStream writer;

    ZMake(String[] processArgs) {

        ProcessBuilder processBuilder;
        if (localZMake) processBuilder = new ProcessBuilder(  Constants.zmakeCachingPath + "/zmake.exe", String.join(" ", processArgs));
        else processBuilder = new ProcessBuilder("zmake", String.join(" ", processArgs));

        processBuilder.redirectErrorStream(true);
        // processBuilder.inheritIO();
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = process.getOutputStream();

    }

    static boolean isAvailableFromCache() {
        ProcessBuilder pb = new ProcessBuilder(Constants.zmakeCachingPath + "/zmake.exe");
        try {
            Process process = pb.start();
            process.destroy();
        } catch (IOException e) {
            return false;
        }

        Logger.getInstance().info("Running ZMake portable");
        localZMake = true;
        return true;
    }

    static boolean isAvailable() {
        ProcessBuilder pb = new ProcessBuilder("zmake");
        try {
            Process process = pb.start();
            process.destroy();
        } catch (IOException e) {
            return isAvailableFromCache();
        }

        return true;
    }

    public static void downloadZMake() {
        FSHelper.downloadFile(Constants.zmakeDownloadPath, "zmake.zip");

        try (
                ZipFile zipFile = new ZipFile("zmake.zip")
        ) {
            zipFile.extractAll(Constants.zmakeCachingPath);
            Files.deleteIfExists(new File("zmake.zip").toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        localZMake = true;
    }

    public static void zmakeSelfCheck() {
        java.util.logging.Logger logger = Logger.getInstance();

        logger.info("Looking for zmake...");
        if (!ZMake.isAvailable()) {
            logger.warning("zmake undetected [ERROR]");
            ZMake.downloadZMake();
        } else {
            logger.info("zmake detected [OK]");
        }
    }

    public static void initProject(String projectPath) { // Project path must be empty!
        ZMake session = openSession(new String[] {projectPath} );
        int resultCode;

        try {
            session.writer.write("a\r\n".getBytes());
            session.writer.flush();

            synchronized (session.process) {
                resultCode = session.process.waitFor();;
            }

            new File(projectPath + "/app-side").mkdir();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (resultCode != 0) throw new RuntimeException("ZMake returns non zero non-zero code: " + resultCode);

    }

    public static void compileProject(String projectPath) {
        if (!FSHelper.isDirectoryProject(projectPath)) {
            Logger.getInstance().warning("Directory is not project: " + projectPath);
            return;
        }

        ZMake session = openSession( new String[] {projectPath} );

        int resultCode;

        try {
            synchronized (session.process) {
                resultCode = session.process.waitFor();;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (resultCode != 0) throw new RuntimeException("ZMake returns non zero non-zero code: " + resultCode);

    }

    private static ZMake openSession(String[] processArgs) {
        return new ZMake(processArgs);
    }
}
