package com.trace.base.tool.configuration.executor.second;

import com.trace.base.tool.domain.base.User;
import com.trace.base.tool.util.ThreadHolderUtil;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * 线程池封装传递参数
 *
 * @author wl
 * @date 2021-2-4
 */
public class TaskAdapterDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<Object, Object> objectMap= ThreadHolderUtil.getValueMap();
        User user= ThreadHolderUtil.getUser();
        // 对Runnable进行装饰
        return new Runnable() {
            @Override
            public void run() {
                try {
                    ThreadHolderUtil.setValueMap(objectMap);
                    ThreadHolderUtil.setUser(user);
                    runnable.run();
                } finally {
                    ThreadHolderUtil.clearValueMap();
                    ThreadHolderUtil.clearUser();
                }
            }
        };
    }
}