package com.packy.location.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDAO {
    private String id;
    private String province;
    private String city;
    private String district;
    private BigDecimal lng;
    private BigDecimal lat;
}
