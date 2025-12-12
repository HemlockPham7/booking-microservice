package com.convit.employeeservice.query.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllEmployeeQuery {
    private Boolean isDisciplined;
}