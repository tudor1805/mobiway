package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "traffic_events")
public class TrafficEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;

    public TrafficEvent() {
    }

    public TrafficEvent(Integer id) {
        this.id = id;
    }

    public TrafficEvent(Integer id, String eventName, String eventDescription) {
        this.id = id;
        this.name = eventName;
        this.description = eventDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String eventName) {
        this.name = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String eventDescription) {
        this.description = eventDescription;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.model.TrafficEvent[ id=" + id + " ]";
    }

}
