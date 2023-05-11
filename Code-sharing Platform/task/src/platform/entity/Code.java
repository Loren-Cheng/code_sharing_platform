package platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "Code")
@ToString
public class Code {
    @Id
    @JsonIgnore
    @Column(unique = true, updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;
    int time;
    int views;
    boolean timeRestricted;
    boolean viewsRestricted;

    public Code() {
    }

    public Code(String id, String code, LocalDateTime date, int time, int views, boolean timeRestricted, boolean viewsRestricted) {
        this.id = id;
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
        this.timeRestricted = timeRestricted;
        this.viewsRestricted = viewsRestricted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public boolean isViewsRestricted() {
        return viewsRestricted;
    }

    public void setViewsRestricted(boolean viewsRestricted) {
        this.viewsRestricted = viewsRestricted;
    }
}
