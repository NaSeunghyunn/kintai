package com.kintai.kintai.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class TimeUtils {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");
    private static final String EMPTY_STRING = "";

    public double toDecimalTime(LocalTime localTime) {
        if(localTime == null) return 0;
        return localTime.getHour() + (double) localTime.getMinute() / 60;
    }

    public String toFormatTime(double doubleTime) {
        int hour = (int) doubleTime;
        int minute = (int) ((doubleTime - hour) * 60);
        return String.format("%02d:%02d", hour, minute);
    }

    public String formatTime(LocalTime time) {
        if (time == null) return EMPTY_STRING;
        return time.format(TIME_FORMATTER);
    }

    public LocalTime durationTime(LocalTime start, LocalTime end) {
        if (start == null || end == null) return null;

        Duration duration = Duration.between(start, end);
        long seconds = duration.getSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;

        return LocalTime.of((int) hours, (int) minutes);
    }
}
