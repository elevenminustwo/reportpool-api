package tr.edu.akdeniz.reportpool.model;

import java.util.Date;

public class UnitReportDto {
    public String name;
    public String surname;
    public Date completedDate;
    public String path;
    public String departmentName;
    public String unitName;

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public Date getCompletedDate() {
        return completedDate;
    }

    public String getPath() {
        return path;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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


    public UnitReportDto(String departmentName,String unitName,String name,String surname,Date completedDate,String path){
        this.name=name;
        this.surname=surname;
        this.departmentName=departmentName;
        this.unitName=unitName;
        this.completedDate=completedDate;
        this.path=path;

    }
}
