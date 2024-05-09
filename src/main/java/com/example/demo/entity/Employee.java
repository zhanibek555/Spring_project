package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "employee_tbl")
public class Employee {

    public Employee() {
    }

    public Employee(int id, String name, String designation, double salary, String doj) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        Doj = doj;
    }

    @Id
    private int id;
    private String name;
    private String designation;
    private double salary;
    private String Doj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDoj() {
        return Doj;
    }

    public void setDoj(String doj) {
        Doj = doj;
    }
}
