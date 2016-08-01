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
public class ConfiguracionB2B {
    
    private String nombreRetailer;
    private String rutaZip;
    private String rutaResp;
    private String rutaInput;

    public ConfiguracionB2B() {
    }

    public String getNombreRetailer() {
        return nombreRetailer;
    }

    public void setNombreRetailer(String nombreRetailer) {
        this.nombreRetailer = nombreRetailer;
    }

    public String getRutaZip() {
        return rutaZip;
    }

    public void setRutaZip(String rutaZip) {
        this.rutaZip = rutaZip;
    }

    public String getRutaResp() {
        return rutaResp;
    }

    public void setRutaResp(String rutaResp) {
        this.rutaResp = rutaResp;
    }

    public String getRutaInput() {
        return rutaInput;
    }

    public void setRutaInput(String rutaInput) {
        this.rutaInput = rutaInput;
    }
    
    
}
