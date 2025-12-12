package com.convit.employeeservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseModel {
    private String id;
    private String firstName;
    private String lastName;
    private String Kin;
    private Boolean isDisciplined;
}
