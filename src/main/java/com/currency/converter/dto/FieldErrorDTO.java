package com.currency.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FieldErrorDTO {

    private String name;

    private String message;

}
