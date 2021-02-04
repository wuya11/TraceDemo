package com.trace.base.tool.filter.trace.domain;

/**
 * span额外信息
 *
 * @author ty
 */
public class Annotation {
    /**
     * 时间戳(单位:微秒)
     */
    private long timestamp;
    /**
     * 类型
     */
    private ActionEnum action;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "timestamp=" + timestamp +
                ", action=" + action +
                '}';
    }

    public static class Builder {
        /**
         * 时间戳(单位:微秒)
         */
        private long timestamp;
        /**
         * 类型
         */
        private ActionEnum action;

        public Annotation build() {
            Annotation annotation = new Annotation();
            annotation.setTimestamp(timestamp);
            annotation.setAction(action);
            return annotation;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder action(ActionEnum action) {
            this.action = action;
            return this;
        }
    }
}
