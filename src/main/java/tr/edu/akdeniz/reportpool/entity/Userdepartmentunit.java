package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(UserdepartmentunitPK.class)
public class Userdepartmentunit {
    private int departmentunitId;
    private int userId;

    @Id
    @Column(name = "departmentunit_id", nullable = false)
    public int getDepartmentunitId() {
        return departmentunitId;
    }

    public void setDepartmentunitId(int departmentunitId) {
        this.departmentunitId = departmentunitId;
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
        Userdepartmentunit that = (Userdepartmentunit) o;
        return departmentunitId == that.departmentunitId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(departmentunitId, userId);
    }
}
