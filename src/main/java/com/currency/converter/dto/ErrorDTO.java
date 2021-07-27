package com.currency.converter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDTO {

    private Integer status;

    private String type;

    private String title;

    private String detail;

    private List<FieldErrorDTO> fields = new ArrayList<>();
}
