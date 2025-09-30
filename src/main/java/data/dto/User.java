package data.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private Date birthdate;
    private Date registeredAt;
    private Date deletedAt;

    public static User fromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("user_id")));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        Timestamp timestamp;
        timestamp = rs.getTimestamp("birthdate");
        if (timestamp != null) {
            user.setBirthdate(new Date(timestamp.getTime()));
        }
        timestamp = rs.getTimestamp("registered_at");
        user.setRegisteredAt(new Date(timestamp.getTime()));
        timestamp = rs.getTimestamp("deleted_at");
        if (timestamp != null) {
            user.setDeletedAt(new Date(timestamp.getTime()));
        }
        return user;
    }



    public Date getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }



    public Date getDeletedAt()
    {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt)
    {
        this.deletedAt = deletedAt;
    }



    public Date getRegisteredAt()
    {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt)
    {
        this.registeredAt = registeredAt;
    }


    public UUID getId()
    {
        return id;
    }

    public User setId(UUID id)
    {
        this.id = id;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public User setName(String name)
    {
        this.name = name;
        return this;
    }

    public String getEmail()
    {
        return email;
    }

    public User setEmail(String email)
    {
        this.email = email;
        return this;
    }


}

