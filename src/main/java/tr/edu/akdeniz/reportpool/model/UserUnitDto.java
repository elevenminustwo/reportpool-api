package tr.edu.akdeniz.reportpool.model;

public class UserUnitDto {

    public int departmentId;
    public int userId;
    public String unitId;

    public UserUnitDto() {
    }

    public UserUnitDto(int departmentId, int userId, String unitId) {
        this.departmentId = departmentId;
        this.userId = userId;
        this.unitId = unitId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
