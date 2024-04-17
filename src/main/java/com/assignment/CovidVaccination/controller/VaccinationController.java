package com.assignment.CovidVaccination.controller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.CovidVaccination.controller.exceptions.CitizenAlreadyExistsException;
import com.assignment.CovidVaccination.controller.exceptions.CitizenNotFoundException;
import com.assignment.CovidVaccination.controller.exceptions.InvalidAadharException;
import com.assignment.CovidVaccination.controller.exceptions.UnderAgeException;
import com.assignment.CovidVaccination.controller.exceptions.MinimumPeriodViolationException;
import com.assignment.CovidVaccination.controller.exceptions.NoVaccineDataException;
import com.assignment.CovidVaccination.controller.exceptions.VaccineLimitExceedException;
import com.assignment.CovidVaccination.controller.exceptions.DifferentVaccineException;
import com.assignment.CovidVaccination.controller.exceptions.VaccineNotFoundException;
import com.assignment.CovidVaccination.entity.CitizenEntity;
import com.assignment.CovidVaccination.services.VaccineService;

@RestController
@RequestMapping("/api")
public class VaccinationController {
	
	private final VaccineService vaccineService;
	
	@Autowired
	public VaccinationController(VaccineService vaccineService) {
		this.vaccineService = vaccineService;
	}
	
