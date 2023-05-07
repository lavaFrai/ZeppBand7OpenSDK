package ru.lavafrai.zeppBand7OpenSDK;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import ru.lavafrai.zeppBand7OpenSDK.utils.AppJsonParser;
import ru.lavafrai.zeppBand7OpenSDK.utils.ArgsParser;
import ru.lavafrai.zeppBand7OpenSDK.utils.FSHelper;
import ru.lavafrai.zeppBand7OpenSDK.utils.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

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
                ZeppPlayer.stopIfRunning();
                buildProject(ArgsParser.getDirPath(args));
                runProject(ArgsParser.getDirPath(args));
                break;
            case STOP:
                ZeppPlayer.stopIfRunning();
                break;
            case ZPK:
                buildProject(ArgsParser.getDirPath(args));
                genZPK(ArgsParser.getDirPath(args));
                break;
            case DECOMPILE:
                decompileProject("");
        }
    }

    private void decompileProject(String projectPath) {
        logger.warning("Not ready yet [ERROR]");
    }

    private void runProject(String projectPath) {
        ZeppPlayer.zeppPlayerSelfCheck();
        ZeppPlayer.runProject(projectPath);

    }

    private void buildProject(String projectPath) {
        ZMake.zmakeSelfCheck();

        ZMake.compileProject(projectPath);
        logger.info("Project built in " + projectPath);
        logger.info(".bin saved: " + projectPath + "\\dist\\" + Paths.get(projectPath).getFileName() + ".bin" );
    }

    private void genZPK(String projectPath) {
        AppJsonParser app = new AppJsonParser(projectPath);
        app.saveAppSideJson(projectPath);


        FSHelper.copyDirectory(new File(projectPath + "/app-side"), new File(projectPath + "/zpk/app-side"));
        FSHelper.copyDirectory(new File(projectPath + "/build"), new File(projectPath + "/zpk/device"));

        try {
            new File(projectPath + "/zpk/app-side.zip").deleteOnExit();
            new File(projectPath + "/zpk/device.zip").deleteOnExit();

            dirToZip(new File(projectPath + "/zpk/app-side"), projectPath + "/zpk/app-side.zip");
            dirToZip(new File(projectPath + "/zpk/device"), projectPath + "/zpk/device.zip");

            ZipFile zipFile = new ZipFile(projectPath + "/dist/" + Paths.get(projectPath).getFileName() + ".zpk");
            zipFile.addFile(projectPath + "/zpk/app-side.zip");
            zipFile.addFile(projectPath + "/zpk/device.zip");
            zipFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dirToZip(File dir, String _zip) throws IOException {
        ZipFile zip = new ZipFile(_zip);
        Arrays.asList(dir.listFiles()).forEach((e) -> {
            try {
                if (e.isDirectory()) {
                    zip.addFolder(e);
                } else {
                    zip.addFile(e);
                }
            } catch (ZipException ex) {
                throw new RuntimeException(ex);
            }
        });
        zip.close();
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
        System.out.println("\tr [directory?] - RUN build your project and run built application in emulator.");
        System.out.println("\ts - STOP Stopping already started emulation.");
        System.out.println("\td [.bin file] - (Not realized yet) DECOMPILE unpack .bin of application into current directory.");
    }
}
