package data.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class ProductGroup {

    private UUID id;
    private UUID parentId;
    private String name;
    private String description;
    private String slug;
    private String imageUrl;
    private Date deletedAt;

    public static ProductGroup fromResultSet(ResultSet rs) throws SQLException {
        ProductGroup pg = new ProductGroup();
        pg.setId(UUID.fromString(rs.getString("pg_id")));
        String parentId = rs.getString("pg_parent_id");
        if(parentId != null) {
            pg.setParentId(UUID.fromString(parentId));
        }
        pg.setName(rs.getString("pg_name"));
        pg.setDescription(rs.getString("pg_description"));
        pg.setSlug(rs.getString("pg_slug"));
        pg.setImageUrl(rs.getString("pg_image_url"));
        Timestamp timestamp;
        timestamp = rs.getTimestamp("pg_deleted_at");
        if(timestamp != null) {
            pg.setDeletedAt(new Date(timestamp.getTime()));
        }

        return pg;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}
