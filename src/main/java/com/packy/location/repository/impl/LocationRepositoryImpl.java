package com.packy.location.repository.impl;

import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;
import com.packy.location.mapper.mybatis.LocationMapper;
import com.packy.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Repository
public class LocationRepositoryImpl implements LocationRepository {
    private final LocationMapper locationMapper;

    @Override
    public LocationVO getLocationById(String id) {
        return locationMapper.getLocationById(id);
    }

    @Override
    public List<LocationVO> getLocationNearBy(LocationDTO locationDTO) {
        return locationMapper.getLocationNearBy(locationDTO);
    }
}
