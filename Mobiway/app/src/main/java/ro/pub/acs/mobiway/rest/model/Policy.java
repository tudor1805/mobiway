package ro.pub.acs.mobiway.rest.model;

import java.util.Date;

public class Policy {

    private Integer id;
    private String appId;
    private String policyName;
    private String policyDescription;

    public Policy() {
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

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    @Override
    public String toString() {
        return "ro.pub.acs.mobiway.rest.model.Policy[ idUser=" + id + " ]";
    }

}