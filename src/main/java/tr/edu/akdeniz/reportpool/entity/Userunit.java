package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(UserunitPK.class)
public class Userunit {
    private int unitId;
    private int userId;

    @Id
    @Column(name = "unit_id", nullable = false)
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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
        Userunit userunit = (Userunit) o;
        return unitId == userunit.unitId &&
                userId == userunit.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(unitId, userId);
    }
}
