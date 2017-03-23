package org.c4sg.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_skill")
public class UserSkill implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Basic
    @Column(columnDefinition="int", nullable = false)
    private int order ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill=skill;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order=order;
    }



}
