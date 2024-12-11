package com.app.repositiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Properties;

public interface PropertiesRepository extends JpaRepository<Properties, Long> {
	List<Properties> findByManagerId(Long managerId);
}
