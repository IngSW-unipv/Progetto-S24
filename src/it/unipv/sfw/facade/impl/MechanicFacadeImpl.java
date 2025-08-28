package it.unipv.sfw.facade.impl;

import java.util.Objects;

import it.unipv.sfw.dao.interfacedao.IMechanicDAO;
import it.unipv.sfw.exceptions.ComponentNotFoundException;
import it.unipv.sfw.exceptions.PilotNotFoundException;
import it.unipv.sfw.exceptions.VehicleNotFoundException;
import it.unipv.sfw.exceptions.WrongIDException;
import it.unipv.sfw.exceptions.WrongRequestException;
import it.unipv.sfw.facade.AddComponentOutcome;
import it.unipv.sfw.facade.AddComponentResult;
import it.unipv.sfw.facade.MechanicFacade;

public class MechanicFacadeImpl implements MechanicFacade {

    private final IMechanicDAO md;

    public MechanicFacadeImpl(IMechanicDAO mechanicDao) {
        this.md = Objects.requireNonNull(mechanicDao);
    }

    // === VEHICLE / PILOT ===

    @Override
    public void assignVehicleToMechanicAndPilot(String mechanicId,
                                                String pilotId,
                                                String vehicleMsnUpper)
            throws PilotNotFoundException, VehicleNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();

        md.checkPilot(pilotId);
        md.checkVehicle(msn);

        md.insertPilotOnVehicle(pilotId, msn);
        md.insertMeccOnVehicle(msn, mechanicId);

        md.insertLogEvent(mechanicId, "INSERT VEHICLE : " + msn);
    }

    @Override
    public void linkPilotToVehicle(String mechanicId,
                                   String vehicleMsnUpper,
                                   String pilotId,
                                   String pilotNameUpper,
                                   String pilotSurnameUpper,
                                   String pilotNumber)
            throws PilotNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = pilotNameUpper == null ? null : pilotNameUpper.toUpperCase();
        String surname = pilotSurnameUpper == null ? null : pilotSurnameUpper.toUpperCase();

        md.selectP(pilotId, name, surname, pilotNumber);
        md.insertPilotOnVehicle(pilotId, msn);
        md.insertLogEvent(mechanicId, "INSERT ID PILOT : " + pilotId);
    }

    @Override
    public void unlinkPilotFromVehicle(String mechanicId, String pilotId) {
        md.removePilot(pilotId);
        md.insertLogEvent(mechanicId, "REMOVE PILOT : " + pilotId);
    }

    // === COMPONENTS ===

    @Override
    public AddComponentResult addComponent(String mechanicId,
                                           String vehicleMsnUpper,
                                           int componentId,
                                           String componentNameUpper,
                                           String declaredStatusUpper,
                                           Integer wearFromModelOrNull)
            throws ComponentNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = componentNameUpper == null ? null : componentNameUpper.toUpperCase();
        String status = declaredStatusUpper == null ? null : declaredStatusUpper.toUpperCase();

        if (msn == null || msn.isBlank()
                || name == null || name.isBlank()
                || status == null || status.isBlank()) {
            return new AddComponentResult(AddComponentOutcome.INVALID_INPUT);
        }

        md.checkCompo(String.valueOf(componentId), name, status);

        if (wearFromModelOrNull != null) {
            boolean ok = md.insertComponent(String.valueOf(componentId), msn);
            if (ok) {
                md.updateWear(wearFromModelOrNull, String.valueOf(componentId));
            }
            md.insertLogEvent(mechanicId, "INSERT COMPONENT ID: " + componentId);
            return new AddComponentResult(AddComponentOutcome.INSERTED_OK);
        }

        if ("WORN".equalsIgnoreCase(status)) {
            md.insertLogEvent(mechanicId, "REQUEST REPLACEMENT SUGGESTED FOR COMPONENT ID: " + componentId);
            return new AddComponentResult(AddComponentOutcome.NEEDS_REPLACEMENT);
        }

        return new AddComponentResult(AddComponentOutcome.INVALID_INPUT);
    }

    @Override
    public void removeComponent(String mechanicId,
                                String vehicleMsnUpper,
                                int componentId,
                                String componentNameUpper)
            throws ComponentNotFoundException {

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String name = componentNameUpper == null ? null : componentNameUpper.toUpperCase();

        md.checkCompo(String.valueOf(componentId), name, "USED");
        md.removeComponent(String.valueOf(componentId), msn);
        md.insertLogEvent(mechanicId, "REMOVE COMPONENT ID: " + componentId);
    }

    // === REQUESTS ===

    @Override
    public void createComponentRequest(String mechanicId,
                                       String staffIdTyped,
                                       String componentId,
                                       String vehicleMsnUpper,
                                       String descriptionUpper)
            throws WrongIDException, ComponentNotFoundException,
                   WrongRequestException, VehicleNotFoundException {

        if (mechanicId == null || !mechanicId.equals(staffIdTyped)) {
            throw new WrongIDException();
        }

        String msn = vehicleMsnUpper == null ? null : vehicleMsnUpper.toUpperCase();
        String desc = descriptionUpper == null ? null : descriptionUpper.toUpperCase();
        String staffUp = staffIdTyped.toUpperCase();

        md.checkIdCompo(componentId);
        md.checkRequest(componentId);

        if (msn == null || msn.isBlank()) {
            throw new VehicleNotFoundException("Missing or empty MSN");
        }
        md.checkVehicle(msn);

        md.insertRequest(desc, staffUp, componentId, msn);
        md.insertLogEvent(mechanicId, "INSERT REQUEST FOR COMPONENT: " + componentId);
    }

    @Override
    public void log(String mechanicId, String description) {
        md.insertLogEvent(mechanicId, description);
    }
}
