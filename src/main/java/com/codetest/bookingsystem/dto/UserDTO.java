package com.codetest.bookingsystem.dto;

import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.enums.Status;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private String email;
    private String password;
    private String phoneNo;
    private String address;
    private Role role;
    private Status status;

}
