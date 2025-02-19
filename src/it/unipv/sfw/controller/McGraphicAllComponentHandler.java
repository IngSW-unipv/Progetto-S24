package it.unipv.sfw.controller;

import java.util.Set;

import it.unipv.sfw.model.component.Components;
import it.unipv.sfw.model.staff.Session;
import it.unipv.sfw.view.McGraphicAllComponentView;

/**
 * Gestisce la visualizzazione grafica di tutti i componenti.
 * Recupera i componenti dal sistema e li visualizza nella finestra corrispondente.
 */
public class McGraphicAllComponentHandler {
    
    private Set<Components> components = fetchComponent();
    private McGraphicAllComponentView gv = new McGraphicAllComponentView(components);

    /**
     * Mostra la finestra con la lista di tutti i componenti.
     */
    public void showWindow() {
        gv.show();
    }
    
    /**
     * Recupera l'insieme dei componenti disponibili dal sistema.
     * 
     * @return Un insieme di oggetti {@link Components} disponibili.
     */
    private Set<Components> fetchComponent(){
        return Session.getIstance().getV().getComponent();
    }
}
