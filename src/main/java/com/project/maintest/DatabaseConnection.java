package com.project.maintest;

import com.project.bbdd.ConnectionManager;
import com.project.entities.Project;
import com.project.entities.Task;
import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DatabaseConnection {

    public static void main(String[] args) {
        Connection conn = ConnectionManager.getMySQLConnection();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("managementProjectPU");
        EntityManager em = emf.createEntityManager();
        
        System.out.println("PROJECT BY ID: " + em.find(Project.class, 1));
        System.out.println("TASK BY ID: " + em.find(Task.class, 1));
    }
}
