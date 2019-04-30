package com.packy.location.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {
    private String id;
    private String province;
    private String city;
    private String district;
    private BigDecimal lng;
    private BigDecimal lat;
    private BigDecimal distance;
    private Integer radius;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer pageOffset;

    private Integer getPageOffset() {
        return (pageNumber - 1) * pageSize;
    }
}