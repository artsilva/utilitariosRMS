/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos;

import cl.rpro.utilitariorms.renombrado_archivos.helper.QueryHelper;
import cl.rpro.utilitariorms.renombrado_archivos.model.ConfiguracionB2B;
import cl.rpro.utilitariorms.renombrado_archivos.model.RetailerSeleccionados;
import cl.rpro.utilitariorms.utils.DBUtils;
import cl.rpro.utilitariorms.utils.SingletonRMSBean;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author Artsk
 */
@Named
public class Batchlet01ObtenerConfiguracion extends AbstractBatchlet {

    @Inject
    @BatchProperty
    private String jumbo;

    @Inject
    @BatchProperty
    private String unimarc;

    @Inject
    @BatchProperty
    private String lider;

    @Inject
    @BatchProperty
    private String tottus;

    private DataSource dataSource;

    private @Inject
    SingletonRMSBean rmsBean;

    @Override
    public String process() throws Exception {
        
        this.dataSource = QueryHelper.getDataSource();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;        
        
        try {
            Logger.getLogger(Batchlet01ObtenerConfiguracion.class.getName()).log(Level.INFO, "Obteniendo configuración de rutas de archivos.");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(QueryHelper.SELECT_CONFIG_RENOMBRADO);
            rs = ps.executeQuery();
            List<ConfiguracionB2B> listaConfig = new ArrayList<>();
            while (rs.next()) {
                ConfiguracionB2B config = new ConfiguracionB2B();
                config.setNombreRetailer(rs.getString("nombre_retailer"));
                config.setRutaZip(rs.getString("ruta_zip"));
                config.setRutaResp(rs.getString("ruta_zip_resp"));
                config.setRutaInput(rs.getString("ruta_input"));
                listaConfig.add(config);
            }            
            rmsBean.setProperty("configuracion", listaConfig);

            List<RetailerSeleccionados> retailerSeleccionados = new ArrayList<>();

            List<String> nombreRetailer = new ArrayList<>();
            nombreRetailer.add(jumbo);
            nombreRetailer.add(unimarc);
            nombreRetailer.add(lider);
            nombreRetailer.add(tottus);

            Field fld[] = Batchlet01ObtenerConfiguracion.class.getDeclaredFields();
            for (int i = 0; i < nombreRetailer.size(); i++) {
                if (nombreRetailer.get(i).contains("true")) {
                    Logger.getLogger(Batchlet01ObtenerConfiguracion.class.getName()).log(Level.INFO, fld[i].getName().toUpperCase());
                    RetailerSeleccionados retailers = new RetailerSeleccionados();
                    retailers.setNombreRetailer(fld[i].getName().toUpperCase());
                    retailers.setSeleccionado(Boolean.TRUE);
                    retailerSeleccionados.add(retailers);
                }
            }
            if (retailerSeleccionados.isEmpty()) {
                Logger.getLogger(Batchlet01ObtenerConfiguracion.class.getName()).log(Level.INFO, "Para ejecutar el proceso es necesario marcar un retailer al menos");
                return "FAILED";
            }
            rmsBean.setProperty("retailerSeleccionados", retailerSeleccionados);
            
        } catch (SQLException ex) {
            Logger.getLogger(Batchlet01ObtenerConfiguracion.class.getName()).log(Level.SEVERE, "Ocurrio un problema al obtener la configuración.", ex);
            return "FAILED";
        } catch (Exception ex) {
            Logger.getLogger(Batchlet01ObtenerConfiguracion.class.getName()).log(Level.SEVERE, "Ocurrio un problema al obtener la configuración.", ex);
            return "FAILED";
        } finally {
            DBUtils.limpiarRecursos(ps, rs, conn);
        }
        return "COMPLETED";
    }
}
