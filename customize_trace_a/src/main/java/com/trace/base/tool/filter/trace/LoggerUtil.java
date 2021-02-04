package com.trace.base.tool.filter.trace;

import com.trace.base.tool.util.StringConst;
import com.trace.base.tool.util.log.LoggerBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingRandomAccessFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CronTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DirectWriteRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

/**
 * 日志工具类
 *
 * @author ty
 */
class LoggerUtil {
    /**
     * 日志根目录
     */
    private static final boolean SHOULD_INIT;
    private static final String DEFAULT_TRACE_LOG_ROOT = "/wwwlogs/trace/";
    private static final String TRACE_LOG_ROOT;
    private static final String SPAN_LOG_ROOT = "/wwwlogs/span/";
    private static final String TRACE_LOG_ROOT_PROP = "trace.logRoot";
    static final String TRACE_PROJECT_NAME_PROP = "trace.project";
    static final String TRACE_LOGGER_NAME = "traceLogger";
    static final String SPAN_LOGGER_NAME = "spanLogger";
    private static Logger TRACE_LOGGER;
    private static Logger SPAN_LOGGER;
    static String PROJECT_NAME;

    static {
        PROJECT_NAME = StringConst.PROJECT_NAME;
        TRACE_LOG_ROOT = DEFAULT_TRACE_LOG_ROOT + PROJECT_NAME;
        SHOULD_INIT = true;
        createTraceLogger();
        createSpanLogger();
    }

    /**
     * 获取trace日志
     */
    static final Logger getTraceLogger() {
        return TRACE_LOGGER;
    }

    private static void createTraceLogger() {
        if (SHOULD_INIT) {
            // 一定要用false,否则使用SLF4J获取的LoggerContext与全局的LoggerContext不一致
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration configuration = context.getConfiguration();
            final Layout layout = PatternLayout.newBuilder()
                    .withPattern("%m%n")
                    .withConfiguration(configuration)
                    .withCharset(StandardCharsets.UTF_8)
                    .withAlwaysWriteExceptions(false)
                    .withNoConsoleNoAnsi(true)
                    .build();
            final TriggeringPolicy policy = CronTriggeringPolicy.createPolicy(configuration, null, "0 0 0/6 * * ?");
            // directWrite策略
            RolloverStrategy directWriteRolloverStrategy = DirectWriteRolloverStrategy.newBuilder()
                    .withMaxFiles("10")
                    .withConfig(configuration)
                    .build();
            final Appender appender = RollingRandomAccessFileAppender.newBuilder()
                    .withFilePattern(TRACE_LOG_ROOT + "/request_" + PROJECT_NAME + "-%d{yyyy-MM-dd-HH}.log")
                    .withAppend(true)
                    .withName("traceFile")
                    .setConfiguration(configuration)
                    .withImmediateFlush(true)
                    .withPolicy(policy)
                    .withStrategy(directWriteRolloverStrategy)
                    .withLayout(layout)
                    .build();
            appender.start();
            // 新增json日志appender
            configuration.addAppender(appender);
            AppenderRef appenderRef = AppenderRef.createAppenderRef("traceFile", Level.INFO, null);
            LoggerConfig loggerConfig = AsyncLoggerConfig.createLogger("false", "info", TRACE_LOGGER_NAME, "false", new AppenderRef[]{appenderRef}, null, configuration, null);
            // appenderRef添加还不够，必须手动添加appender
            loggerConfig.addAppender(appender, Level.INFO, null);
            loggerConfig.start();
            configuration.addLogger(TRACE_LOGGER_NAME, loggerConfig);
            context.updateLoggers();
        }
        // 如果没有配置会使用默认日志
        TRACE_LOGGER = LoggerFactory.getLogger(TRACE_LOGGER_NAME);
    }

    private static void createSpanLogger() {
        SPAN_LOGGER = LoggerBuilder.getIfAbsent(SPAN_LOGGER_NAME, loggerName -> new LoggerBuilder(SPAN_LOG_ROOT, loggerName));
    }

    /**
     * 获取span日志
     */
    static final Logger getSpanLogger() {
        return SPAN_LOGGER;
    }

    /**
     * 生成traceId
     */
    static String traceId() {
        return randomUuid();
    }

    /**
     * 生成requestId
     */
    static String requestId() {
        return randomUuid();
    }

    /**
     * 生成spanId
     */
    static String spanId() {
        return randomUuid();
    }

    static String randomUuid() {
        return UUID.randomUUID().toString() + new Random().nextInt(1000000);
    }
}
