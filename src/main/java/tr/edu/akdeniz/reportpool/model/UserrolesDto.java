package tr.edu.akdeniz.reportpool.model;

public class UserrolesDto {
    private int userId;
    private int roleId;

    public UserrolesDto() {
    }
    public UserrolesDto(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
