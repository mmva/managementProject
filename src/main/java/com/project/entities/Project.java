package com.project.entities;

import com.project.models.ActiveRecord;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "projects")
public class Project extends ActiveRecord<Project> implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(length=100)
    private String name;
    
    @Basic(optional = true)
    @Column(length=255)
    private String description;
    
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    // @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="id_project", referencedColumnName="id", nullable=false)    
    private List<Task> tasksList;

    public Project() {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
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

    @XmlTransient
    public List<Task> getTasks() {
        return tasksList;
    }

    public void setTasks(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    
    // Dos objetos iguales si disponen del mismo identificador id
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Project other = (Project) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }   
    
    // Implementacion del Active Record    
    public static Project findById(EntityManager em, int id){
         Project object =  em.find(Project.class, id);
         
         return object;
    }     
    
    public static List<Project> findByPage(EntityManager em, int page, int pagePerPage){
        String eql = "SELECT x FROM Project x ORDER BY x.id";
        TypedQuery<Project> query = em.createQuery(eql, Project.class);
        int limit1 = pagePerPage*(page-1);
        
        query.setFirstResult(limit1);
        query.setMaxResults(pagePerPage);
        List<Project> projects = query.getResultList();        
        
        return projects;
    }
    
    public static long count(EntityManager em){
        String eql = "SELECT count(x) FROM Project x"; // Siempre indicar alias
        TypedQuery<Number> query = em.createQuery(eql, Number.class);
        long count = query.getSingleResult().longValue(); 
        
        System.out.println("Number of projects: " + count);
        
        return count;        
    }       
}
