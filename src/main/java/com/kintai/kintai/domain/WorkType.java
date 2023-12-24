package com.kintai.kintai.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkType {
    WORK("出勤"), VACATION("休み");

    private final String ja;
}
