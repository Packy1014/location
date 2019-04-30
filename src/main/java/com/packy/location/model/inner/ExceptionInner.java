package com.packy.location.model.inner;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.packy.location.utility.constant.Labels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonAutoDetect(
        fieldVisibility = ANY,
        getterVisibility = NONE,
        isGetterVisibility = NONE,
        setterVisibility = NONE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        Labels.Common.HTTP_STATUS,
        Labels.Common.ERRORS
})
public class ExceptionInner {
    @JsonProperty(required = true)
    private HttpStatus httpStatus;
    private List<String> errors;
}
