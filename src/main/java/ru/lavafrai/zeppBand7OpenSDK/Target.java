package ru.lavafrai.zeppBand7OpenSDK;

public enum Target {
    BUILD, // Only compile .bin
    BUILD_AND_RUN, // Compile .bin and run in emulator
    DECOMPILE, // Unpack .bin
    INIT, // Initialize new project
    HELP // Show help only
}
