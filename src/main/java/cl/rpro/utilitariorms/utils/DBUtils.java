/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.utils;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author artsk
 */
public class DBUtils {

    private DBUtils() {
    }

    public static void limpiarRecursos(PreparedStatement... pss) {
        if (pss == null) {
            return;
        }
        for (PreparedStatement ps : pss) {
            try {
                ps.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void limpiarRecursos(ResultSet... rss) {
        if (rss == null) {
            return;
        }
        for (ResultSet rs : rss) {
            try {
                rs.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void limpiarRecursos(Connection... conns) {
        if (conns == null) {
            return;
        }
        for (Connection conn : conns) {
            try {
                conn.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void limpiarRecursos(BufferedWriter... buffereds) {
        if (buffereds == null) {
            return;
        }
        for (BufferedWriter bw : buffereds) {
            try {
                bw.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void limpiarRecursos(PreparedStatement ps, ResultSet rs) {
        limpiarRecursos(ps);
        limpiarRecursos(rs);
    }

    public static void limpiarRecursos(PreparedStatement ps, ResultSet rs, Connection conn) {
        limpiarRecursos(ps);
        limpiarRecursos(rs);
        limpiarRecursos(conn);
    }
}
