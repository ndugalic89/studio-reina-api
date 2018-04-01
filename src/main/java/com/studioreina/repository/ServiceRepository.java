package com.studioreina.repository;

import org.springframework.data.repository.CrudRepository;

import com.studioreina.model.Service;
import com.studioreina.repository.custom.ServiceRepositoryCustom;

public interface ServiceRepository extends CrudRepository<Service, Long>, ServiceRepositoryCustom {
}
