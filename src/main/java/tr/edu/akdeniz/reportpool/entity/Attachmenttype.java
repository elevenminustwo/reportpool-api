package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Attachmenttype {
    private int attachmenttypeId;
    private String type;

    @Id
    @Column(name = "AttachmenttypeID", nullable = false)
    public int getAttachmenttypeId() {
        return attachmenttypeId;
    }

    public void setAttachmenttypeId(int attachmenttypeId) {
        this.attachmenttypeId = attachmenttypeId;
    }

    @Basic
    @Column(name = "Type", nullable = true, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachmenttype that = (Attachmenttype) o;
        return attachmenttypeId == that.attachmenttypeId &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(attachmenttypeId, type);
    }
}
