package com.fdoochann.employservice.validation;

import com.fdoochann.employservice.exceptions.BadRequestException;
import com.fdoochann.employservice.model.Employee;
import com.fdoochann.employservice.repository.CompanyRepository;
import com.fdoochann.employservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Администратор on 22.11.2016.
 */
@Component
public class EmployeeValidator {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PersonRepository personRepository;

    public void validate(Employee employee)
    {
        if (!companyRepository.exists(employee.getCompany().getId()))
        {
            throw  new BadRequestException("Incorrect companyId value. There is no company with id");
        }
        if (!personRepository.exists(employee.getPerson().getId()))
        {
            throw  new BadRequestException("Incorrect personId value. There is no person with id");
        }
    }
}
