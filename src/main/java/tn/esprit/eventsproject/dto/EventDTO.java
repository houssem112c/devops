package tn.esprit.eventsproject.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class EventDTO {
    private int idEvent;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private float cout;
    private Set<Integer> participantIds;  // Set of participant IDs
    private Set<Integer> logisticsIds;    // Set of logistics IDs

    // Default constructor
    public EventDTO() {}

    // Constructor for convenience
    public EventDTO(int idEvent, String description, LocalDate dateDebut, LocalDate dateFin, float cout, Set<Integer> participantIds, Set<Integer> logisticsIds) {
        this.idEvent = idEvent;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cout = cout;
        this.participantIds = participantIds;
        this.logisticsIds = logisticsIds;
    }
}
