package com.studioreina.controller;

import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.studioreina.StudioReinaAPI;
import com.studioreina.model.Category;
import com.studioreina.repository.CategoryRepository;
import com.studioreina.util.ErrorReport;

@RestController
@RequestMapping(value="/category")
public class CategoryController {

	public static final Logger logger = LoggerFactory.getLogger(StudioReinaAPI.class);
	
	@Autowired
	private CategoryRepository categoryRepository;
 
	/**
	 * Get all categories
	 * @return List of categories
	 * 
	 */
	@RequestMapping(value="/categories", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categorylist = new ArrayList<>();
		Iterable<Category> categories = categoryRepository.findAll();
		logger.info("getting categories");
 
		categories.forEach(categorylist::add);
		if (categorylist.isEmpty()) {
            return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Category>>(categorylist, HttpStatus.OK);
	}
	
	/** 
	 * Check if category exist
	 * @param name of the category
	 * @return True or false if category exist or not
	 * 
	 */
	public boolean isExist(String name) {
		return categoryRepository.isExist(name);
	}
	
	/** 
	 * Insert new category in database
	 * @param category object
	 * @return Inserted category
	 */
	@RequestMapping(value="/insert")
	public ResponseEntity<?> insert(@RequestBody Category category) {
		if(isExist(category.getName())) {
			logger.info("Category with name {} exist", category.getName());
			return new ResponseEntity<ErrorReport>(
					new ErrorReport("Category with name: '" + category.getName() + "' already exist!"), 
					HttpStatus.OK);
		} 
		logger.info("Category with name {} don't exist", category.getName());
		return new ResponseEntity<Category>(
				categoryRepository.save(new Category(category.getId(), category.getName())), 
				HttpStatus.OK
				);
	}
	
	/**
	 * Delete category with id
	 * @param id of category that will be deleted
	 */
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Category with id {}", id);
		 
        Category category = categoryRepository.findOne(id);
        if (category == null) {
            logger.error("Unable to delete. Category with id {} not found.", id);
            return new ResponseEntity<ErrorReport>(
            		new ErrorReport("Category with id " + id + " not found."),
                    HttpStatus.OK);
        }
		categoryRepository.delete(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Delete all categories in database
	 */
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
	public void deleteAll() {
		categoryRepository.deleteAll();
	}
	
	/**
	 * Get Category by Id
	 * @param id of category
	 * @return category object with requested id
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCategoryById(@PathVariable("id") long id) {
		Category category = categoryRepository.findOne(id);
		if (category == null) {
            logger.error("Category with id {} not found.", id);
            return new ResponseEntity<ErrorReport>(
            		new ErrorReport("Category with id " + id + " not found."),
                    HttpStatus.OK);
        }
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	
}
