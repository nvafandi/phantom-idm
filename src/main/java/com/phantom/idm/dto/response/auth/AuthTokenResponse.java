package com.phantom.idm.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.phantom.idm.constant.Constant;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder
@Builder
public class AuthTokenResponse {

    private String token;

    private String refreshToken;

    @JsonFormat(pattern = Constant.FORMAT_DATE_YYYYMMDD_4, timezone = Constant.FORMAT_TIMEZONE)
    private Date exp;

}
