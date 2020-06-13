package br.com.apirest.users.entrypoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserToEntity {
    @NotNull(message = "Name not to be null")
    @Size(min = 5, max = 200, message
            = "Name must be between 5 and 200 characters")
    private String name;
    @NotNull(message = "Age Not to be null")
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer age;
}
