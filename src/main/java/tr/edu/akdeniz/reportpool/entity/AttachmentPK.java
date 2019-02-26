package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AttachmentPK implements Serializable {
    private int attachmentId;
    private int reportId;
    private int userId;
    private int attachmenttypeId;

    @Column(name = "AttachmentID", nullable = false)
    @Id
    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Column(name = "report_id", nullable = false)
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

    @Column(name = "attachmenttype_id", nullable = false)
    @Id
    public int getAttachmenttypeId() {
        return attachmenttypeId;
    }

    public void setAttachmenttypeId(int attachmenttypeId) {
        this.attachmenttypeId = attachmenttypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentPK that = (AttachmentPK) o;
        return attachmentId == that.attachmentId &&
                reportId == that.reportId &&
                userId == that.userId &&
                attachmenttypeId == that.attachmenttypeId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(attachmentId, reportId, userId, attachmenttypeId);
    }
}
