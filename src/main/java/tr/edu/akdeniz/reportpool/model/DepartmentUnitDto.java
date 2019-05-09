package tr.edu.akdeniz.reportpool.model;

public class DepartmentUnitDto {
    public int departmentId;
    public int unitId;

    public DepartmentUnitDto() {
    }

    public DepartmentUnitDto(int departmentId, int unitId) {
        this.departmentId = departmentId;
        this.unitId = unitId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
