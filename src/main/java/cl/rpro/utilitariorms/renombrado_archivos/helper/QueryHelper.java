/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos.helper;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author Artsk
 */
public class QueryHelper {
    
    public static String SELECT_CONFIG_RENOMBRADO = "SELECT nombre_retailer, ruta_zip, ruta_zip_resp, ruta_input FROM rpro_config.rms_configuracion_renombrado_b2b WHERE habilitado = 'true';";

    public static DataSource getDataSource() throws Exception {
        InitialContext ctx = new InitialContext();
        return (DataSource) ctx.lookup("jdbc/webadmin");
    }
}
