package com.project.maintest;

import com.project.bbdd.ConnectionManager;
import com.project.entities.Employee;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DatabaseConnection {

    public static void main(String[] args) {
        Connection conn = ConnectionManager.getMySQLConnection();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("managementProjectPU");
        EntityManager em = emf.createEntityManager();
        System.out.println("" + em.find( Employee.class, 1 ));
    }
}
