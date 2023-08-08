package com.serverless.dto;

import com.serverless.dao.Vehicle;

import java.io.Serializable;

public class VehicleDTO implements Serializable {

    private static final long serialVersionUID = 6340329078934274467L;

    private String name;

    private String type;

    private String cc;

    private String tyreBrand;

    public VehicleDTO() {
        super();
    }

    public VehicleDTO(String name, String type, String cc, String tyreBrand) {
        this();
        this.name = name;
        this.type = type;
        this.cc = cc;
        this.tyreBrand = tyreBrand;
    }

    public VehicleDTO(Vehicle vehicle) {
        this();
        this.name = vehicle.getName();
        this.type = vehicle.getType();
        this.cc = vehicle.getCc();
        this.tyreBrand = vehicle.getTyreBrand();
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
