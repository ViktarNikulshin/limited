package com.beta.limited.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
public class RoleDto {
    private Integer id;
    private String name;
}
