package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(UserrolesPK.class)
public class Userroles {
    private int userId;
    private int roleId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Userroles userroles = (Userroles) o;
        return userId == userroles.userId &&
                roleId == userroles.roleId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, roleId);
    }
}
