package com.fdoochann.employservice.repository;

import com.fdoochann.employservice.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anna_Bildyug
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
