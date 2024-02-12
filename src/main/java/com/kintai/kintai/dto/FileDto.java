package com.kintai.kintai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDto {
    private String fileName;
    private byte[] file;
}
