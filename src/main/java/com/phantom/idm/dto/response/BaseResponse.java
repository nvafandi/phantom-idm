package com.phantom.idm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    @NonNull
    private int status;

    @NonNull
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data = null;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Metadata metadata;

}
