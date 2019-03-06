package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Report {
    private int reportId;
    private String title;
    private String text;
    private Byte isCompleted;
    private Date dateCompleted;
    private int userId;
    private int departmentunitId;

    @Id
    @Column(name = "ReportID", nullable = false)
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "Title", nullable = true, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "IsCompleted", nullable = true)
    public Byte getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Byte isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Basic
    @Column(name = "DateCompleted", nullable = true)
    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "departmentunit_id", nullable = false)
    public int getDepartmentunitId() {
        return departmentunitId;
    }

    public void setDepartmentunitId(int departmentunitId) {
        this.departmentunitId = departmentunitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId &&
                userId == report.userId &&
                departmentunitId == report.departmentunitId &&
                Objects.equals(title, report.title) &&
                Objects.equals(text, report.text) &&
                Objects.equals(isCompleted, report.isCompleted) &&
                Objects.equals(dateCompleted, report.dateCompleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reportId, title, text, isCompleted, dateCompleted, userId, departmentunitId);
    }
}