	// Method to validate aadhar
	private boolean validateAadhar(long aadhar) {
		if (String.valueOf(aadhar).length() != 12) {
			return false;
		}
		return true;
	}
	
	
	/*
	 * @POST - register new citizen
	 * params - citizen entity from request body
	 * return - newly registered citizen or error
	 */
	@PostMapping("/citizens/register")
	public ResponseEntity<Object> register(@RequestBody CitizenEntity params) {
		try {
			// Check if Aadhar is not valid
			if (!validateAadhar(params.getAadhar())) {
				throw new InvalidAadharException();
			}
			
			// Check if citizen already registered
			CitizenEntity citizen = vaccineService.getCitizenByAadhar(params.getAadhar());
			if (citizen != null) {
				throw new CitizenAlreadyExistsException();
			}
			
			// Check age of citizen
			LocalDateTime dateOfBirth = params.getDateOfBirth()
					.toLocalDate()
					.atTime(LocalTime.now());
			
			Duration duration = Duration
					.between(LocalDateTime.now(), dateOfBirth);
			
			long age = Math.abs(duration.toDays()) / 365;
			if (age < 18) {
				throw new UnderAgeException();
			}
			
			CitizenEntity registeredCitizenEntity = vaccineService.registerCitizen(params);
			
			return ResponseHandler.generateResponse("Citizen registered successfully!", HttpStatus.CREATED, registeredCitizenEntity);
		}
		catch (CitizenAlreadyExistsException e) {
			return ResponseHandler.generateErrorResponse("Aadhar number already registered!", HttpStatus.CONFLICT);
		}
		catch (InvalidAadharException e) {
			return ResponseHandler.generateErrorResponse("Invalid aadhar number!", HttpStatus.BAD_REQUEST);
		}
		catch (UnderAgeException e) {
			return ResponseHandler.generateErrorResponse("Age less than suggested!", HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			if (e instanceof org.springframework.dao.DataIntegrityViolationException) {
				return ResponseHandler.generateErrorResponse("Received invalid data!", HttpStatus.BAD_REQUEST);
			}
			return ResponseHandler.generateErrorResponse("OOPS! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/*
	 * @GET - fetch all citizens
	 * params -
	 * return - all citizens or error
	 */
	@GetMapping("/citizens/list/all")
	public ResponseEntity<Object> getAllCitizens() {
		try {
			List<CitizenEntity> allCitizens = vaccineService.getAllCitizens();
			
			return ResponseHandler.generateResponse(null, HttpStatus.OK, allCitizens);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("OOPS! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * @GET - fetch citizens with vaccination status = PARTIAL
	 * params -
	 * return - all citizens with vaccination status = PARTIAL, or error
	 */
	@GetMapping("/citizens/list/status/partial")
	public ResponseEntity<Object> getPartiallyVaccinatedCitizens() {
		try {
			List<CitizenEntity> vaccinatedCitizens = vaccineService.getPartiallyVaccinatedCitizens();
			
			return ResponseHandler.generateResponse(null, HttpStatus.OK, vaccinatedCitizens);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("OOPS! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * @GET - fetch citizens with vaccination status = FULL
	 * params -
	 * return - all citizens with vaccination status = FULL, or error
	 */
	@GetMapping("/citizens/list/status/full")
	public ResponseEntity<Object> getFullyVaccinatedCitizens() {
		try {
			List<CitizenEntity> vaccinatedCitizens = vaccineService.getFullyVaccinatedCitizens();
			
			return ResponseHandler.generateResponse(null, HttpStatus.OK, vaccinatedCitizens);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("OOPS! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * @GET - fetch a single citizen by aadhar number
	 * params - citizen's aadhar number
	 * return - single citizen or error
	 */
	@GetMapping("/citizens/list/{aadhar}")
	public ResponseEntity<Object> getCitizen(@PathVariable long aadhar) {
		try {
			// Check if Aadhar is not valid
			if (!validateAadhar(aadhar)) {
				throw new InvalidAadharException();
			}
			
			CitizenEntity citizen = vaccineService.getCitizenByAadhar(aadhar);
			if (citizen == null) {
				throw new CitizenNotFoundException();
			}
			
			return ResponseHandler.generateResponse("Successfully fetched.", HttpStatus.OK, citizen);
		}
		catch (InvalidAadharException e) {
			return ResponseHandler.generateErrorResponse("Invalid aadhar number!", HttpStatus.BAD_REQUEST);
		}
		catch (CitizenNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Aadhar number not registered!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("OOPS! Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// Method to find next vaccinationDate
	Date getNextVaccinationDate(long aadhar) {
		CitizenEntity citz = vaccineService.getCitizenByAadhar(aadhar);
		
		Calendar cal = Calendar.getInstance();
		Date nextVaccineDate = new Date(System.currentTimeMillis());
		if (citz.getVaccineCount() == 1) {
			nextVaccineDate = citz.getVaccine1Date();
			cal.setTime(nextVaccineDate);
			cal.add(Calendar.DATE, 121);
			nextVaccineDate = new Date(cal.getTimeInMillis());
		}
		else if (citz.getVaccineCount() == 2) {
			nextVaccineDate = citz.getVaccine2Date();
			cal.setTime(nextVaccineDate);
			cal.add(Calendar.DATE, 271);
			nextVaccineDate = new Date(cal.getTimeInMillis());
		}
		
		return nextVaccineDate;
	}
	
	// Method to update vaccination status & vaccination date for citizen
	public CitizenEntity getUpdatedCitizen(CitizenEntity citizen, String vaccineName) throws
			MinimumPeriodViolationException,
			DifferentVaccineException,
			VaccineLimitExceedException {
		
		int curVaccineCount = citizen.getVaccineCount();
		if (curVaccineCount == 0) {
			citizen.setVaccineCount(1);
			citizen.setVaccine1Date(new Date(System.currentTimeMillis()));
			citizen.setVaccineName(vaccineName);
			citizen.setVaccinationStatus("PARTIALLY_VACCINATED");
		}
		else if (curVaccineCount == 1) {
			LocalDateTime vaccine1Date = citizen.getVaccine1Date()
					.toLocalDate()
					.atTime(LocalTime.now());
			
			Duration duration = Duration
					.between(LocalDateTime.now(), vaccine1Date);
			
			long numberOfDays = Math.abs(duration.toDays());

			if (numberOfDays <= 120) {
				throw new MinimumPeriodViolationException();
			}
			
			if (!citizen.getVaccineName().equals(vaccineName)) {
				throw new DifferentVaccineException();
			}
			
			citizen.setVaccineCount(2);
			citizen.setVaccine2Date(new Date(System.currentTimeMillis()));
			citizen.setVaccinationStatus("FULLY_VACCINATED");
		}
		else if (curVaccineCount == 2) {
			LocalDateTime vaccine2Date = citizen.getVaccine2Date()
					.toLocalDate()
					.atTime(LocalTime.now());
			
			Duration duration = Duration
					.between(LocalDateTime.now(), vaccine2Date);
			
			long numberOfDays = Math.abs(duration.toDays());
			if (numberOfDays <= 270) {
				throw new MinimumPeriodViolationException();
			}
			
			if (!citizen.getVaccineName().equals(vaccineName)) {
				throw new DifferentVaccineException();
			}
			
			citizen.setVaccineCount(3);
			citizen.setVaccine3Date(new Date(System.currentTimeMillis()));
			citizen.setVaccinationStatus("BOOSTER_DOSE");
		}
		else if (curVaccineCount == 3) {
			throw new VaccineLimitExceedException();
		}
		
		return citizen;
	}
	
	
	/*
	 * @PUT - update vaccination status of citizen by aadhar number
	 * params - citizen's aadhar number
	 * return - single citizen with updated vaccination status & date or error
	 */
	@PutMapping("/citizens/status/update/{aadhar}/{vaccineName}")
	public ResponseEntity<Object> updateVaccinationStatus(@PathVariable("aadhar") long aadhar, @PathVariable("vaccineName") String vaccineName) {
		try {
			if (!validateAadhar(aadhar)) {
				throw new InvalidAadharException();
			}
			
			CitizenEntity citizen = vaccineService.getCitizenByAadhar(aadhar);
			if (citizen == null) {
				throw new CitizenNotFoundException();
			}
			
			CitizenEntity updatedCitizen = getUpdatedCitizen(citizen, vaccineName);
			
			CitizenEntity updatedCitizenEntity = vaccineService.updateVaccinationStatus(updatedCitizen);
			
			return ResponseHandler.generateResponse(citizen.getVaccinationStatus(), HttpStatus.OK, updatedCitizenEntity);
		}
		catch (InvalidAadharException e) {
			return ResponseHandler.generateErrorResponse("Invalid aadhar number", HttpStatus.OK);
		}
		catch (CitizenNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Aadhar number not found!", HttpStatus.NOT_FOUND);
		}
		catch (VaccineLimitExceedException e) {
			return ResponseHandler.generateErrorResponse("Not allowed to take more than 3 vaccines!", HttpStatus.NOT_ACCEPTABLE);
		}
		catch (MinimumPeriodViolationException e) {
			Date vaccineDate = getNextVaccinationDate(aadhar);
			return ResponseHandler.generateErrorResponse("Can't vaccinate before " + vaccineDate, HttpStatus.NOT_ACCEPTABLE);
		}
		catch (DifferentVaccineException e) {
			return ResponseHandler.generateErrorResponse("Citizen previously vaccinated with different vaccine!", HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/*
	 * @Get - fetch vaccination status of a citizen by aadhar number
	 * params - citizen's aadhar number
	 * return - single citizen with vaccination status or error
	 */
	@GetMapping("/citizens/status/{aadhar}")
	public ResponseEntity<Object> getVaccinationStatus(@PathVariable long aadhar) {
		try {
			if (!validateAadhar(aadhar)) {
				throw new InvalidAadharException();
			}
			
			CitizenEntity citizen = vaccineService.getCitizenByAadhar(aadhar);
			if (citizen == null) {
				throw new CitizenNotFoundException();
			}
			
			String responseMessage = "";
			
			if (citizen.getVaccinationStatus().equals("NOT_VACCINATED")) {
				responseMessage = "Citizen is not vaccinated.";
			}
			else if (citizen.getVaccinationStatus().equals("PARTIALLY_VACCINATED")) {
				responseMessage = "Citizen is partially vaccinated.";
			}
			else if (citizen.getVaccinationStatus().equals("FULLY_VACCINATED")) {
				responseMessage = "Citizen is fully vaccinated.";
			}
			
			return ResponseHandler.generateResponse(responseMessage, HttpStatus.OK, citizen);
		}
		catch (InvalidAadharException e) {
			return ResponseHandler.generateErrorResponse("Invalid aadhar number!", HttpStatus.BAD_REQUEST);
		}
		catch (CitizenNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Aadhar number not found!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * @DELETE - delete a citizen by aadhar number
	 * params - citizen's aadhar number
	 * return - Success message or error
	 */
	@DeleteMapping("/citizens/delete/{aadhar}")
	public ResponseEntity<Object> deleteVaccinatedCitizen(@PathVariable long aadhar) {
		try {
			if (!validateAadhar(aadhar)) {
				throw new InvalidAadharException();
			}
			
			CitizenEntity citizen = vaccineService.getCitizenByAadhar(aadhar);
			if (citizen == null) {
				throw new CitizenNotFoundException();
			}
			
			if (citizen.getVaccineCount() < 3) {
				return ResponseHandler.generateErrorResponse("Citizen data can't be deleted!", HttpStatus.CONFLICT);
			}
			
			vaccineService.deleteVaccinatedCitizen(aadhar);
			
			return ResponseHandler.generateResponse("Citizen data deleted successfully.", HttpStatus.OK, HttpStatus.BAD_REQUEST);
		}
		catch (InvalidAadharException e) {
			return ResponseHandler.generateErrorResponse("Invalid aadhar number!", HttpStatus.BAD_REQUEST);
		}
		catch (CitizenNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Aadhar number not found!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	 * GET - fetch citizens by all vaccine names
	 * params - vaccine name
	 * return - list of citizens grouped by vaccine names or error
	 */
	@GetMapping("/citizens/list/vaccine/all")
	public ResponseEntity<Object> getCitizensByVaccine() {
		try {
			Map<String, List<CitizenEntity>> map = new HashMap<String, List<CitizenEntity>>();
			List<String> allVaccines = vaccineService.getVaccineNames();
			
			if (allVaccines == null) {
				throw new NoVaccineDataException();
			}
			
			for (String vaccine : allVaccines) {
				if (vaccine == null) continue;
				List<CitizenEntity> citizens = vaccineService.getCitizensByVaccineName(vaccine);
				map.put(vaccine, citizens);
			}
			
			return ResponseHandler.generateResponse("Successfully fetched.", HttpStatus.OK, map);
		}
		catch (NoVaccineDataException e) {
			return ResponseHandler.generateErrorResponse("No vaccine data available!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * GET - fetch citizens by single vaccine names
	 * params - vaccine name
	 * return - list of citizens with given vaccine
	 */
	@GetMapping("/citizens/list/vaccine/{vaccineName}")
	public ResponseEntity<Object> getCitizensByVaccine(@PathVariable String vaccineName) {
		try {
			List<CitizenEntity> citizens = vaccineService.getCitizensByVaccineName(vaccineName);
			
			if (citizens == null || (citizens != null && citizens.isEmpty())) {
				throw new VaccineNotFoundException();
			}
			
			return ResponseHandler.generateResponse("Successfully fetched.", HttpStatus.OK, citizens);
		}
		catch (VaccineNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Vaccine details not found!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * GET - fetch count of citizens with given vaccine
	 * params - vaccine name
	 * return - count of citizens given {vaccineName} vaccine
	 */
	@GetMapping("/citizens/count/vaccine/{vaccineName}")
	public ResponseEntity<Object> getCitizensCountByVaccine(@PathVariable String vaccineName) {
		try {
			Long count = vaccineService.getCitizenCountByVaccine(vaccineName);
			if (count == 0) {
				throw new VaccineNotFoundException();
			}
			
			return ResponseHandler.generateResponse("Successfully fetched.", HttpStatus.OK, count);
		}
		catch (VaccineNotFoundException e) {
			return ResponseHandler.generateErrorResponse("Vaccine details not found!", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	 * GET - fetch count of all vaccines
	 * params -
	 * return - list of all vaccines with count of citizens with that vaccine
	 */
	@GetMapping("/citizens/report/vaccine")
	public ResponseEntity<Object> getVaccineReport() {
		try {
			Map<String, Long> map = new HashMap<String, Long>();
			List<String> allVaccines = vaccineService.getVaccineNames();
			
			for (String vaccine : allVaccines) {
				if (vaccine == null) continue;
				Long count = vaccineService.getCitizenCountByVaccine(vaccine);
				map.put(vaccine, count);
			}
			
			return ResponseHandler.generateResponse("Successfully fetched.", HttpStatus.OK, map);
		}
		catch (Exception e) {
			return ResponseHandler.generateErrorResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
