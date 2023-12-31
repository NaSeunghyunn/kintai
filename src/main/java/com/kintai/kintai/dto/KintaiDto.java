package com.kintai.kintai.dto;

import com.kintai.kintai.domain.KintaiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class KintaiDto {
    private Long id;
    private KintaiStatus status;
    private String memberName;
    private YearMonth workYearMonth;
    List<KintaiDetailDto> details = new ArrayList<>();
}
