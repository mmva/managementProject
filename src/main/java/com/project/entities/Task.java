package com.project.entities;

import com.project.models.ActiveRecord;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@Entity
@Table(name = "tasks")
public class Task extends ActiveRecord<Task> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(length=100)
    private String name;
    
    @Basic(optional = false)
    @Column(length=255)
    private String description;
    
    @Basic(optional = false)
    @Column(name = "estimate_at")
    @Temporal(TemporalType.TIME)
    private Date estimateAt;
    
    @Basic(optional = true)
    @Column(name = "date_start")
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    
    @Basic(optional = true)
    @Column(name = "date_finish")
    @Temporal(TemporalType.DATE)
    private Date dateFinish;
        
    @Basic(optional = false)
    @Column(name = "date_last_update")
    @Temporal(TemporalType.TIMESTAMP)    
    private Date dateLastUpdate;
        
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;
        
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    private Project project;

    public Task() {
    }

    public Task(String name, String description, Time estimateAt, Date dateStart, Date dateFinish, Date dateLastUpdate) {
        this.name = name;
        this.description = description;
        this.estimateAt = estimateAt;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.dateLastUpdate = dateLastUpdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEstimateAt() {
        return estimateAt;
    }

    public void setEstimateAt(Date estimateAt) {
        this.estimateAt = estimateAt;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // Dos objetos iguales si coincide su identificador unico
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Task other = (Task) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", name=" + name + ", description=" + description + ", estimateAt=" + estimateAt + ", dateStart=" + dateStart + ", dateFinish=" + dateFinish + ", dateLastUpdate=" + dateLastUpdate + ", project=" + project + '}';
    }  
    
    // Implementacion del Active Record
    public static Task findById(EntityManager em, int id){
         Task object = em.find(Task.class, id);
         
         return object;
    } 
    
    public static List<Task> findByPage(EntityManager em, int page, int pagePerPage){
        String eql = "SELECT x FROM Task x ORDER BY x.id";
        TypedQuery<Task> query = em.createQuery(eql, Task.class);
        int limit1 = pagePerPage*(page-1);
        
        query.setFirstResult(limit1);
        query.setMaxResults(pagePerPage);
        List<Task> tasks = query.getResultList();        
        
        return tasks;
    }
    
    public static long count(EntityManager em){
        String eql = "SELECT count(x) FROM Task x"; // Siempre indicar alias
        TypedQuery<Number> query = em.createQuery(eql, Number.class);
        long count = query.getSingleResult().longValue(); 
        
        System.out.println("Number of taks: " + count);
        
        return count;        
    }       
}
