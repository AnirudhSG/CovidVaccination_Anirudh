package com.assignment.CovidVaccination.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.CovidVaccination.dao.CitizenDAO;
import com.assignment.CovidVaccination.entity.CitizenEntity;

@Service
public class VaccineServiceImplementation implements VaccineService {
	
	private final CitizenDAO citizenDAO;
	
	@Autowired
	public VaccineServiceImplementation(CitizenDAO citizenDAO) {
		this.citizenDAO = citizenDAO;
	}
	
	@Override
	@Transactional
	public CitizenEntity registerCitizen(CitizenEntity citizen) {
		return citizenDAO.registerCitizen(citizen);
	}

	@Override
	@Transactional
	public List<CitizenEntity> getAllCitizens() {
		return citizenDAO.getAllCitizens();
	}

	@Override
	@Transactional
	public List<CitizenEntity> getPartiallyVaccinatedCitizens() {
		return citizenDAO.getPartiallyVaccinatedCitizens();
	}

	@Override
	@Transactional
	public List<CitizenEntity> getFullyVaccinatedCitizens() {
		return citizenDAO.getFullyVaccinatedCitizens();
	}

	@Override
	@Transactional
	public CitizenEntity getCitizenByAadhar(long aadhar) {
		return citizenDAO.getCitizenByAadhar(aadhar);
	}

	@Override
	@Transactional
	public CitizenEntity updateVaccinationStatus(CitizenEntity citizen) {
		return citizenDAO.updateVaccinationStatus(citizen);
	}

	@Override
	@Transactional
	public void deleteVaccinatedCitizen(long aadhar) {
		citizenDAO.deleteVaccinatedCitizen(aadhar);
	}

	@Override
	@Transactional
	public List<CitizenEntity> getCitizensByVaccineName(String vaccineName) {
		return citizenDAO.getCitizensByVaccineName(vaccineName);
	}

	@Override
	@Transactional
	public List<String> getVaccineNames() {
		return citizenDAO.getVaccineNames();
	}

	@Override
	@Transactional
	public Long getCitizenCountByVaccine(String vaccineName) {
		return citizenDAO.getCitizenCountByVaccine(vaccineName);
	}
}