package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserdepartmentunitPK implements Serializable {
    private int departmentunitId;
    private int userId;

    @Column(name = "departmentunit_id", nullable = false)
    @Id
    public int getDepartmentunitId() {
        return departmentunitId;
    }

    public void setDepartmentunitId(int departmentunitId) {
        this.departmentunitId = departmentunitId;
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
        UserdepartmentunitPK that = (UserdepartmentunitPK) o;
        return departmentunitId == that.departmentunitId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentunitId, userId);
    }
}
