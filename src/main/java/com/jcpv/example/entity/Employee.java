package com.jcpv.example.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by JanCarlo on 03/07/2017.
 */
@Entity
@Table(name="EMPLOYEE")
@Cache(region = "personCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EMP_ID")
    private Long id;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name = "DESIGNATION")
    private String designation;

    @ManyToOne
    @JoinColumn(name = "DPT_ID")
    private  Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return getId() != null ? getId().equals(employee.getId()) : employee.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", department=" + department +
                '}';
    }
}

