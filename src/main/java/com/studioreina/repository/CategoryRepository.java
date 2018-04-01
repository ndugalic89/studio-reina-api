package com.studioreina.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.studioreina.model.Category;
import com.studioreina.repository.custom.CategoryRepositoryCustom;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>, CategoryRepositoryCustom  {
	
}
