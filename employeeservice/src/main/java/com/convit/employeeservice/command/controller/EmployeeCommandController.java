package com.convit.employeeservice.command.controller;

import com.convit.employeeservice.command.command.CreateEmployeeCommand;
import com.convit.employeeservice.command.command.DeleteEmployeeCommand;
import com.convit.employeeservice.command.command.UpdateEmployeeCommand;
import com.convit.employeeservice.command.model.CreateEmployeeModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model){
        CreateEmployeeCommand command = CreateEmployeeCommand.builder()
                .id(UUID.randomUUID().toString())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .Kin(model.getKin())
                .isDisciplined(Boolean.FALSE)
                .build();
        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@Valid @RequestBody UpdateEmployeeCommand model, @PathVariable String employeeId) {
        UpdateEmployeeCommand command = UpdateEmployeeCommand.builder()
                .id(employeeId)
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .Kin(model.getKin())
                .isDisciplined(model.getIsDisciplined())
                .build();
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId){
        DeleteEmployeeCommand command = DeleteEmployeeCommand.builder()
                .id(employeeId)
                .build();
        return commandGateway.sendAndWait(command);
    }
}
