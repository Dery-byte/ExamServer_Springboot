package com.exam.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String fullName;
}
