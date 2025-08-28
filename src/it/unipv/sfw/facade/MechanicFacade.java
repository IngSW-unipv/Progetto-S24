package it.unipv.sfw.facade;

import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;

public interface MechanicFacade {

    // === VEHICLE / PILOT ===
    void assignVehicleToMechanicAndPilot(
            String mechanicId,
            String pilotId,
            String vehicleMsnUpper
    ) throws PilotNotFoundException, VehicleNotFoundException;

    void linkPilotToVehicle(
            String mechanicId,
            String vehicleMsnUpper,
            String pilotId,
            String pilotNameUpper,
            String pilotSurnameUpper,
            String pilotNumber
    ) throws PilotNotFoundException;

    void unlinkPilotFromVehicle(String mechanicId, String pilotId);

    // === COMPONENTS ===
    AddComponentResult addComponent(
            String mechanicId,
            String vehicleMsnUpper,
            int componentId,
            String componentNameUpper,
            String declaredStatusUpper,  // "NEW"/"USED"/"WORN"
            Integer wearFromModelOrNull
    ) throws ComponentNotFoundException;

    void removeComponent(
            String mechanicId,
            String vehicleMsnUpper,
            int componentId,
            String componentNameUpper
    ) throws ComponentNotFoundException;

    // === REQUESTS ===
    void createComponentRequest(
            String mechanicId,
            String staffIdTyped,     // quello digitato in UI;
            String componentId,
            String vehicleMsnUpper,
            String descriptionUpper
    ) throws WrongIDException, ComponentNotFoundException,
             WrongRequestException, VehicleNotFoundException;

    // logging
    void log(String mechanicId, String description);
}
