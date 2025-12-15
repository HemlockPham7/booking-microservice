package com.convit.employeeservice.query.projection;

import com.convit.commonservice.model.EmployeeResponseCommonModel;
import com.convit.commonservice.queries.GetDetailEmployeeQuery;
import com.convit.employeeservice.command.data.Employee;
import com.convit.employeeservice.command.data.EmployeeRepository;
import com.convit.employeeservice.query.queries.GetAllEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {

    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeResponseCommonModel> handle(GetAllEmployeeQuery query) {
        List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(query.getIsDisciplined());
        return listEmployee.stream().map(employee -> {
            EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
            BeanUtils.copyProperties(employee, model);

            return model;
        }).toList();
    }

    @QueryHandler
    public EmployeeResponseCommonModel handle(GetDetailEmployeeQuery query) throws Exception{
        Employee employee = employeeRepository.findById(query.getId()).orElseThrow(() -> new Exception("Employee not found"));
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        BeanUtils.copyProperties(employee,model);
        return model;
    }
}