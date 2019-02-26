package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@IdClass(ReportPK.class)
public class Report {
    private int reportId;
    private String reportText;
    private Date dateCompleted;
    private int userId;

    @Id
    @Column(name = "ReportID", nullable = false)
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "ReportText", nullable = true, length = -1)
    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    @Basic
    @Column(name = "DateCompleted", nullable = true)
    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId &&
                userId == report.userId &&
                Objects.equals(reportText, report.reportText) &&
                Objects.equals(dateCompleted, report.dateCompleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reportId, reportText, dateCompleted, userId);
    }
}
