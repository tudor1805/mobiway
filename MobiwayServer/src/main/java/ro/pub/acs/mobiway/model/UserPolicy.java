package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "user_policy")
public class UserPolicy implements Serializable {
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
    @Column(name = "app_id")
    private String appId;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User idUser;

    @JoinColumn(name = "id_policy", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Policy idPolicy;

    public UserPolicy() {
    }

    public UserPolicy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Policy getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(Policy idPolicy) {
        this.idPolicy = idPolicy;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.model.UserPolicy[ id=" + id + " ]";
    }

}
