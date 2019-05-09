package tr.edu.akdeniz.reportpool.model;

public class DepartmentUnitAddDto {
    public int depId;
    public String name;

    public DepartmentUnitAddDto() {
    }

    public DepartmentUnitAddDto(int depId, String name) {
        this.depId = depId;
        this.name = name;
    }

    public int getDepartmentId() {
        return depId;
    }

    public void setDepartmentId(int depId) {
        this.depId = depId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
