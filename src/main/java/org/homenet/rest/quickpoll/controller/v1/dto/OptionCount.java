package org.homenet.rest.quickpoll.controller.v1.dto;

public class OptionCount {
    private long optionId;
    private long count;

    public OptionCount(long optionId, long count) {
        this.optionId = optionId;
        this.count = count;
    }
}
