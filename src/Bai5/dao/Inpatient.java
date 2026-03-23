package Bai5.dao;

import java.time.LocalDate;

public class Inpatient {
    private int id;
    private String code;
    private String fullName;
    private int age;
    private String department;
    private String diagnosis;
    private LocalDate admitDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDate getAdmitDate() { return admitDate; }
    public void setAdmitDate(LocalDate admitDate) { this.admitDate = admitDate; }
}