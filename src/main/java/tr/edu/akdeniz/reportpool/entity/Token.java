package tr.edu.akdeniz.reportpool.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "token")
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private long tokenId;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "created_date", nullable = false)
    private long createdDate;
    @Column(name = "user_id", nullable = false)
    private int userId;

    public Token() {

    }

    public Token(int userId) {
        this.userId = userId;
        createdDate = System.currentTimeMillis();
        token = UUID.randomUUID().toString();
    }


    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
