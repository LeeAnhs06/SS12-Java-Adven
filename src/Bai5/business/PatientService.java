package Bai5.business;

import Bai5.dao.Inpatient;
import Bai5.dao.PatientDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientService {
    private final PatientDAO dao = new PatientDAO();

    public void init() throws SQLException {
        dao.ensureTable();
    }

    public List<Inpatient> getAllPatients() throws SQLException {
        return dao.findAllBasic();
    }

    public boolean admit(String code, String fullName, int age, String dept, String diagnosis) throws SQLException {
        Inpatient p = new Inpatient();
        p.setCode(code);
        p.setFullName(fullName);
        p.setAge(age);
        p.setDepartment(dept);
        p.setDiagnosis((diagnosis == null || diagnosis.isBlank()) ? null : diagnosis);
        p.setAdmitDate(LocalDate.now());
        return dao.insert(p) == 1;
    }

    public boolean updateDiagnosis(int id, String diagnosis) throws SQLException {
        return dao.updateDiagnosisById(id, diagnosis) == 1;
    }

    public LocalDate getAdmitDate(int id) throws SQLException {
        java.sql.Date d = dao.findAdmitDateById(id);
        return d == null ? null : d.toLocalDate();
    }

    public boolean discharge(int id) throws SQLException {
        return dao.deleteById(id) == 1;
    }
}