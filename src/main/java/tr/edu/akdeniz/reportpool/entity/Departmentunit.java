package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Departmentunit {
    private int departmentUnitId;
    private int departmentId;
    private int unitId;

    @Id
    @Column(name = "DepartmentUnitID")
    public int getDepartmentUnitId() {
        return departmentUnitId;
    }

    public void setDepartmentUnitId(int departmentUnitId) {
        this.departmentUnitId = departmentUnitId;
    }

    @Basic
    @Column(name = "department_id", nullable = false)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "unit_id", nullable = false)
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departmentunit that = (Departmentunit) o;
        return departmentUnitId == that.departmentUnitId &&
                departmentId == that.departmentId &&
                unitId == that.unitId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentUnitId, departmentId, unitId);
    }
}
