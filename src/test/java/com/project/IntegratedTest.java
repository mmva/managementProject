package com.project;

import com.project.bbdd.ConnectionManager;
import com.project.entities.Employee;
import com.project.entities.Project;
import com.project.entities.Task;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegratedTest {

    private EntityManager em;
    
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;
    
    Project project1;
    Project project2;
    
    Task task1;
    Task task2;
    Task task3;
    Task task4;
        
    @Before
    public void emptyTable() throws SQLException{ 
        
        Connection conn = ConnectionManager.getMySQLConnection();
        try {
            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            Statement stmt3 = conn.createStatement();
            Statement stmt4 = conn.createStatement();
            Statement stmt5 = conn.createStatement();
            stmt4.execute( "SET foreign_key_checks  = 0" );
            stmt1.execute( "TRUNCATE TABLE employees" );
            stmt2.execute( "TRUNCATE TABLE tasks" );
            stmt3.execute( "TRUNCATE TABLE projects" );
            stmt5.execute( "SET foreign_key_checks  = 1" );
        } catch ( Exception e ) {
            System.err.println( "Error Truncating" + e.getMessage());
        } finally {
            conn.close();
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("managementProjectPU");
        this.em = emf.createEntityManager();
        
        employee1 = new Employee( "Felix", "Garcia", "Tapia", "12345678-F", "felix@empresa.es", "abc", "PROGRAMADOR", "INACTIVO" );
        employee1.setId( 1L );
        employee2 = new Employee( "David", "Fernandez", "Galan", "87654321-A", "david@empresa.es", "def", "ANALISTA", "ACTIVO" );
        employee2.setId( 2L );
        employee3 = new Employee( "Ruben", "Gutierrez", "Perez", "12348765-C", "ruben@empresa.es", "sds", "JEFE_PROYECTO", "INACTIVO" );
        employee3.setId( 3L );
        employee4 = new Employee( "Marta", "Ecija", "Galan", "11114444-H", "marta@empresa.es", "dsd", "ANALISTA", "ACTIVO" );
        employee4.setId( 4L );
        employee5 = new Employee( "Isabel", "Lopez", "Galan", "22226666-P", "isabel@empresa.es", "sdasd", "ADMINISTRADOR", "INACTIVO" );
        employee5.setId( 5L );        
        
        this.project1 = new Project("Management Project", "Online Management Project");
        this.project1.setId(1);
        
        this.project2 = new Project("Academy", "Management Academies in Saragossa");
        this.project2.setId(2);  
        
        Time time = new Time(28800000);    
        this.task1 = new Task("Creacion BBDD", "Creacion de la base de datos y de las tablas", time, Calendar.getInstance().getTime(), null, null);
        this.task1.setProject(this.project1);
        this.task1.setEmployee(this.employee1);
        
        time = new Time(14400000);    
        this.task2 = new Task("Estructuracion", "Arquitectura inicial del proyecto", time, Calendar.getInstance().getTime(), null, null);
        this.task2.setProject(this.project1);
        this.task2.setEmployee(this.employee2);  
        
        time = new Time(7200000);    
        this.task3 = new Task("Entidades", "Creacion entidad Employee", time, Calendar.getInstance().getTime(), null, null);
        this.task3.setProject(this.project1);
        this.task3.setEmployee(this.employee3);   
        
        time = new Time(57600000);    
        this.task4 = new Task("Entidades y Testeo", "Creacion Entidades y Testeo", time, Calendar.getInstance().getTime(), null, null);
        this.task4.setProject(this.project2);
        this.task4.setEmployee(this.employee4);         
    }
    
    @Test
    public void test_create_findById() throws SQLException, ClassNotFoundException{ 
       // Creamos los empleados
       this.employee1.create(em); 
       this.employee2.create(em);
       this.employee3.create(em);
       this.employee4.create(em);
       this.employee5.create(em);
       // Creamos los proyectos
       this.project1.create(this.em);  
       this.project2.create(this.em); 
       // Creamos las tareas asociadas a los proyectos
       this.task1.create(this.em);
       this.task2.create(this.em);
       this.task3.create(this.em);
       this.task4.create(this.em);
       
       // Deberia tratarse del 1
       Assert.assertNotNull(Employee.findById(this.em, this.employee1.getId())); 
       Assert.assertNotNull(Project.findById(this.em, this.project1.getId()));   
       Assert.assertNotNull(Task.findById(this.em, this.task1.getId()));
       
       Assert.assertEquals(this.task1, Task.findById(this.em, this.task1.getId()));
    }   
    
    @Test
    public void test_findEmployeesProject() throws SQLException, ClassNotFoundException{ 
       // Creamos los empleados
       this.employee1.create(em); 
       this.employee2.create(em);
       this.employee3.create(em);
       this.employee4.create(em);
       this.employee5.create(em);
       // Creamos los proyectos
       this.project1.create(this.em);  
       this.project2.create(this.em); 
       // Creamos las tareas asociadas a los proyectos
       this.task1.create(this.em);
       this.task2.create(this.em);
       this.task3.create(this.em);
       this.task4.create(this.em);
       
       List<Employee> expectedList = new ArrayList<Employee>();
       expectedList.add(this.employee1);
       expectedList.add(this.employee2);
       expectedList.add(this.employee3);
       
       List<Employee> recoveredList = Project.findEmployeesProject(em, this.project1.getId());
       
       Assert.assertEquals(expectedList, recoveredList);
    }    
}
