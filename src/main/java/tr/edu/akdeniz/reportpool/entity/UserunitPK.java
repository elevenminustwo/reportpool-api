package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class UserunitPK implements Serializable {
    private int unitId;
    private int userId;

    @Column(name = "unit_id", nullable = false)
    @Id
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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
        UserunitPK that = (UserunitPK) o;
        return unitId == that.unitId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(unitId, userId);
    }
}
