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
import com.studioreina.model.Service;
import com.studioreina.repository.ServiceRepository;
import com.studioreina.util.ErrorReport;

@RestController
@RequestMapping(value="/service")
public class ServiceController {

	public static final Logger logger = LoggerFactory.getLogger(StudioReinaAPI.class);
	
	@Autowired
	private ServiceRepository serviceRepository;
 
	/**
	 * Get all services
	 * @return List of services
	 * 
	 */
	@RequestMapping(value="/services", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Service>> getAllServices() {
		List<Service> serviceList = new ArrayList<>();
		Iterable<Service> services = serviceRepository.findAll();
		logger.info("getting services");
 
		services.forEach(serviceList::add);
		if (serviceList.isEmpty()) {
            return new ResponseEntity<List<Service>>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Service>>(serviceList, HttpStatus.OK);
	}
	
	/** 
	 * Check if service exist
	 * @param name of the service
	 * @return True or false if service exist or not
	 * 
	 */
	public boolean isExist(String name) {
		return serviceRepository.isExist(name);
	}
	
	/** 
	 * Insert new service in database
	 * @param service object
	 * @return Inserted service
	 */
	@RequestMapping(value="/insert")
	public ResponseEntity<?> insert(@RequestBody Service service) {
		if(isExist(service.getName())) {
			logger.info("Service with name {} exist! Insert failed", service.getName());
			return new ResponseEntity<ErrorReport>(
					new ErrorReport("Service with name: '" + service.getName() + "' already exist! Insert failed"), 
					HttpStatus.OK);
		} 
		logger.info("Service with name {} don't exist! Insert successed!", service.getName());
		return new ResponseEntity<Service>(
				serviceRepository.save(new Service(service.getId(), service.getName(), service.getPrice(), service.getCategory())),  //save(new Service(service.getId(), service.getName())), 
				HttpStatus.OK
				);
	}
	
	/**
	 * Delete service with id
	 * @param id of service that will be deleted
	 */
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Service with id {}", id);
		 
		Service service = serviceRepository.findOne(id);
        if (service == null) {
            logger.error("Unable to delete. Service with id {} not found.", id);
            return new ResponseEntity<ErrorReport>(
            		new ErrorReport("Service with id " + id + " not found."),
                    HttpStatus.OK);
        }
        serviceRepository.delete(id);
        return new ResponseEntity<Service>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Delete all services in database
	 */
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
	public void deleteAll() {
		serviceRepository.deleteAll();
	}
	
	/**
	 * Get Service by Id
	 * @param id of service
	 * @return service object with requested id
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getServiceById(@PathVariable("id") long id) {
		Service service = serviceRepository.findOne(id);
		if (service == null) {
            logger.error("Service with id {} not found.", id);
            return new ResponseEntity<ErrorReport>(
            		new ErrorReport("Service with id " + id + " not found."),
                    HttpStatus.OK);
        }
		return new ResponseEntity<Service>(service, HttpStatus.OK);
	}
	
}
