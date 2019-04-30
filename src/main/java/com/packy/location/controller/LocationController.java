package com.packy.location.controller;

import com.packy.location.annotation.OperationLogAnnotation;
import com.packy.location.mapper.mapstruct.LocationMapper;
import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;
import com.packy.location.service.LocationService;
import com.packy.location.validator.ParaValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.packy.location.model.inner.OperationLogInner.MODULE.LOCATION;
import static com.packy.location.model.inner.OperationLogInner.OPERATION_TYPE.READ;
import static com.packy.location.utility.constant.Labels.Common.*;
import static com.packy.location.utility.constant.Labels.Location.*;
import static com.packy.location.validator.ParameterValidator.*;

@RestController
@RequestMapping(path = VERSION, produces = "application/json")
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationController(LocationService locationService, LocationMapper locationMapper) {
        this.locationService = locationService;
        this.locationMapper = locationMapper;
    }

    @GetMapping("/{id}")
    @OperationLogAnnotation(module = LOCATION, operationType = READ)
    public LocationVO getLocationById(@PathVariable("id") final String id) {
        validateId(id);
        return locationService.getLocationById(id);
    }

    @GetMapping("/nearby/{id}")
    @OperationLogAnnotation(module = LOCATION, operationType = READ)
    public List<LocationVO> getLocationNearbyById(@PathVariable final String id,
                                                  @RequestParam final Integer pageNumber,
                                                  @RequestParam final Integer pageSize,
                                                  @RequestParam final Integer radius) {
        validateParaForGetLocationNearbyById(id, pageNumber, pageSize, radius);
        return locationService.getLocationNearBy(locationMapper.from(getLocationById(id), pageNumber, pageSize, radius));
    }

    @GetMapping("/nearby")
    @OperationLogAnnotation(module = LOCATION, operationType = READ)
    public List<LocationVO> getLocationNearbyByLngAndLat(@RequestParam final BigDecimal lat,
                                                         @RequestParam final BigDecimal lng,
                                                         @RequestParam final Integer pageNumber,
                                                         @RequestParam final Integer pageSize,
                                                         @RequestParam final Integer radius) {
        validateParaForGetLocationNearbyByLngAndLat(lat, lng, pageNumber, pageSize, radius);
        return locationService.getLocationNearBy(LocationDTO.builder().lat(lat).lng(lng).pageNumber(pageNumber).pageSize(pageSize).radius(radius).build());
    }

    private void validateId(String id) {
        ParaValidationUtil.getInstance()
                .addMandatoryParamValidator(ID, id, STRING_VALUE_VALIDATION)
                .validate();
    }

    private void validateParaForGetLocationNearbyById(final String id,
                                                      final Integer pageNumber,
                                                      final Integer pageSize,
                                                      final Integer radius) {
        ParaValidationUtil.getInstance()
                .addMandatoryParamValidator(ID, id, STRING_VALUE_VALIDATION)
                .addMandatoryParamValidator(PAGE_NUMBER, pageNumber, POSITIVE_INTEGER_VALIDATION)
                .addMandatoryParamValidator(PAGE_SIZE, pageSize, POSITIVE_INTEGER_VALIDATION)
                .addMandatoryParamValidator(RADIUS, radius, POSITIVE_INTEGER_VALIDATION)
                .validate();
    }

    private void validateParaForGetLocationNearbyByLngAndLat(final BigDecimal lat,
                                                             final BigDecimal lng,
                                                             final Integer pageNumber,
                                                             final Integer pageSize,
                                                             final Integer radius) {
        ParaValidationUtil.getInstance()
                .addMandatoryParamValidator(LATITUDE, lat, LATITUDE_VALIDATION)
                .addMandatoryParamValidator(LONGITUDE, lng, LONGITUDE_VALIDATION)
                .addMandatoryParamValidator(PAGE_NUMBER, pageNumber, POSITIVE_INTEGER_VALIDATION)
                .addMandatoryParamValidator(PAGE_SIZE, pageSize, POSITIVE_INTEGER_VALIDATION)
                .addMandatoryParamValidator(RADIUS, radius, POSITIVE_INTEGER_VALIDATION)
                .validate();
    }
}
