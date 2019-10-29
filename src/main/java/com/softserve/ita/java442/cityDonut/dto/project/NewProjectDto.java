package com.softserve.ita.java442.cityDonut.dto.project;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.category.CategoryNameDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDto {

    private long id;

    @NotNull(message = ErrorMessage.NULL_NAME_ERROR)
    @Size(
            min = 4,
            max = 50,
            message = ErrorMessage.PROJECT_NAME_SIZE_ERROR
    )
    private String name;

    @Size(max = 1000, message = ErrorMessage.PROJECT_DESCRIPTION_SIZE_ERROR)
    private String description;

    @Size(max = 255, message = ErrorMessage.PROJECT_LOCATION_SIZE_ERROR)
    private String location;

    @Digits(integer = 9, fraction = 6, message = ErrorMessage.PROJECT_LOCATION_PARAM_FORMAT_ERROR)
    @DecimalMin(value = "-180.0", message = ErrorMessage.PROJECT_LOCATION_PARAM_RANGE_ERROR)
    @DecimalMax(value = "180.0", message = ErrorMessage.PROJECT_LOCATION_PARAM_RANGE_ERROR)
    private double locationLatitude;

    @Digits(integer = 9, fraction = 6, message = ErrorMessage.PROJECT_LOCATION_PARAM_FORMAT_ERROR)
    @DecimalMin(value = "-180.0", message = ErrorMessage.PROJECT_LOCATION_PARAM_RANGE_ERROR)
    @DecimalMax(value = "180.0", message = ErrorMessage.PROJECT_LOCATION_PARAM_RANGE_ERROR)
    private double locationLongitude;

    private List<CategoryNameDto> categories;

}
