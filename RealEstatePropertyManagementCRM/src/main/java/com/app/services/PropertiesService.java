package com.app.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.app.entities.Properties;
import com.app.repositiory.PropertiesRepository;

@Service
public class PropertiesService {
	private final PropertiesRepository propertiesRepository;

	public PropertiesService(PropertiesRepository propertiesRepository) {
		this.propertiesRepository = propertiesRepository;
	}

	public Properties addProperty(Properties property) {
		return propertiesRepository.save(property);
	}

	public Properties updateProperty(Long id, Properties updatedProperty) {
		Properties existingProperty = propertiesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Property not found with id " + id));

		existingProperty.setName(updatedProperty.getName());
		existingProperty.setLocation(updatedProperty.getLocation());
		existingProperty.setStatus(updatedProperty.getStatus());

		return propertiesRepository.save(existingProperty);
	}

	public void deleteProperty(Long id) {
		if (!propertiesRepository.existsById(id)) {
			throw new EntityNotFoundException("Property not found with id " + id);
		}
		propertiesRepository.deleteById(id);
	}

	public Properties getPropertyById(Long id) {
		return propertiesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Property not found with id " + id));
	}

	public List<Properties> getAllProperties() {
		return propertiesRepository.findAll();
	}

	public List<Properties> getPropertiesByManager(Long managerId) {
		return propertiesRepository.findByManagerId(managerId);
	}
}
