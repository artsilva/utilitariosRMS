/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos.model;

/**
 *
 * @author Artsk
 */
public class RetailerSeleccionados {
    
    private String nombreRetailer;
    private boolean seleccionado;

    public RetailerSeleccionados() {
    }

    public String getNombreRetailer() {
        return nombreRetailer;
    }

    public void setNombreRetailer(String nombreRetailer) {
        this.nombreRetailer = nombreRetailer;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
    
}
