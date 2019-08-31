package com.sxt.po;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties(value={"rolePrivileges"})
public class Privilege {
    private int id;
    private String name;
    private String privilege;
    private String method;

    private List<RolePrivileges> rolePrivileges;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST, mappedBy="privilege_id")
    public List<RolePrivileges> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(List<RolePrivileges> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }
}
