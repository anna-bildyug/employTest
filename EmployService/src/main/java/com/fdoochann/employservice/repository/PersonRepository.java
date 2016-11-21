package com.fdoochann.employservice.repository;

import com.fdoochann.employservice.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anna_Bildyug
 */
public interface PersonRepository extends CrudRepository<Person, Long> {

}
