package nju.cgm.sparkgraphxtest.model;

import java.io.Serializable;

/**
 * @author: Bright Chan
 * @date: 2020/11/20 20:25
 * @description: Actor
 */
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public Actor() {
    }

    public Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
