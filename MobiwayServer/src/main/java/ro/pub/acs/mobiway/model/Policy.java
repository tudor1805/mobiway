package ro.pub.acs.mobiway.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;

@Entity
@Table(name = "policy")
public class Policy implements Serializable {
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

    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "policy_name")
    private String policyName;

    @Basic(optional = false)
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "policy_description")
    private String policyDescription;

    public Policy() {
    }

    public Policy(Integer id) {
        this.id = id;
    }

    public Policy(Integer id, String policyName, String policyDescription) {
        this.id = id;
        this.policyName = policyName;
        this.policyDescription = policyDescription;
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

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.model.Policy[ id=" + id + " ]";
    }
    
}
