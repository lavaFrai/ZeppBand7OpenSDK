package ru.lavafrai.zeppBand7OpenSDK;

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
        } else {
            logger.warning("zmake detected [OK]");
        }
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
