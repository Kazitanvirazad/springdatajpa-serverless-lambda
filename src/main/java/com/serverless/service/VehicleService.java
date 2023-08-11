package com.serverless.service;

import com.serverless.dao.Vehicle;
import com.serverless.dto.VehicleDTO;
import com.serverless.repo.VehicleRepo;
import com.serverless.util.ResponseObject;
import com.serverless.util.VehicleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleRepo vehicleRepo;
    private VehicleUtil vehicleUtil;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo, VehicleUtil vehicleUtil) {
        this.vehicleRepo = vehicleRepo;
        this.vehicleUtil = vehicleUtil;
    }

    public List<VehicleDTO> getVehicles() {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(vehicles)) {
            vehicles.stream().forEach(vehicle -> {
                VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
                if (vehicleDTO != null)
                    vehicleDTOS.add(vehicleDTO);
            });

        }
        return vehicleDTOS;
    }

    public void addVehicle(VehicleDTO vehicle) {
        Vehicle vehicleDAO = vehicleUtil.createVehicleDAO(vehicle);
        vehicleRepo.save(vehicleDAO);
    }

    public boolean isVehicleExist(String vehicleName) {
        return vehicleRepo.existsById(vehicleName);
    }

    public VehicleDTO getVehicleById(String id) {
        VehicleDTO vehicleDTO = null;
        if (isVehicleExist(id)) {
            Optional<Vehicle> vehicleOPT = vehicleRepo.findById(id);
            if (!vehicleOPT.isEmpty()) {
                vehicleDTO = new VehicleDTO(vehicleOPT.get());
            }
        }
        return vehicleDTO;
    }

    public ResponseObject deleteVehicleById(String id) {
        boolean flag = true;
        String message = "";
        if (isVehicleExist(id)) {
            vehicleRepo.deleteById(id);
            if (!isVehicleExist(id)) {
                flag = false;
                message = id + " deleted successfully!";
            } else
                message = "Deletion failed!";
        } else
            message = "Vehicle doesn't exist!";
        return ResponseObject.builder().setError(flag).setMessage(message).build();
    }

    public ResponseObject updateVehicleById(String fieldName, String value, String vehicleName) throws IllegalAccessException {
        if (isVehicleExist(vehicleName)) {
            Optional<Vehicle> vehicle = vehicleRepo.findById(vehicleName);
            if (!vehicle.isEmpty()) {
                Vehicle v = vehicle.get();
                vehicleUtil.updateClassField(fieldName, value, v);
                return ResponseObject.builder().setError(false).setData(new VehicleDTO(vehicleRepo.save(v))).build();
            }
            return ResponseObject.builder().setError(true).setMessage("Vehicle update failed!").build();
        }
        return ResponseObject.builder().setError(true).setMessage("Vehicle does not exists!").build();
    }

    public ResponseObject getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepo.findAllByType(vehicleType);
        List<VehicleDTO> vehicleDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(vehicles)) {
            vehicles.stream().forEach(vehicle -> {
                vehicleDTOS.add(new VehicleDTO(vehicle));
            });
            return ResponseObject.builder().setError(false).setData(vehicleDTOS).build();
        } else {
            return ResponseObject.builder().setError(true)
                    .setMessage("Vehicles of type - " + vehicleType + " not found!")
                    .build();
        }
    }
}
