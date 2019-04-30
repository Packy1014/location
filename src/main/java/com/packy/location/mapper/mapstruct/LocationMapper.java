package com.packy.location.mapper.mapstruct;

import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface LocationMapper {
    @Mappings({
            @Mapping(source = "locationVO.id", target = "id"),
            @Mapping(source = "locationVO.province", target = "province"),
            @Mapping(source = "locationVO.city", target = "city"),
            @Mapping(source = "locationVO.district", target = "district"),
            @Mapping(source = "locationVO.lng", target = "lng"),
            @Mapping(source = "locationVO.lat", target = "lat"),
            @Mapping(source = "pageNumber", target = "pageNumber"),
            @Mapping(source = "pageSize", target = "pageSize"),
            @Mapping(source = "radius", target = "radius"),
    })
    LocationDTO from(final LocationVO locationVO, final Integer pageNumber, final Integer pageSize, final Integer radius);
}