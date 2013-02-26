/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "estimate_at")
    @Temporal(TemporalType.TIME)
    private Date estimateAt;
    @Column(name = "date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;
    @Column(name = "date_finish")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinish;
    @Basic(optional = false)
    @Column(name = "date_last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastUpdate;
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    @ManyToOne
    private Employee idEmployee;
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project idProject;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Task(Integer id, String name, String description, Date estimateAt, Date dateLastUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.estimateAt = estimateAt;
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

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Project getIdProject() {
        return idProject;
    }

    public void setIdProject(Project idProject) {
        this.idProject = idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.project.entities.Tasks[ id=" + id + " ]";
    }
    
}
