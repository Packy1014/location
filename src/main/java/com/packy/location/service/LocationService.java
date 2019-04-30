package com.packy.location.service;

import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;

import java.util.List;

public interface LocationService {
    LocationVO getLocationById(String id);

    List<LocationVO> getLocationNearBy(LocationDTO locationDTO);
}
