package ru.lavafrai.zeppBand7OpenSDK;

public enum Target {
    BUILD, // Only compile .bin
    BUILD_AND_RUN, // Compile .bin and run in emulator
    ZPK, // Build zpk from project
    STOP, // Stop already running session of emulation
    DECOMPILE, // Unpack .bin
    INIT, // Initialize new project
    HELP // Show help only
}
