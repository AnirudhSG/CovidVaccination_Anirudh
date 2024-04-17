package com.assignment.CovidVaccination.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "citizen")
@SecondaryTable(name = "vaccinedetail")
public class CitizenEntity {
	
	@Id
	@Column(name = "aadhar")
	private long aadhar;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "dob")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "pincode")
	private int pincode;
	
	@Column(table = "vaccinedetail", name = "vaccine_count")
	private int vaccineCount;
	
	@Column(table = "vaccinedetail", name = "vaccine_name")
	private String vaccineName;
	
	@Column(table = "vaccinedetail", name = "vaccine1_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date vaccine1Date;
	
	@Column(table = "vaccinedetail", name = "vaccine2_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date vaccine2Date;
	
	@Column(table = "vaccinedetail", name = "vaccine3_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date vaccine3Date;
	
	@Column(table = "vaccinedetail", name = "vaccination_status")
	private String vaccinationStatus;
	
	// Default Constructor
	public CitizenEntity() {
		this.vaccineCount = 0;
		this.vaccinationStatus = "NOT_VACCINATED";
	}

	public CitizenEntity(long aadhar, String firstName, String lastName, Date dateOfBirth, String mobile, String city, String state, int pincode) {
		this.aadhar = aadhar;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.mobile = mobile;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public long getAadhar() {
		return aadhar;
	}

	public void setId(long aadhar) {
		this.aadhar = aadhar;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public int getVaccineCount() {
		return vaccineCount;
	}

	public void setVaccineCount(int vaccineCount) {
		this.vaccineCount = vaccineCount;
	}

	public Date getVaccine1Date() {
		return vaccine1Date;
	}

	public void setVaccine1Date(Date vaccine1Date) {
		this.vaccine1Date = vaccine1Date;
	}

	public Date getVaccine2Date() {
		return vaccine2Date;
	}

	public void setVaccine2Date(Date vaccine2Date) {
		this.vaccine2Date = vaccine2Date;
	}

	public Date getVaccine3Date() {
		return vaccine3Date;
	}

	public void setVaccine3Date(Date vaccine3Date) {
		this.vaccine3Date = vaccine3Date;
	}

	public String getVaccinationStatus() {
		return vaccinationStatus;
	}

	public void setVaccinationStatus(String vaccinationStatus) {
		this.vaccinationStatus = vaccinationStatus;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
}
