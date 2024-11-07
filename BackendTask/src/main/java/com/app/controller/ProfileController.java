package com.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.entities.Profile;
import com.app.service.ProfileService;

@RestController // Marks this class as a controller that handles HTTP requests and returns JSON responses
@RequestMapping("/profile") // Sets the base URL for all endpoints in this controller
public class ProfileController {

	@Autowired
	@Lazy // Lazy initialization to inject the ProfileService when it is first needed
	private ProfileService myProfileService; // Injects the ProfileService to handle business logic

	// Get all profiles
	@GetMapping // Handles GET requests at the base URL (/profile)
	public List<Profile> getAllProfiles() {
		// Fetches all profiles from the service
		return myProfileService.getAllProfiles();
	}

	// Get a profile by ID
	@GetMapping("{id}") // Handles GET requests for a specific profile by its ID (/profile/{id})
	public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
		// Fetches the profile based on the provided ID
		Optional<Profile> profile = myProfileService.getProfileById(id);
		// If the profile is found, return it with an HTTP OK status, else return 404 (Not Found)
		return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Create a new profile
	@PostMapping // Handles POST requests for creating a new profile (/profile)
	public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
		// Calls the service to create a new profile
		Profile savedProfile = myProfileService.createProfile(profile);
		// Returns the created profile with an HTTP OK status
		return ResponseEntity.ok(savedProfile);
	}

	// Update an existing profile
	@PutMapping("{id}") // Handles PUT requests to update a profile by its ID (/profile/{id})
	public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profileDetails) {
		// Updates the profile based on the ID and provided details
		Profile updatedProfile = myProfileService.updateProfile(id, profileDetails);
		// Returns the updated profile with an HTTP OK status
		return ResponseEntity.ok(updatedProfile);
	}

	// Delete a profile by ID
	@DeleteMapping("{id}") // Handles DELETE requests to remove a profile by its ID (/profile/{id})
	public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
		// Calls the service to delete the profile by its ID
		myProfileService.deleteProfile(id);
		System.out.println("Profile Deleted Successfully....");
		// Returns a no-content (204) HTTP response to indicate successful deletion
		return ResponseEntity.noContent().build();
	}
}
