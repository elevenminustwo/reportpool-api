package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Attachment {
    private int attachmentId;
    private String file;
    private int attachmenttypeId;
    private int reportId;

    @Id
    @Column(name = "AttachmentID", nullable = false)
    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Basic
    @Column(name = "file", nullable = true, length = 255)
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Basic
    @Column(name = "attachmenttype_id", nullable = false)
    public int getAttachmenttypeId() {
        return attachmenttypeId;
    }

    public void setAttachmenttypeId(int attachmenttypeId) {
        this.attachmenttypeId = attachmenttypeId;
    }

    @Basic
    @Column(name = "report_id", nullable = false)
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return attachmentId == that.attachmentId &&
                attachmenttypeId == that.attachmenttypeId &&
                reportId == that.reportId &&
                Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attachmentId, file, attachmenttypeId, reportId);
    }
}
