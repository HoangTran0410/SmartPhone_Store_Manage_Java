/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

/**
 *
 * @author DELL
 */
public class Employee {

    private String empNo;
    private String empName;

    private Double salary;
    private int grade;
    private Double bonus;

    public Employee(String empNo, String empName,//
            Double salary, int grade, Double bonus) {
        this.empNo = empNo;
        this.empName = empName;
        this.salary = salary;
        this.grade = grade;
        this.bonus = bonus;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }
}
