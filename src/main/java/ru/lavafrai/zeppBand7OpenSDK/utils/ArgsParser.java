package ru.lavafrai.zeppBand7OpenSDK.utils;

import ru.lavafrai.zeppBand7OpenSDK.Target;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.File;

public class ArgsParser {
    public static Target getTarget(String[] args) {

        if (args.length < 1) {
            Logger.getInstance().info("TARGET NOT DETECTED");
            return Target.HELP;
        }

        switch (args[0]) {
            case "i":
                Logger.getInstance().info("DETECTED TARGET INIT");
                return Target.INIT;
            case "b":
            case "build":
                Logger.getInstance().info("DETECTED TARGET BUILD");
                return Target.BUILD;
            case "r":
            case "run":
                Logger.getInstance().info("DETECTED TARGET BUILD_AND_RUN");
                return Target.BUILD_AND_RUN;
            case "s":
            case "stop":
                Logger.getInstance().info("DETECTED TARGET STOP");
                return Target.STOP;
            case "d":
                Logger.getInstance().info("DETECTED TARGET DECOMPILE");
                return Target.DECOMPILE;
            case "h":
            case "help":
            case "--help":
                Logger.getInstance().info("DETECTED TARGET HELP");
                return Target.HELP;
        }

        Logger.getInstance().info("TARGET NOT DETECTED");
        return Target.HELP;
    }

    public static String getDirPath(String[] args) {
        String cwd;

        if ( new File(args[args.length - 1]).isDirectory() ) {
            Logger.getInstance().info("Detected work dir: " + args[args.length - 1]);
            cwd = args[args.length - 1];
        } else {
            cwd = System.getProperty("user.dir");
            Logger.getInstance().info("Work dir unspecified: using system cwd (" + cwd + ")");
        }

        return cwd;
    }
}
