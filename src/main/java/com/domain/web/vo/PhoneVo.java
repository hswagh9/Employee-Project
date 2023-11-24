package com.domain.web.vo;
import com.domain.enums.PhoneType;

import lombok.Data;

@Data
public class PhoneVo {
    private Long id;
    private PhoneType type;
    private String value;
}
