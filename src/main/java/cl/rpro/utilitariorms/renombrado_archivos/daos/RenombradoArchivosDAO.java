/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.renombrado_archivos.daos;

import cl.rpro.utilitariorms.renombrado_archivos.model.ConfiguracionB2B;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.ejb.Stateless;

/**
 *
 * @author Artsk
 */
@Stateless
public class RenombradoArchivosDAO {

    public void validaRutaResp(ConfiguracionB2B configuracion) {

        String resp = configuracion.getRutaResp();
        File rutaResp = new File(resp);
        if (!rutaResp.exists()) {
            rutaResp.mkdir();
            Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.INFO, "Se creó la carpeta RESP.");
        }
    }

    public boolean descomprimeArchivos(ConfiguracionB2B configuracion) {

        String source = configuracion.getRutaZip() + configuracion.getNombreRetailer();
        String resp = configuracion.getRutaResp();
        String destination = configuracion.getRutaInput();

        byte[] buffer = new byte[1024];
        try {
            //setea la ruta del zip
            ZipInputStream origen = new ZipInputStream(new FileInputStream(source));
            //obtiene los archivos contenidos dentro del zip
            ZipEntry ze = origen.getNextEntry();

            while (ze != null) {
                if (ze.isDirectory()) {
                    ze = origen.getNextEntry();
                    continue;
                }
                String fileName = ze.getName();
                File rutaDestino = new File(destination + File.separator + fileName);

                Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.INFO, "Archivo Descomprimido: {0}", rutaDestino.getAbsoluteFile());
                
                //crea carpeta de destino
                new File(rutaDestino.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(rutaDestino);

                int len;
                while ((len = origen.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = origen.getNextEntry();
            }
            origen.closeEntry();
            origen.close();

        } catch (IOException ex) {
            Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.SEVERE, "No se pudo descomprimir", ex);
        }
        return true;
    }
}
