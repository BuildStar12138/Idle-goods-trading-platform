package com.example.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UapdateFamilyInfor implements Serializable {
    private String familyId;
    private String familyName;
    private String address;
}
