package com.assignment.CovidVaccination.services;

import java.util.List;

import com.assignment.CovidVaccination.entity.CitizenEntity;

public interface VaccineService {
	
	public CitizenEntity registerCitizen(CitizenEntity citizen);
	public List<CitizenEntity> getAllCitizens();
	public List<CitizenEntity> getPartiallyVaccinatedCitizens();
	public List<CitizenEntity> getFullyVaccinatedCitizens();
	public CitizenEntity getCitizenByAadhar(long aadhar);
	public CitizenEntity updateVaccinationStatus(CitizenEntity citizen);
	public void deleteVaccinatedCitizen(long aadhar);
	public List<CitizenEntity> getCitizensByVaccineName(String vaccineName);
	public List<String> getVaccineNames();
	public Long getCitizenCountByVaccine(String vaccineName);
}
