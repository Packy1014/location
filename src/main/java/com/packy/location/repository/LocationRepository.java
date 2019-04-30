package com.packy.location.repository;

import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;

import java.util.List;

public interface LocationRepository {
    LocationVO getLocationById(String id);

    List<LocationVO> getLocationNearBy(LocationDTO locationDTO);
}
