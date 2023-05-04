package ru.lavafrai.zeppBand7OpenSDK;

import ru.lavafrai.zeppBand7OpenSDK.utils.ArgsParser;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

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
                buildProject("");
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
    }

    private void runProject(String projectPath) {

    }

    private void buildProject(String projectPath) {

    }

    private void initProject(String projectPath) {
        logger.info("Looking for zmake...");
        if (!ZMake.isAvailable()) {
            logger.warning("zmake undetected [ERROR]");
            return;
        } else {
            logger.info("zmake detected [OK]");
        }

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
        System.out.println("\ti [directory] - (Not realized yet) INIT generate new project template inside this directory.");
        System.out.println("\tb [directory?] - (Not realized yet) BUILD build your project. Result file will appear in dist directory.");
        System.out.println("\tr [directory?] - (Not realized yet) RUN build your project and run built application in emulator.");
        System.out.println("\td [.bin file] - (Not realized yet) DECOMPILE unpack .bin of application into current directory.");
    }
}
