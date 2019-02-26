package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ReportPK implements Serializable {
    private int reportId;
    private int userId;

    @Column(name = "ReportID", nullable = false)
    @Id
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
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
        ReportPK reportPK = (ReportPK) o;
        return reportId == reportPK.reportId &&
                userId == reportPK.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(reportId, userId);
    }
}
