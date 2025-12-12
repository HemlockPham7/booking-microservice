package com.convit.employeeservice.query.controller;

import com.convit.employeeservice.query.model.EmployeeResponseModel;
import com.convit.employeeservice.query.queries.GetAllEmployeeQuery;
import com.convit.employeeservice.query.queries.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<EmployeeResponseModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined) {
        log.info("Calling to getAllEmployee");
        GetAllEmployeeQuery getAllEmployeeQuery = GetAllEmployeeQuery.builder()
                .isDisciplined(isDisciplined)
                .build();
        return queryGateway.query(getAllEmployeeQuery, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getDetailEmployee(@PathVariable String employeeId){
        GetDetailEmployeeQuery getDetailEmployeeQuery = GetDetailEmployeeQuery.builder()
                .id(employeeId)
                .build();
        return queryGateway.query(getDetailEmployeeQuery,ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
    }
}
