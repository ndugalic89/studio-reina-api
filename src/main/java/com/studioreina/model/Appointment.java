package com.studioreina.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appointment", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class Appointment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="appointment_generator")
	@SequenceGenerator(name = "appointment_generator", sequenceName = "appointment_id_seq", allocationSize = 1)
	private long id;
	
	@NotNull
    @Valid
    @OneToOne
    @JoinColumn(name="user_entity_id",referencedColumnName="id", nullable=false)
	private User user;
	
	@OneToOne
	@NotNull
    @Valid
    @JoinColumn(name="service_id",referencedColumnName="id", nullable=false)
	private Service service;
	
	@Column(name = "appointment_dataTime")
	private Date dateTime;
	
	
	public Appointment() {
		super();
	}

	public Appointment(long id, User user, Service service, Date dateTime) {
		super();
		this.id = id;
		this.user = user;
		this.service = service;
		this.dateTime = dateTime;
	}

	public long getId() {
		return id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Service getService() {
		return service;
	}
	
	public void setService(Service service) {
		this.service = service;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
