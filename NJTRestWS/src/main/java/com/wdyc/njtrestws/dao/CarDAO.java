/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdyc.njtrestws.dao;

import com.wdyc.njtrestws.domen.CarEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Dusan
 */
public class CarDAO {

    private static final String RETRIEVE_CARS_FOR_USER = "SELECT c FROM CarEntity c WHERE c.owner.id = :userId";
    private static final String RETRIEVE_CARS_REGISTRATION_SUGGESTION = "SELECT c FROM CarEntity c WHERE UPPER(c.registration) LIKE CONCAT('%',UPPER(:registration),'%')";

    public CarEntity saveCar(CarEntity car) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
            return car;
        } catch (Exception e) {
            throw new Exception("Greska prilikom cuvanja automobila! Automobil vec postoji!");
        } finally {
            em.close();
            emf.close();
        }
    }

    public List<CarEntity> retrieveCarsForUser(Integer userId) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CarEntity> query = em.createQuery(RETRIEVE_CARS_FOR_USER, CarEntity.class).setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Greska prilikom vracanja vozila za korisnika!");
        } finally {
            em.close();
            emf.close();
        }
    }

    public CarEntity updateCar(CarEntity car) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        CarEntity foundCar = em.find(CarEntity.class, car.getId());
        try {
            if (foundCar == null) {
                throw new Exception("The car you are trying to update does not exist!");
            } else {
                if (car.getRegistration() != null && !car.getRegistration().isEmpty()) {
                    foundCar.setRegistration(car.getRegistration());
                }
                if (car.getColor() != null && !car.getColor().isEmpty()) {
                    foundCar.setColor(car.getColor());
                }
                if (car.getOwner() != null) {
                    
                    foundCar.setOwner(car.getOwner());
                }
                em.getTransaction().commit();
                return foundCar;
            }
        } catch (Exception e) {
            throw new Exception("Greska prilikom promene vozila u bazi!");
        } finally {
            em.close();
            emf.close();
        }
    }

    public CarEntity retrieveByRegistration(String registration) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            CarEntity retrievedCar = em.createNamedQuery("CarEntity.findByRegistration", CarEntity.class).setParameter("registration", registration).getSingleResult();
            System.out.println(retrievedCar);
            return retrievedCar;
        } catch (Exception e) {
            throw new Exception("Greska! Automobil sa tom registarskom oznokom ne postoji!");
        } finally {
            em.close();
            emf.close();
        }
    }

    public List<CarEntity> retrieveRegistrationSuggestions(String registration) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CarEntity> query = em.createQuery(RETRIEVE_CARS_REGISTRATION_SUGGESTION, CarEntity.class).setParameter("registration", registration);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Greska prilikom vracanja vozila za sugestiju registracije!");
        } finally {
            em.close();
            emf.close();
        }
    }

    public CarEntity retrieveCarByVin(String vin) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.wdyc_NJTRestWS_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        try {
            CarEntity retrievedCar = (CarEntity) em.createNamedQuery("CarEntity.findByVin", CarEntity.class).setParameter("vin", vin).getSingleResult();
            return retrievedCar;
        } catch (Exception e) {
            throw new Exception("Greska! No car with that vin!");
        } finally {
            em.close();
            emf.close();
        }
    }

}
