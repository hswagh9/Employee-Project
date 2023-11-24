package com.domain.web.vo;

import com.domain.enums.DepartmentType;

import lombok.Data;

@Data
public class DepartmentVo {
    private Long id;
    private DepartmentType type;
    private String name;
}
