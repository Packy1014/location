package com.packy.location.service.impl;

import com.packy.location.exception.ExceptionBuilder;
import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;
import com.packy.location.repository.LocationRepository;
import com.packy.location.service.LocationService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.packy.location.exception.FunctionalException.FunctionalMessage.LOCATION_ID_DOES_NOT_EXIT;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class LocationServerImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public LocationVO getLocationById(String id) {
        return Optional.ofNullable(Try.of(() -> locationRepository.getLocationById(id)).onFailure(exception -> {
            throw ExceptionBuilder.buildDatabaseTechnicalException(exception);
        }).get()).orElseThrow(() -> ExceptionBuilder.buildEntityNotFoundFunctionalException(LOCATION_ID_DOES_NOT_EXIT, id));
    }

    @Override
    public List<LocationVO> getLocationNearBy(LocationDTO locationDTO) {
        return Try.of(() -> locationRepository.getLocationNearBy(locationDTO)).onFailure(exception -> {
            throw ExceptionBuilder.buildDatabaseTechnicalException(exception);
        }).get();
    }
}
