package it.unipv.sfw.model.staff;

/**
 * Classe che rappresenta uno stratega, un tipo di membro dello staff.
 * Estende la classe {@link Staff}.
 */
public class Strategist extends Staff {

    private int timeLap = 0; // Tempo sul giro

    /**
     * Costruttore della classe Stratega.
     * @param id L'ID dello stratega.
     * @param pwd La password dello stratega.
     */
    public Strategist(String id, String pwd) {
        super(id, pwd);
    }

    /**
     * Restituisce il tipo di membro dello staff.
     * @return Il tipo di membro dello staff ({@link Staff.TypeController.STRATEGA}).
     */
    @Override
    public TypeRole getType() {
        return Staff.TypeRole.STRATEGIST;
    }

    /**
     * Restituisce il tempo sul giro.
     * @return Il tempo sul giro.
     */
    public int getTimeLap() {
        return timeLap;
    }

    /**
     * Imposta il tempo sul giro.
     * @param timeLap Il tempo sul giro da impostare.
     */
    public void setTimeLap(int timeLap) {
        this.timeLap = timeLap;
    }

}