package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(AttachmentPK.class)
public class Attachment {
    private int attachmentId;
    private String path;
    private int reportId;
    private int userId;
    private int attachmenttypeId;

    @Id
    @Column(name = "AttachmentID", nullable = false)
    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "Path", nullable = true, length = 255)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Id
    @Column(name = "report_id", nullable = false)
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "attachmenttype_id", nullable = false)
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
        Attachment that = (Attachment) o;
        return attachmentId == that.attachmentId &&
                reportId == that.reportId &&
                userId == that.userId &&
                attachmenttypeId == that.attachmenttypeId &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attachmentId, path, reportId, userId, attachmenttypeId);
    }
}
