package com.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "firt_name", length = 100 )
    private String firtName;
    
    @Column(name = "last_name", length = 100 )
    private String lastName;
    
    @Column(name = "last_name2", length = 100 )
    private String lastName2;
    
    @Column(name = "nif", length = 10 )
    private String nif;
    
    @Column(name = "email", length = 100, unique = true )
    private String email;
    
    @Column(length = 20 )
    private String password;
    
    @Column(name = "profile")
    private String profile;
    
    @Column(name = "active")
    private String active;
    
    @OneToMany( /*cascade=CascadeType.ALL,*/ fetch = FetchType.LAZY )
    @JoinColumn( name = "id_project", referencedColumnName = "id", nullable = false )
    private List<Task> tasks;

    // constructors
    
    public Employee() {
    }
 
    public Employee(String firtName, String lastName, String lastName2, String nif, String email, String password, String profile, String active ) {
        this.firtName = firtName;
        this.lastName = lastName;
        this.lastName2 = lastName2;
        this.nif = nif;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.active = active;
    }

    // getters and setters
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // equals and hashCode

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firtName=" + firtName + ", lastName=" + lastName + ", lastName2=" + lastName2 + ", nif=" + nif + ", email=" + email + ", profile=" + profile + ", active=" + active + '}';
    } 
    
    // ACTIVE RECORD
    public static Employee findById( EntityManager em, long id ) {
        Employee employee = em.find( Employee.class, id );
        return employee;
    }
    
    public static List<Employee> findByPage( EntityManager em, int page, int employeesPerPage ) {
        String eql = "SELECT x FROM Employee x ORDER BY x.id ASC";
        TypedQuery<Employee> query = em.createQuery( eql, Employee.class );
        int employee_limit = ( page - 1 ) * employeesPerPage;
        query.setFirstResult( employee_limit ); 
        query.setMaxResults( employeesPerPage );
        return query.getResultList();
    }
    
    public static long count( EntityManager em ) {
        String eql = "SELECT COUNT(x) FROM Employee x";
        TypedQuery<Number> query = em.createQuery( eql, Number.class );
        long count = query.getSingleResult().longValue();
        return count;
    }
    
    public static List<Employee> findByLastName(EntityManager em, String lastName) {
        String sql = "SELECT x FROM Employee x WHERE x.lastName LIKE '%' :lastName '%'";
        TypedQuery<Employee> query = em.createQuery(sql, Employee.class);
        query.setParameter( "lastName", lastName );
        return query.getResultList();
    }
    
    public static List<Employee> findByProfile(EntityManager em, String profile) {
        String sql = "SELECT x FROM Employee x WHERE x.profile = :profile";
        TypedQuery<Employee> query = em.createQuery(sql, Employee.class);
        query.setParameter( "profile", profile );
        return query.getResultList();
    }
    
    public static List<Employee> findByState(EntityManager em, String state) {
        String sql = "SELECT x FROM Employee x WHERE x.active = :state";
        TypedQuery<Employee> query = em.createQuery(sql, Employee.class);
        query.setParameter( "state", state );
        return query.getResultList();
    }
    
    // Modifications
    
    public boolean create( EntityManager em ) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin(); // iniciamos la transacción
            createNoTransaction( em );
            et.commit(); // guardamos los cambios en la base de datos
            return true;
        } catch ( Exception e ) {
            if ( et.isActive() ) {
                et.rollback(); // sirve para deshacer la transacción
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public void createNoTransaction( EntityManager em ) {
        em.persist( this );
        em.flush();
    }
    
    public Employee update( EntityManager em ) {
        EntityTransaction et = em.getTransaction();
        
        try {
            et.begin();
            Employee employee = updateNoTransaction( em );
            et.commit();
            return employee;
        } catch ( Exception e ) {
            if ( et.isActive() ) {
                et.rollback();
            }
            e.printStackTrace();
            return null;
        }  
    }
    
    public Employee updateNoTransaction( EntityManager em ) {
        Employee employee = em.merge( this );
        em.flush();
        return employee;
    }
    
    public boolean delete( EntityManager em ) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            deleteNoTransaction( em );
            et.commit();
            return true;
        } catch ( Exception e ) {
            if ( et.isActive() ) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    
    public void deleteNoTransaction( EntityManager em ) {
        em.remove( this );
        em.flush();
    } 
}