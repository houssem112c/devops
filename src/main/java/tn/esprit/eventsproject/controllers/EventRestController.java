package tn.esprit.eventsproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.eventsproject.dto.EventDTO;  // Import the EventDTO
import tn.esprit.eventsproject.dto.LogisticsDTO;
import tn.esprit.eventsproject.dto.ParticipantDTO;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("event")
@RestController
public class EventRestController {

    private final IEventServices eventServices;

    @PostMapping("/addPart")
    public ParticipantDTO addParticipant(@RequestBody ParticipantDTO participantDTO) {
        // Convert ParticipantDTO to Participant entity
        Participant participant = new Participant();
        participant.setNom(participantDTO.getNom());
        participant.setPrenom(participantDTO.getPrenom());
        participant.setTache(participantDTO.getTache());

        // Add Participant using the service
        Participant savedParticipant = eventServices.addParticipant(participant);

        // Convert the saved entity back to DTO
        Set<Integer> eventIds = savedParticipant.getEvents().stream()
                .map(Event::getIdEvent)
                .collect(Collectors.toSet());

        return new ParticipantDTO(
                savedParticipant.getIdPart(),
                savedParticipant.getNom(),
                savedParticipant.getPrenom(),
                savedParticipant.getTache(),
                eventIds
        );
    }

    @PostMapping("/addEvent/{id}")
    public EventDTO addEventPart(@RequestBody EventDTO eventDTO, @PathVariable("id") int idPart) {
        // Convert EventDTO to Event entity
        Event event = new Event();
        event.setDescription(eventDTO.getDescription());
        event.setDateDebut(eventDTO.getDateDebut());
        event.setDateFin(eventDTO.getDateFin());
        event.setCout(eventDTO.getCout());

        // Add Event using the service
        Event savedEvent = eventServices.addAffectEvenParticipant(event, idPart);

        // Convert the saved entity back to DTO
        Set<Integer> participantIds = savedEvent.getParticipants().stream()
                .map(Participant::getIdPart)
                .collect(Collectors.toSet());

        Set<Integer> logisticsIds = savedEvent.getLogistics().stream()
                .map(Logistics::getIdLog)
                .collect(Collectors.toSet());

        return new EventDTO(
                savedEvent.getIdEvent(),
                savedEvent.getDescription(),
                savedEvent.getDateDebut(),
                savedEvent.getDateFin(),
                savedEvent.getCout(),
                participantIds,
                logisticsIds
        );
    }

    @PostMapping("/addEvent")
    public EventDTO addEvent(@RequestBody EventDTO eventDTO) {
        // Convert EventDTO to Event entity
        Event event = new Event();
        event.setDescription(eventDTO.getDescription());
        event.setDateDebut(eventDTO.getDateDebut());
        event.setDateFin(eventDTO.getDateFin());
        event.setCout(eventDTO.getCout());

        // Add Event using the service
        Event savedEvent = eventServices.addAffectEvenParticipant(event);

        // Convert the saved entity back to DTO
        Set<Integer> participantIds = savedEvent.getParticipants().stream()
                .map(Participant::getIdPart)
                .collect(Collectors.toSet());

        Set<Integer> logisticsIds = savedEvent.getLogistics().stream()
                .map(Logistics::getIdLog)
                .collect(Collectors.toSet());

        return new EventDTO(
                savedEvent.getIdEvent(),
                savedEvent.getDescription(),
                savedEvent.getDateDebut(),
                savedEvent.getDateFin(),
                savedEvent.getCout(),
                participantIds,
                logisticsIds
        );
    }
    @PutMapping("/addAffectLog/{description}")
    public LogisticsDTO addAffectLog(@RequestBody LogisticsDTO logisticsDTO, @PathVariable("description") String descriptionEvent) {
        // Convert LogisticsDTO to Logistics entity
        Logistics logistics = new Logistics();
        logistics.setDescription(logisticsDTO.getDescription());
        logistics.setReserve(logisticsDTO.isReserve());
        logistics.setPrixUnit(logisticsDTO.getPrixUnit());
        logistics.setQuantite(logisticsDTO.getQuantite());

        // Add Logistics using the service
        Logistics savedLogistics = eventServices.addAffectLog(logistics, descriptionEvent);

        // Convert the saved entity back to DTO
        return new LogisticsDTO(
                savedLogistics.getIdLog(),
                savedLogistics.getDescription(),
                savedLogistics.isReserve(),
                savedLogistics.getPrixUnit(),
                savedLogistics.getQuantite()
        );
    }

    @GetMapping("/getLogs/{d1}/{d2}")
    public List<LogisticsDTO> getLogistiquesDates(@PathVariable("d1") LocalDate datedebut, @PathVariable("d2") LocalDate datefin) {
        List<Logistics> logisticsList = eventServices.getLogisticsDates(datedebut, datefin);
        return logisticsList.stream()
                .map(logistics -> new LogisticsDTO(
                        logistics.getIdLog(),
                        logistics.getDescription(),
                        logistics.isReserve(),
                        logistics.getPrixUnit(),
                        logistics.getQuantite()
                ))
                .collect(Collectors.toList());
    }
}