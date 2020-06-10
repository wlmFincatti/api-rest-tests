package br.com.apirest.users.entrypoint.dto;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserErrorDetails {

    private String error;
    private String path;
    private Date timestamp;

}
