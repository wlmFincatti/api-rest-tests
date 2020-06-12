package br.com.apirest.users.entrypoint.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserErrorDetailsDto {

    private String error;
    private String path;
    private Date timestamp;

}
