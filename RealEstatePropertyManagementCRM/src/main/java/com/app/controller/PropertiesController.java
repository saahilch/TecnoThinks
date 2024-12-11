package com.app.controller;

import com.app.entities.Properties;
import com.app.entities.User;
import com.app.services.PropertiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertiesController {

	private final PropertiesService propertiesService;

	public PropertiesController(PropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	@PostMapping
	public ResponseEntity<Properties> addProperty(@RequestBody Properties property,
			@RequestAttribute("user") User manager) {
		property.setManager(manager); // Set the manager from authenticated user
		return ResponseEntity.ok(propertiesService.addProperty(property));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Properties> updateProperty(@PathVariable Long id, @RequestBody Properties property) {
		return ResponseEntity.ok(propertiesService.updateProperty(id, property));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
		propertiesService.deleteProperty(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Properties> getPropertyById(@PathVariable Long id) {
		return ResponseEntity.ok(propertiesService.getPropertyById(id));
	}

	@GetMapping
	public ResponseEntity<List<Properties>> getAllProperties() {
		return ResponseEntity.ok(propertiesService.getAllProperties());
	}

	@GetMapping("/manager/{managerId}")
	public ResponseEntity<List<Properties>> getPropertiesByManager(@PathVariable Long managerId) {
		return ResponseEntity.ok(propertiesService.getPropertiesByManager(managerId));
	}
}
