package com.serverless.repo;

import com.serverless.dao.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, String> {
    List<Vehicle> findAllByType(String type);

}
