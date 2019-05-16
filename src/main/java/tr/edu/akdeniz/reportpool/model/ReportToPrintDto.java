package tr.edu.akdeniz.reportpool.model;

public class ReportToPrintDto {

    private int reportId;
    private String text;
    private String dateCompleted;
    private String firstNameOfSubmitter;
    private String lastNameOfSubmitter;
    private String departmentName;
    private String unitName;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getFirstNameOfSubmitter() {
        return firstNameOfSubmitter;
    }

    public void setFirstNameOfSubmitter(String firstNameOfSubmitter) {
        this.firstNameOfSubmitter = firstNameOfSubmitter;
    }

    public String getLastNameOfSubmitter() {
        return lastNameOfSubmitter;
    }

    public void setLastNameOfSubmitter(String lastNameOfSubmitter) {
        this.lastNameOfSubmitter = lastNameOfSubmitter;
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
