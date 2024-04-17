package com.assignment.CovidVaccination.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.assignment.CovidVaccination.entity.CitizenEntity;

@Repository
public class CitizenDAOImplementation implements CitizenDAO {

	private final EntityManager entityManager;
	
	@Autowired
	public CitizenDAOImplementation(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public CitizenEntity registerCitizen(CitizenEntity citizen) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(citizen);
		return citizen;
	}

	@Override
	public List<CitizenEntity> getAllCitizens() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CitizenEntity> query = currentSession.createQuery(
				"FROM CitizenEntity", CitizenEntity.class);
		
		return query.getResultList();
	}

	@Override
	public List<CitizenEntity> getPartiallyVaccinatedCitizens() {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString = 
			"FROM CitizenEntity ce WHERE ce.vaccineCount=1";
		Query<CitizenEntity> query = currentSession.createQuery(
				queryString, CitizenEntity.class);
		
		return query.getResultList();
	}

	@Override
	public List<CitizenEntity> getFullyVaccinatedCitizens() {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString =
			"FROM CitizenEntity ce WHERE ce.vaccineCount=2";
		Query<CitizenEntity> query = currentSession.createQuery(
				queryString, CitizenEntity.class);
		
		return query.getResultList();
	}

	@Override
	public CitizenEntity getCitizenByAadhar(long aadhar) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.get(CitizenEntity.class, aadhar);
	}

	@Override
	public CitizenEntity updateVaccinationStatus(CitizenEntity citizen) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(citizen);
		return citizen;
	}

	@Override
	public void deleteVaccinatedCitizen(long aadhar) {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString =
			"DELETE FROM CitizenEntity WHERE id=?1";
		javax.persistence.Query query = currentSession.createQuery(queryString).setParameter(1, aadhar);
		query.executeUpdate();
	}

	@Override
	public List<CitizenEntity> getCitizensByVaccineName(String vaccineName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString =
			"FROM CitizenEntity WHERE vaccineName=?1";
		
		@SuppressWarnings("unchecked")
		Query<CitizenEntity> query = currentSession.createQuery(queryString).setParameter(1, vaccineName);
		
		List<CitizenEntity> citizens = query.getResultList();
		return citizens;
	}

	@Override
	public List<String> getVaccineNames() {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString =
			"SELECT DISTINCT c.vaccineName FROM CitizenEntity c";
		
		Query<String> query = currentSession.createQuery(queryString, String.class);
		
		List<String> vaccines = query.getResultList();
		return vaccines;
	}

	@Override
	public Long getCitizenCountByVaccine(String vaccineName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String queryString =
			"SELECT COUNT(*) FROM CitizenEntity c WHERE c.vaccineName =?1";
		javax.persistence.Query query = currentSession.createQuery(queryString).setParameter(1, vaccineName);
		
		Long count = (Long)query.getSingleResult();
		return count;
	}
}
