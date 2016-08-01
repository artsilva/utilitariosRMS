/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos;

import java.io.Serializable;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Artsk
 */
@Named(value = "renombradoArchivosMB")
@SessionScoped
public class RenombradoArchivosMB implements Serializable{
    private boolean jumbo;
    private boolean unimarc;
    private boolean lider;
    private boolean tottus;
    private String ultimaIdEjecutada;
    
    public RenombradoArchivosMB() {
    }

    @PostConstruct
    private void init() {
        System.out.println("postconstructwww");
    }
    
    public String ejecutarRenombradoArchivosB2B() {
        JobOperator jo = BatchRuntime.getJobOperator();
        Properties props = new Properties();
        props.setProperty("jumbo", String.valueOf(jumbo));
        props.setProperty("unimarc", String.valueOf(unimarc));
        props.setProperty("lider", String.valueOf(lider));
        props.setProperty("tottus", String.valueOf(tottus));
        long jid = jo.start("renombrado_archivos_job", props);
        this.ultimaIdEjecutada = String.valueOf(jid);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Ha comenzado el proceso de renombrado de Archivos B2B", "Espere un momento..."));
        return null;
    }
    
    public boolean isJumbo() {
        return jumbo;
    }

    public void setJumbo(boolean jumbo) {
        this.jumbo = jumbo;
    }

    public boolean isUnimarc() {
        return unimarc;
    }

    public void setUnimarc(boolean unimarc) {
        this.unimarc = unimarc;
    }

    public boolean isLider() {
        return lider;
    }

    public void setLider(boolean lider) {
        this.lider = lider;
    }

    public boolean isTottus() {
        return tottus;
    }

    public void setTottus(boolean tottus) {
        this.tottus = tottus;
    }

    public String getUltimaIdEjecutada() {
        return ultimaIdEjecutada;
    }

    public void setUltimaIdEjecutada(String ultimaIdEjecutada) {
        this.ultimaIdEjecutada = ultimaIdEjecutada;
    }

}
