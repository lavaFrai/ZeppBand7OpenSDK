package ru.lavafrai.zeppBand7OpenSDK;

import ru.lavafrai.zeppBand7OpenSDK.utils.ArgsParser;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.IOException;
import java.nio.file.Paths;

public class ZeppBand7OpenSDK {
    Target currentTarget;
    java.util.logging.Logger logger = Logger.getInstance();

    public ZeppBand7OpenSDK(String[] args) {

        Logger.getInstance().info("Starting ZeppBand7OpenSDK by. lava_frai");
        currentTarget = ArgsParser.getTarget(args);

        if (currentTarget == Target.HELP) printHelp();
        else switch (currentTarget) {
            case INIT:
                initProject(ArgsParser.getDirPath(args));
                break;
            case BUILD:
                buildProject(ArgsParser.getDirPath(args));
                break;
            case BUILD_AND_RUN:
                buildProject("");
                runProject("");
                break;
            case DECOMPILE:
                decompileProject("");
        }
    }

    private void decompileProject(String projectPath) {
        logger.warning("Not ready yet [ERROR]");
    }

    private void runProject(String projectPath) {
        logger.warning("Not ready yet [ERROR]");

    }

    private void buildProject(String projectPath) {
        ZMake.zmakeSelfCheck();

        ZMake.compileProject(projectPath);
        logger.info("Project built in " + projectPath);
        logger.info(".bin saved: " + projectPath + "\\dist\\" + Paths.get(projectPath).getFileName() + ".bin" );
    }

    private void initProject(String projectPath) {
        ZMake.zmakeSelfCheck();

        if (!FSHelper.isDirectoryEmpty(projectPath)) {
            logger.warning("Project directory must be empty! [ERROR]");
            return;
        }

        ZMake.initProject(projectPath);
        logger.info("Project initialized in " + projectPath);
    }

    void printHelp() {
        System.out.println("\n\nShowing help for ZeppBand7OpenSDK:");
        System.out.println("zb7o-sdk [target]\n");
        System.out.println("Targets:");
        System.out.println("\ti [directory?] - INIT generate new project template inside this directory.");
        System.out.println("\tb [directory?] - BUILD build your project. Result file will appear in dist directory.");
        System.out.println("\tr [directory?] - (Not realized yet) RUN build your project and run built application in emulator.");
        System.out.println("\td [.bin file] - (Not realized yet) DECOMPILE unpack .bin of application into current directory.");
    }
}
