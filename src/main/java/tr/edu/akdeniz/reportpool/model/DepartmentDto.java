package tr.edu.akdeniz.reportpool.model;

public class DepartmentDto {

    public int departmentId;
    private String name;

    public DepartmentDto() {
    }

    public DepartmentDto(int departmentId, String name) {
        this.departmentId = departmentId;
        this.name = name;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
