package it.unipv.sfw.facade;

public class AddComponentResult {
    private final AddComponentOutcome outcome;

    public AddComponentResult(AddComponentOutcome outcome) {
        this.outcome = outcome;
    }

    public AddComponentOutcome getOutcome() {
        return outcome;
    }
}
