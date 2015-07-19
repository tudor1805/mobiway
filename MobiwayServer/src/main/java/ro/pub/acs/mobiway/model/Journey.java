package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "journey")
public class Journey implements Serializable {
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
    @Column(name = "journey_name")
    private String journeyName;
    
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User idUser;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journeyId")
    private Collection<JourneyData> journeyDataCollection;

    public Journey() {
    }

    public Journey(Integer id) {
        this.id = id;
    }

    public Journey(Integer id, String jouneyName) {
        this.id = id;
        this.journeyName = jouneyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public Collection<JourneyData> getJourneyDataCollection() {
        return journeyDataCollection;
    }

    public void setJourneyDataCollection(Collection<JourneyData> journeyDataCollection) {
        this.journeyDataCollection = journeyDataCollection;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.model.Journey[ id=" + id + " ]";
    }
    
}
