package ru.lavafrai.zeppBand7OpenSDK;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collection;

public class ZMake {
    private String[] processArgs;
    private final Process process;
    BufferedReader reader;
    OutputStream writer;

    ZMake(String[] processArgs) {
        this.processArgs = processArgs;

        ProcessBuilder processBuilder = new ProcessBuilder("zmake", String.join(" ", processArgs));
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
        // ToDo: ZMake auto downloading
    }

    public static boolean initProject(String projectPath) { // Project path must be empty!
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

        return true;
    }

    private static ZMake openSession(String[] processArgs) {
        return new ZMake(processArgs);
    }
}
