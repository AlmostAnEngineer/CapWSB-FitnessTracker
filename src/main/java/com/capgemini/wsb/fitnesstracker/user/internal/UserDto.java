package com.capgemini.wsb.fitnesstracker.user.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

record UserDto(@Nullable Long id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {}

record MailDto(@Nullable Long id, String email){};

record AgeDto(Integer age){};

record UserSimpleDto(@Nullable Long id, String firstName, String lastName){}

