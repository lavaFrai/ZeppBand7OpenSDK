package ru.lavafrai.zeppBand7OpenSDK;

import net.lingala.zip4j.ZipFile;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.*;
import java.nio.file.Files;

public class ZMake {
    static boolean localZMake = false;
    private String[] processArgs;
    private final Process process;
    BufferedReader reader;
    OutputStream writer;

    ZMake(String[] processArgs) {
        this.processArgs = processArgs;

        ProcessBuilder processBuilder;
        if (localZMake) processBuilder = new ProcessBuilder(".cache/zmake", String.join(" ", processArgs));
        else processBuilder = new ProcessBuilder("zmake", String.join(" ", processArgs));

        processBuilder.redirectErrorStream(true);
        // processBuilder.inheritIO();

        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        writer = process.getOutputStream();

    }

    static boolean isAvailableFromCache() {
        ProcessBuilder pb = new ProcessBuilder(".cache/zmake.exe");
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
        if (!new File("zmake.zip").exists()) FSHelper.downloadFile("https://raw.githubusercontent.com/lavaFrai/ZeppBand7OpenSDK/master/static/zmake.zip", "zmake.zip");

        try (
                ZipFile zipFile = new ZipFile("zmake.zip")
        ) {
            zipFile.extractAll(".cache");
            Files.deleteIfExists(new File("zmake.zip").toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        localZMake = true;
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (resultCode != 0) throw new RuntimeException("ZMake returns non zero non-zero code");

    }

    private static ZMake openSession(String[] processArgs) {
        return new ZMake(processArgs);
    }
}
