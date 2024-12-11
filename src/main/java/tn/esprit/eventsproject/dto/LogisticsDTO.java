package tn.esprit.eventsproject.dto;

import lombok.Getter;

@Getter
public class LogisticsDTO {

    private int idLog;  // Use idLog to match entity field name
    private String description;
    private boolean reserve;
    private float prixUnit;
    private int quantite;

    // Default constructor
    public LogisticsDTO() {}

    // Constructor for convenience
    public LogisticsDTO(int idLog, String description, boolean reserve, float prixUnit, int quantite) {
        this.idLog = idLog;
        this.description = description;
        this.reserve = reserve;
        this.prixUnit = prixUnit;
        this.quantite = quantite;
    }
}
