package ru.lavafrai.zeppBand7OpenSDK.utils;


import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class AppJsonParser {
    JSONObject object;

    public AppJsonParser(String projectPath) {
        try {
            object = (JSONObject) new JSONParser().parse(new FileReader(projectPath + "/app.json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAppSideJson(String projectPath) {
        try {
            if (new File(projectPath + "/zpk").isDirectory()) FSHelper.clearDirectory(new File(projectPath + "/zpk"));

            JSONObject packageInfo = new JSONObject();
            packageInfo.put("mode", "preview");
            packageInfo.put("timeStamp", 1682235016);
            packageInfo.put("expiredTime", 157680000);
            packageInfo.put("zpm", "2.6.6");
            object.put("packageInfo", packageInfo);

            object.put("platforms", new JSONParser().parse("[{\"name\": \"amazfit-band7\",\"deviceSource\": 253},{\"name\": \"amazfit-band7-w\",\"deviceSource\": 254}]"));

            new File(projectPath + "/zpk/app-side").mkdirs();
            FileWriter file = new FileWriter(projectPath + "/zpk/app-side/app.json");
            object.writeJSONString(file);
            file.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
