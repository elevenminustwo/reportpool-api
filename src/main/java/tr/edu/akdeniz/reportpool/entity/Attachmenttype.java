package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Attachmenttype {
    private int attachmentTypeId;
    private String filetype;

    @Id
    @Column(name = "AttachmentTypeID", nullable = false)
    public int getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(int attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    @Basic
    @Column(name = "Filetype", nullable = true, length = 255)
    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachmenttype that = (Attachmenttype) o;
        return attachmentTypeId == that.attachmentTypeId &&
                Objects.equals(filetype, that.filetype);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attachmentTypeId, filetype);
    }
}
