package com.brainbridge.dto;

public class ProfileUpdateRequest {

    private String name;
    private String bio;
    private String skills;
    private String learningGoals;
    private String interests;
    private String collaborationPreferences;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getLearningGoals() { return learningGoals; }
    public void setLearningGoals(String learningGoals) { this.learningGoals = learningGoals; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getCollaborationPreferences() { return collaborationPreferences; }
    public void setCollaborationPreferences(String collaborationPreferences) {
        this.collaborationPreferences = collaborationPreferences;
    }
}
