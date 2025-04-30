package com.seikan.store.dtos.put;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String name;
    public String email;
}
