package ru.netology;

import java.io.File;

public class RegistrationDto  {
    public String name;
    public String password;
    public String status;
    public RegistrationDto(String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;

    }

}
