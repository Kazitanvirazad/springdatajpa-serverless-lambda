package com.serverless.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicle_name")
    private String name;

    @Column(name = "vehicle_type")
    private String type;

    @Column(name = "cc")
    private String cc;

    @Column(name = "tyre_brand")
    private String tyreBrand;

    public Vehicle() {
        super();
    }

    public Vehicle(String name, String type, String cc, String tyreBrand) {
        this();
        this.name = name;
        this.type = type;
        this.cc = cc;
        this.tyreBrand = tyreBrand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTyreBrand() {
        return tyreBrand;
    }

    public void setTyreBrand(String tyreBrand) {
        this.tyreBrand = tyreBrand;
    }
}
