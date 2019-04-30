package com.packy.location.mapper.mybatis;

import com.packy.location.model.dto.LocationDTO;
import com.packy.location.model.vo.LocationVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface LocationMapper {
    @Select("SELECT id, province, city, district, lng, lat FROM location where id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = String.class),
            @Result(property = "province", column = "province", javaType = String.class),
            @Result(property = "city", column = "city", javaType = String.class),
            @Result(property = "district", column = "district", javaType = String.class),
            @Result(property = "lng", column = "lng", javaType = BigDecimal.class),
            @Result(property = "lat", column = "lat", javaType = BigDecimal.class)
    })
    LocationVO getLocationById(String id);

    @Select("  SELECT *\n" +
            "    FROM (\n" +
            "      SELECT id,\n" +
            "             province,\n" +
            "             city,\n" +
            "             district,\n" +
            "             lng,\n" +
            "             lat,\n" +
            "             st_distance_sphere ( coordinate, point ( #{lng}, #{lat} ) ) AS distance \n" +
            "        FROM location \n" +
            "      HAVING distance <= #{radius}) AS result \n" +
            "   WHERE result.lng <> #{lng} \n" +
            "      OR result.lat <> #{lat} \n" +
            "ORDER BY result.distance \n" +
            "   LIMIT #{pageOffset}, #{pageSize} ")
    @Results({
            @Result(property = "id", column = "id", javaType = String.class),
            @Result(property = "province", column = "province", javaType = String.class),
            @Result(property = "city", column = "city", javaType = String.class),
            @Result(property = "district", column = "district", javaType = String.class),
            @Result(property = "lng", column = "lng", javaType = BigDecimal.class),
            @Result(property = "lat", column = "lat", javaType = BigDecimal.class),
            @Result(property = "distance", column = "distance", javaType = BigDecimal.class)
    })
    List<LocationVO> getLocationNearBy(LocationDTO locationDTO);
}
