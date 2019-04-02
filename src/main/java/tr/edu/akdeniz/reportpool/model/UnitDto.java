package tr.edu.akdeniz.reportpool.model;

public class UnitDto {

    public int unitId;
    public  String name;

    public UnitDto() {
    }

    public UnitDto(int unitId, String name) {
        this.unitId = unitId;
        this.name = name;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
