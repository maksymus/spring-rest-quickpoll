package org.homenet.rest.quickpoll.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class VoteResult {
    public int totalCount;
    public List<OptionCount> optionCounts = new ArrayList<>();
}
