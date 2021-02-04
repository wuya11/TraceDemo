package com.trace.base.tool.filter.trace.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * span信息
 *
 * @author ty
 */
public class Span {
    /**
     * 当前span编号
     */
    private String spanId;
    /**
     * 父span编号
     */
    private String parentId;
    /**
     * 时长(单位:微秒)
     */
    private long duration;
    /**
     * 额外信息列表
     */
    private List<Annotation> annotations;

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "Span{" +
                "spanId='" + spanId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", duration=" + duration +
                ", annotations=" + annotations +
                '}';
    }

    public static class Builder {
        /**
         * 当前span编号
         */
        private String spanId;
        /**
         * 父span编号
         */
        private String parentId;
        /**
         * 时长(单位:微秒)
         */
        private long duration;
        /**
         * 额外信息列表
         */
        private List<Annotation> annotations = new ArrayList<>(2);

        public Span build() {
            Span span = new Span();
            span.setSpanId(spanId);
            span.setParentId(parentId);
            span.setDuration(duration);
            span.setAnnotations(annotations);
            return span;
        }

        public Builder spanId(String spanId) {
            this.spanId = spanId;
            return this;
        }

        public Builder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder annotations(List<Annotation> annotations) {
            this.annotations = annotations;
            return this;
        }

        public Builder annotation(Annotation annotation) {
            this.annotations.add(annotation);
            return this;
        }
    }
}
