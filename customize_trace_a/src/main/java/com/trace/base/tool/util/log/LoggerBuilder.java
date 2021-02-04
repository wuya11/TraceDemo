package com.trace.base.tool.util.log;

import com.trace.base.tool.util.StringConst;
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
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 日志工具类
 *
 * @author ty
 */
public final class LoggerBuilder {
    private static final ConcurrentHashMap<String, Logger> CACHE = new ConcurrentHashMap<>();
    private static final boolean PROJECT_NAME_PRESENT;
    private static boolean LOG4J2_PRESENT;

    static {
        PROJECT_NAME_PRESENT = !StringUtils.isEmpty(StringConst.PROJECT_NAME);
        try {
            Class.forName("org.apache.logging.log4j.core.LoggerContext");
            LOG4J2_PRESENT = true;
        } catch (ClassNotFoundException e) {
            LOG4J2_PRESENT = false;
        }
    }

    private final String rootPath;
    private final String loggerName;
    private String pattern = "%m%n";

    /**
     * 创建必须属性
     *
     * @param rootPath   根目录,目前只支持/xxx/xxx/和/xxx/xx,windows环境会自动替换为C:\\xxx\xxx
     * @param loggerName 日志名称,全局唯一
     */
    public LoggerBuilder(String rootPath, String loggerName) {
        this.rootPath = resolveRootPath(rootPath);
        this.loggerName = loggerName;
    }

    public LoggerBuilder pattern(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalArgumentException("参数不能为空或者空字符串");
        }
        this.pattern = pattern;
        return this;
    }

    public Logger build() {
        return CACHE.computeIfAbsent(loggerName, s -> createLogger());
    }

    /**
     * 根据日志名称获取日志对象
     *
     * @param loggerName 日志名称
     * @return 日志对象, 返回结果可能为空
     */
    public static Logger get(String loggerName) {
        return CACHE.get(loggerName);
    }

    /**
     * 获取已存在的日志对象,如果没有使用构造器生成
     *
     * @param loggerName      日志名称
     * @param builderFunction 构造器
     * @return 日志对象
     */
    public static Logger getIfAbsent(String loggerName, Function<String, LoggerBuilder> builderFunction) {
        Logger logger = CACHE.get(loggerName);
        if (logger != null) {
            return logger;
        }
        LoggerBuilder loggerBuilder = builderFunction.apply(loggerName);
        if (loggerBuilder == null || !Objects.equals(loggerName, loggerBuilder.loggerName)) {
            throw new IllegalStateException("loggerName不一致");
        }
        return loggerBuilder.build();
    }

    private Logger createLogger() {
        // 必须要有项目名称和log4j2
        if (PROJECT_NAME_PRESENT && LOG4J2_PRESENT) {
            // 一定要用false,否则使用SLF4J获取的LoggerContext与全局的LoggerContext不一致
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration configuration = context.getConfiguration();
            final Layout<String> layout = PatternLayout.newBuilder()
                    .withPattern(pattern)
                    .withConfiguration(configuration)
                    .withCharset(StandardCharsets.UTF_8)
                    .withAlwaysWriteExceptions(false)
                    .withNoConsoleNoAnsi(true)
                    .build();
            // 暂时不开放自定义日志翻篇策略,统一一天一个
            final TriggeringPolicy policy = CronTriggeringPolicy.createPolicy(configuration, null, "0 0 0 * * ?");
            // directWrite策略
            RolloverStrategy directWriteRolloverStrategy = DirectWriteRolloverStrategy.newBuilder()
                    .withMaxFiles("10")
                    .withConfig(configuration)
                    .build();
            // 暂时不开放自定义appender名称
            String appenderName = loggerName + "File";
            final Appender appender = RollingRandomAccessFileAppender.newBuilder()
                    // TODO resolve不规范的路径字符串
                    .withFilePattern(rootPath + StringConst.PROJECT_NAME + "/" + "%d{yyyy-MM-dd}.log")
                    .withAppend(true)
                    .withName(appenderName)
                    .setConfiguration(configuration)
                    .withImmediateFlush(true)
                    .withPolicy(policy)
                    .withStrategy(directWriteRolloverStrategy)
                    .withLayout(layout)
                    .build();
            appender.start();
            // 新增日志appender
            configuration.addAppender(appender);
            AppenderRef appenderRef = AppenderRef.createAppenderRef(appenderName, Level.INFO, null);
            LoggerConfig loggerConfig = AsyncLoggerConfig.createLogger("false", "info", loggerName, "false", new AppenderRef[]{appenderRef}, null, configuration, null);
            // appenderRef添加还不够，必须手动添加appender
            loggerConfig.addAppender(appender, Level.INFO, null);
            loggerConfig.start();
            configuration.addLogger(loggerName, loggerConfig);
            context.updateLoggers();
        }
        // 否则直接使用默认std输出
        return LoggerFactory.getLogger(loggerName);
    }

    private String resolveRootPath(String rootPath) {
        final String slash = "/";
        if (StringUtils.isEmpty(rootPath)) {
            throw new IllegalArgumentException("根目录不能为空或者空字符串");
        }
        if (!rootPath.endsWith(slash)) {
            rootPath = rootPath + slash;
        }
        return rootPath;
    }
}