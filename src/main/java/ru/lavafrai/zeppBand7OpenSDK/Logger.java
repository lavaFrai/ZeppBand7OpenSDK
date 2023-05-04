package ru.lavafrai.zeppBand7OpenSDK;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.util.logging.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

public class Logger {
    static java.util.logging.Logger logger;

    public static java.util.logging.Logger getInstance() {
        if (logger == null) {
            logger = java.util.logging.Logger.getLogger("ZB7O_SDK");
            logger.setUseParentHandlers(false);

            ConsoleHandler handler = new ConsoleHandler();
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return record.getLevel() + " [" + getUptime() + "s]\t" + record.getMessage() + "\r\n";
                }
            });
            logger.addHandler(handler);
        }
        return logger;
    }

    static String getUptime() {
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        double uptime = mxBean.getUptime() / 1000.;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(uptime);
    }
}
