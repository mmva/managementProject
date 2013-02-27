package com.project;

import com.project.bbdd.ConnectionManager;
import com.project.entities.Employee;
import com.project.entities.Task;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
    private EntityManager em;
    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Employee employee4;
    private Employee employee5;
    
    @Before
    public void emptyTables() {
        try {
            Connection conn = ConnectionManager.getMySQLConnection();
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
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "managementProjectPU" );
        em = emf.createEntityManager();
        employee1 = new Employee( "Felix", "Garcia", "Tapia", "12345678-F", "felix@empresa.es", "abc", "PROGRAMADOR", "INACTIVO" );
        employee1.setId( 1L );
        employee2 = new Employee( "David", "Fernandez", "Galan", "87654321-A", "david@empresa.es", "def", "ANALISTA", "ACTIVO" );
        employee2.setId( 2L );
        employee3 = new Employee( "Ruben", "Gutierrez", "Perez", "12348765-C", "ruben@empresa.es", "sds", "JEFE_PROYECTO", "INACTIVO" );
        employee3.setId( 3L );
        employee4 = new Employee( "Marta", "Ecija", "Galan", "11114444-H", "marta@empresa.es", "dsd", "ANALISTA", "ACTIVO" );
        employee4.setId( 4L );
        employee5 = new Employee( "Alicia", "Ecija", "Garcia", "22226666-P", "isabel@empresa.es", "sdasd", "ADMINISTRADOR", "INACTIVO" );
        employee5.setId( 5L );
    }
    
    @After
    public void after() {
        em.close();
    }
    
    @Test
    public void test_create() throws SQLException {
        employee1.create( em );
        Employee employee = employee1.findById(em, employee1.getId() );
        Assert.assertEquals( employee1, employee );
    }
    
    @Test
    public void test_findById() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        Employee employee = Employee.findById( em, 3 );
        Assert.assertEquals( employee3, employee );
    }
    
    @Test
    public void test_count() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        long count = Employee.count( em );
        Assert.assertEquals( 5, count );
    }
    
    @Test
    public void test_findByPage() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        List<Employee> expected = new ArrayList<Employee>();
        expected.add( employee3 );
        expected.add( employee4 );
        
        List<Employee> employee = Employee.findByPage( em, 2, 2 );
        Assert.assertEquals( expected, employee );
    }
         
    @Test
    public void test_update() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        employee3.setPassword( "hola" );
        employee3.update( em );
        
        Employee employee = Employee.findById( em, employee3.getId() );
        Assert.assertEquals( employee3.getPassword(), employee.getPassword() );
    }
    
    @Test
    public void test_delete() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        
        employee3.delete(em);
        
        Employee employee = Employee.findById( em, employee3.getId() );       
        Assert.assertNull( employee );
    }  
    
    @Test 
    public void test_findByLastName() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em );
        
        List<Employee> expected = new ArrayList<Employee>();
        expected.add( employee5 );
        expected.add( employee4 );
        
        List<Employee> employees = Employee.findByLastName( em, employee4.getLastName() );
        
        Assert.assertEquals( expected, employees );
    }
    
    @Test 
    public void test_findByProfile() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em ); 
        
        List<Employee> expected = new ArrayList<Employee>();
        expected.add( employee4 );
        expected.add( employee2 );
        
        List<Employee> employees = Employee.findByProfile( em, employee2.getProfile() );
        
        Assert.assertEquals( expected, employees );
    }
    
    @Test 
    public void test_findByState() throws SQLException {
        employee1.create( em );
        employee2.create( em );
        employee3.create( em );
        employee4.create( em );
        employee5.create( em ); 
        
        List<Employee> expected = new ArrayList<Employee>();
        expected.add( employee5 );
        expected.add( employee1 );
        expected.add( employee3 );
        
        List<Employee> employees = Employee.findByState( em, employee1.getActive() );
        
        Assert.assertEquals( expected, employees );
    }
}
