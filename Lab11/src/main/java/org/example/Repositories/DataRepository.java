package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.logger.LoggerConfig;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DataRepository<T, ID extends Serializable> {
    protected EntityManager em;
    private final Class<T> entityClass;
    protected final Logger logger = LoggerConfig.getLogger();

    public DataRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public T findById(ID id) {
        long start = System.currentTimeMillis();
        try {
            return em.find(entityClass, id);
        } finally {
            logExecutionTime("findById", start);
        }
    }

    public List<T> findAll() {
        long start = System.currentTimeMillis();
        try {
            return em.createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass).getResultList();
        } finally {
            logExecutionTime("findAll", start);
        }
    }

    public void persist(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            logger.info("Persisted: " + entity);
        } catch (Exception e) {
            handleException(e);
            if (tx.isActive()) tx.rollback();
        }
    }

    public T merge(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T merged = em.merge(entity);
            tx.commit();
            return merged;
        } catch (Exception e) {
            handleException(e);
            if (tx.isActive()) tx.rollback();
            return null;
        }
    }

    public void remove(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            tx.commit();
        } catch (Exception e) {
            handleException(e);
            if (tx.isActive()) tx.rollback();
        }
    }

    protected void handleException(Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }

    private void logExecutionTime(String methodName, long start) {
        long duration = System.currentTimeMillis() - start;
        logger.info(methodName + " took " + duration + " ms");
    }
}