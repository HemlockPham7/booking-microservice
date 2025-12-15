package com.convit.employeeservice.query.controller;

import com.convit.commonservice.model.EmployeeResponseCommonModel;
import com.convit.commonservice.queries.GetDetailEmployeeQuery;
import com.convit.employeeservice.query.queries.GetAllEmployeeQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
@Tag(name = "Employee Query")
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse(
                            description = "Sucess",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized / Invalid Token"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseCommonModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined) {
        log.info("Calling to getAllEmployee");
        GetAllEmployeeQuery getAllEmployeeQuery = GetAllEmployeeQuery.builder()
                .isDisciplined(isDisciplined)
                .build();
        return queryGateway.query(getAllEmployeeQuery, ResponseTypes.multipleInstancesOf(EmployeeResponseCommonModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseCommonModel getDetailEmployee(@PathVariable String employeeId){
        GetDetailEmployeeQuery getDetailEmployeeQuery = GetDetailEmployeeQuery.builder()
                .id(employeeId)
                .build();
        return queryGateway.query(getDetailEmployeeQuery,ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
    }
}
