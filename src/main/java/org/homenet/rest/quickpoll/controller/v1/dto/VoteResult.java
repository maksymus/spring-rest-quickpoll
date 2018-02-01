package org.homenet.rest.quickpoll.controller.v1.dto;

import java.util.ArrayList;
import java.util.List;

public class VoteResult {
    public int totalCount;
    public List<OptionCount> optionCounts = new ArrayList<>();
}
