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
        employee1 = new Employee( "Felix", "Garcia", "Tapia", "12345678-F", "felix@empresa.es", "PROGRAMADOR", "INACTIVO" );
        employee1.setId( 1L );
        employee2 = new Employee( "David", "Fernandez", "Galan", "87654321-A", "david@empresa.es", "ANALISTA", "ACTIVO" );
        employee2.setId( 2L );
        employee3 = new Employee( "Ruben", "Gutierrez", "Perez", "12348765-C", "ruben@empresa.es", "JEFE_PROYECTO", "INACTIVO" );
        employee3.setId( 3L );
        employee4 = new Employee( "Marta", "Ecija", "Galan", "11114444-H", "marta@empresa.es", "ANALISTA", "ACTIVO" );
        employee4.setId( 4L );
        employee5 = new Employee( "Isabel", "Lopez", "Galan", "22226666-P", "isabel@empresa.es", "ADMINISTRADOR", "INACTIVO" );
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
    
    /*@Test
    public void test_findByPage() throws SQLException {
        book1.create( em );
        book2.create( em );
        book3.create( em );
        List<BookBBDD> expected = new ArrayList<BookBBDD>();
        expected.add( book1 );
        expected.add( book2 );
        
        List<BookBBDD> users = BookBBDD.findByPage( em, 1, 2 );
        Assert.assertEquals( expected, users );
    }
         
    @Test
    public void test_update() throws SQLException {
        book1.create( em );
        book2.create( em );
        book3.create( em );
        
        book1.setTitle( "Desconocido" );
        book1.update( em );
        
        BookBBDD book = BookBBDD.findById( em, book1.getId() );
        Assert.assertEquals( book1.getTitle(), book.getTitle() );
    }
    
    @Test
    public void test_delete() throws SQLException {
        book1.create( em );
        book2.create( em );
        book3.create( em );
       
        book1.delete( em );
        
        BookBBDD book = BookBBDD.findById( em, book1.getId() );
        
        Assert.assertNull( book );
    }   */ 
}
