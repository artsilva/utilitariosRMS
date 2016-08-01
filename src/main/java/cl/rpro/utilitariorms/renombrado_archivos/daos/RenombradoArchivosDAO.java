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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
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

    public void descomprimeArchivos(ConfiguracionB2B configuracion) {

        String source = "";
        String carpetaInput = "";
        String carpetaResp = "";
        String extensionArchivo = "";
        String nombreMenosExtensionZIP = "";
        String nombreArchivoB2B = "";
        String rutaDestinoMasB2B = "";
        String rutaRespMasZip = "";

        File[] files = new File(configuracion.getRutaZip()).listFiles();

        for (File nombreArchivo : files) {
            if (!nombreArchivo.getName().contains("RESP")) {

                source = nombreArchivo.toString();
                carpetaInput = configuracion.getRutaInput();
                carpetaResp = configuracion.getRutaResp();

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

                        File rutaDestinoArchivoZip = new File(carpetaInput + File.separator + nombreArchivo.getName());

                        extensionArchivo = fileName.substring(fileName.length() - 4);
                        nombreMenosExtensionZIP = rutaDestinoArchivoZip.getAbsoluteFile().getName().substring(0, rutaDestinoArchivoZip.getAbsoluteFile().getName().length() - 4);
                        nombreArchivoB2B = nombreMenosExtensionZIP + extensionArchivo;
                        //Nombre ruta destino mas nombre del B2B descomprimido
                        rutaDestinoMasB2B = carpetaInput + File.separator + nombreArchivoB2B;
                        rutaRespMasZip = carpetaResp + File.separator + nombreArchivo.getName();

                        //crea carpeta de destino
                        new File(rutaDestinoArchivoZip.getParent()).mkdirs();

                        FileOutputStream fos = new FileOutputStream(rutaDestinoMasB2B);

                        Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.INFO, "==============================================================");
                        Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.INFO, "Archivo ZIP a descomprimir: {0}", rutaDestinoArchivoZip.getAbsoluteFile().getName());
                        Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.INFO, "El archivo descomprimido: {0} se renombró a {1}", new Object[]{fileName, nombreArchivoB2B});

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
                    Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.SEVERE, "Ocurrio un error al intentar descomprimir. ", ex);
                }
                try {
                    moverArchivoZIP(source, rutaRespMasZip);
                } catch (Exception ex) {
                    Logger.getLogger(RenombradoArchivosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void moverArchivoZIP(String origen, String destino) throws Exception {
        Path FROM = Paths.get(origen);
        Path TO = Paths.get(destino);
        Files.move(FROM, TO, REPLACE_EXISTING);
    }
}
