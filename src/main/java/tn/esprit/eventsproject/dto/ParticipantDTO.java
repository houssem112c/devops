package tn.esprit.eventsproject.dto;

import lombok.Getter;
import tn.esprit.eventsproject.entities.Tache;

import java.util.Set;

@Getter
public class ParticipantDTO {
    // Getters and Setters
    private int idPart;
    private String nom;
    private String prenom;
    private Tache tache;
    private Set<Integer> eventIds; // Set of event IDs instead of full Event objects

    // Default constructor
    public ParticipantDTO() {}

    // Constructor for convenience
    public ParticipantDTO(int idPart, String nom, String prenom, Tache tache, Set<Integer> eventIds) {
        this.idPart = idPart;
        this.nom = nom;
        this.prenom = prenom;
        this.tache = tache;
        this.eventIds = eventIds;
    }

    public void setIdPart(int idPart) {
        this.idPart = idPart;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public void setEventIds(Set<Integer> eventIds) {
        this.eventIds = eventIds;
    }
}
