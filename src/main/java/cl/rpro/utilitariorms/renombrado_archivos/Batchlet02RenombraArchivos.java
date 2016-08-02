/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos;

import cl.rpro.utilitariorms.renombrado_archivos.daos.RenombradoArchivosDAO;
import cl.rpro.utilitariorms.renombrado_archivos.model.ConfiguracionB2B;
import cl.rpro.utilitariorms.renombrado_archivos.model.RetailerSeleccionados;
import cl.rpro.utilitariorms.utils.SingletonRMSBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.AbstractBatchlet;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Artsk
 */
@Named
public class Batchlet02RenombraArchivos extends AbstractBatchlet {

    private @Inject
    SingletonRMSBean rmsBean;

    private @Inject
    RenombradoArchivosDAO archivoDAO;

    @Override
    public String process() throws Exception {
        
        List<ConfiguracionB2B> configuraciones = (List<ConfiguracionB2B>) rmsBean.getProperty("configuracion");
        List<RetailerSeleccionados> retailerSeleccionados = (List<RetailerSeleccionados>) rmsBean.getProperty("retailerSeleccionados");

        try {
            for (RetailerSeleccionados retailerSeleccionado : retailerSeleccionados) {
                if (retailerSeleccionado.isSeleccionado()) {
                    for (ConfiguracionB2B configuracion : configuraciones) {
                        if (retailerSeleccionado.getNombreRetailer().equals(configuracion.getNombreRetailer())) {
                            Logger.getLogger(Batchlet02RenombraArchivos.class.getName()).log(Level.INFO, "");
                            Logger.getLogger(Batchlet02RenombraArchivos.class.getName()).log(Level.INFO, "Retailer ejecutado: {0}", configuracion.getNombreRetailer());
                            
                            archivoDAO.validaRutaResp(configuracion);
                            
                            archivoDAO.descomprimeArchivos(configuracion);                            
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Batchlet02RenombraArchivos.class.getName()).log(Level.WARNING, "Se encontro un error.", ex);
        }
        return "COMPLETED";
    }
    
}
