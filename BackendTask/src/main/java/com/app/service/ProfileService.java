package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ProfileDao;
import com.app.entities.Profile;

@Service // Marks this class as a Spring service component, indicating that it contains business logic
public class ProfileService  {

    @Autowired
    private ProfileDao profileRepository;  // Inject the ProfileRepository (DAO) to interact with the database

    // Create a new profile
    public Profile createProfile(Profile profile) {
        // Saves the profile to the database using the ProfileRepository's save method
        return profileRepository.save(profile);  
    }

    // Update an existing profile by ID
    public Profile updateProfile(Long id, Profile profileDetails) {
        // Fetches the profile by ID, throws exception if not found
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found!"));

        // Update the fields of the fetched profile with the new details
        profile.setFirstName(profileDetails.getFirstName());
        profile.setLastName(profileDetails.getLastName());
        profile.setEmail(profileDetails.getEmail());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());

        // Saves the updated profile back to the database
        return profileRepository.save(profile);  
    }

    // Retrieve all profiles from the database
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();  // Finds and returns all profiles
    }

    // Delete a profile by ID
    public void deleteProfile(Long id) {
        // Fetches the profile by ID, throws exception if not found
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found!"));

        // Deletes the profile from the database
        profileRepository.delete(profile);  
    }

    // Retrieve a profile by ID
    public Optional<Profile> getProfileById(Long id) {
        // Finds and returns a profile by ID, wrapped in Optional
        return profileRepository.findById(id);  
    }
}
