package tr.edu.akdeniz.reportpool.model;

public class UserUnitDto {

    private int departmentunitId;
    private String departmentName;
    private String unitName;
    private int departmentId;
    private int unitId;

    public UserUnitDto(int departmentunitId, String departmentName, String unitName, int departmentId, int unitId) {
        this.departmentunitId = departmentunitId;
        this.departmentName = departmentName;
        this.unitName = unitName;
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

    public int getDepartmentunitId() {
        return departmentunitId;
    }

    public void setDepartmentunitId(int departmentunitId) {
        this.departmentunitId = departmentunitId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
