package com.example.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Query implements Serializable {
    private Integer pageIndex;
    private Integer pageSize;
    private String search;
    private String userId;
}
