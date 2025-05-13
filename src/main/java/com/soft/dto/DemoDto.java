package com.soft.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DemoDto {
    /**
     * 全部私有，list特殊处理
     */
    private int id;
    private String name;
    private BigDecimal netValue;
    private Date createdTime;
    List<String> orderByList;
}
