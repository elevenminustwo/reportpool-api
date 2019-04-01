package tr.edu.akdeniz.reportpool.model;

public class UserUnitDto {

    private int departmentunitId;
    private String departmentName;
    private String unitName;

    public UserUnitDto(int departmentunitId, String departmentName, String unitName) {
        this.departmentunitId = departmentunitId;
        this.departmentName = departmentName;
        this.unitName = unitName;
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
