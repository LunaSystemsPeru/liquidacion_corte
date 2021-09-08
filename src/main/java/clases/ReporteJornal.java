/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.lunasystems.liquidacion.frm_principal;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import nicon.notify.core.Notification;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

/**
 *
 * @author Mariela
 */
public class ReporteJornal {

    Conectar conectar = new Conectar();
    Varios varios = new Varios();

    private int idcliente;
    private int idtipo;

    public ReporteJornal() {
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public void rptPagoJornalentreDias(String fecini, String fecfin) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        long diff = 0;
        try {
            Date date1 = myFormat.parse(fecini);
            Date date2 = myFormat.parse(fecfin);
            diff = date2.getTime() - date1.getTime();
            //System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        dias = dias + 1;
        String subquery = "";

        String[] titulos;
        int idias = Integer.parseInt(dias + "");

        titulos = new String[idias + 11];
        titulos[0] = "Item";
        titulos[1] = "Empleado";
        titulos[2] = "Nro Documento";
        titulos[3] = "Nro Cuenta";
        titulos[4] = "Cargo";
        titulos[5] = "Dia Pago";
        titulos[6] = "Hora Pago";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
            /*
                    subquery += "IFNULL((SELECT SEC_TO_TIME(TIMESTAMPDIFF(SECOND,STR_TO_DATE(hora_inicio, '%H:%i'), STR_TO_DATE(hora_salida, '%H:%i'))) "
                    + "FROM jornal_dia "
                    + "WHERE idjornal = jd.idjornal AND fecha = '" + date + "' AND idcargo = jd.idcargo AND hora_pago = jd.hora_pago),0) as '" + date + "', ";
             */
            titulos[7 + i] = varios.fecha_usuario(date);
        }

        String sql = "SELECT jd.idjornal, j.datos, j.nrodocumento, j.nrocuenta, car.descripcion, jd.dia_pago, jd.hora_pago, sum(jd.reintegro) as sreintegro, sum(jd.descuento) as sdescuento "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "inner join parametros_detalle as car on car.iddetalle = jd.idcargo "
                + "where jd.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and jd.idcliente = '" + this.idcliente + "' and jd.idtipo = '" + this.idtipo + "'  "
                + "GROUP by jd.idjornal, jd.hora_pago "
                + "order by datos asc, hora_pago asc";

        titulos[idias + 7] = "Total Horas";
        titulos[idias + 8] = "Reintegro";
        titulos[idias + 9] = "Descuentos";
        titulos[idias + 10] = "Total a Pagar";

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);
        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;

            while (rs.next()) {
                Object objectfila[] = new Object[idias + 11];
                int iidjornal = rs.getInt("idjornal");
                double totalhorasjornal = 0;
                double dreintegro = rs.getDouble("sreintegro");
                double ddescuento = rs.getDouble("sdescuento");
                double dpagojornal = rs.getDouble("dia_pago");
                if (dpagojornal == 0) {
                    dpagojornal = rs.getDouble("hora_pago");
                }

                for (int i = 0; i < dias; i++) {
                    Date fecha_temp = varios.suma_dia(fecini, i);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
                    String fechafinal = varios.fecha_usuario(date);
                    double horatotal = obtenerHorasDia(iidjornal, fechafinal, rs.getString("hora_pago"), rs.getString("dia_pago"));
                    horatotal = horatotal / 24;
                    totalhorasjornal += horatotal;
                    objectfila[7 + i] = horatotal;
                }

                double dapagar = (totalhorasjornal * 24 * dpagojornal) + dreintegro - ddescuento;

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("datos");
                objectfila[2] = rs.getString("nrodocumento");
                objectfila[3] = rs.getString("nrocuenta");
                objectfila[4] = rs.getString("descripcion");
                objectfila[5] = rs.getDouble("dia_pago");
                objectfila[6] = rs.getDouble("hora_pago");
                objectfila[idias + 7] = totalhorasjornal;
                objectfila[idias + 8] = dreintegro;
                objectfila[idias + 9] = ddescuento;
                objectfila[idias + 10] = dapagar;

                listafilas.add(objectfila);
                //System.out.println(Arrays.toString(objectfila));
            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        // System.out.println(Arrays.toString(titulos));
        File dir = new File("");

        String carpeta_reportes = dir.getAbsolutePath() + File.separator + "reportes";

        //String nombre_archivo = carpeta_reportes + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        JFileChooser guardar = new JFileChooser();
        //guardar.showSaveDialog(null);

        guardar.setDialogTitle("Seleccionar Carpeta para guardar Reporte");
        guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //guardar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //guardar.setName("pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls");
        guardar.setAcceptAllFileFilterUsed(false);
        guardar.setApproveButtonText("Sel. Carpeta ");

        if (guardar.showSaveDialog(frm_principal.jTabbedPane1) == JFileChooser.APPROVE_OPTION) {
            String carpetanueva = guardar.getSelectedFile().toString();
            // System.out.println(carpetanueva);
            carpeta_reportes = carpetanueva + File.separator + "jornal" + fecini + "_hasta_" + fecfin;
        } else {
            JOptionPane.showMessageDialog(null, "SE GUARDARA EL REPORTE EN LA CARPETA POR DEFECTO");
            carpeta_reportes += File.separator + "jornal" + fecini + "_hasta_" + fecfin;
        }

        //   System.out.println(guardarComo());
        /*
        File directorio = new File(carpeta_reportes);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
         */
        // Creamos el archivo donde almacenaremos la hoja
        // de calculo, recuerde usar la extension correcta,
        // en este caso .xlsx
        String nombre_archivo = carpeta_reportes + ".xls";// + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        File archivo = new File(nombre_archivo);
        //File archivo =narchivo;

        //  System.out.println(nombre_archivo);
        // Creamos el libro de trabajo de Excel formato OOXML
        HSSFWorkbook workbook = new HSSFWorkbook();

        // La hoja donde pondremos los datos
        HSSFSheet pagina = workbook.createSheet("Resumen");

        // Creamos una fila en la hoja en la posicion 0
        HSSFRow fila = pagina.createRow(0);
        //System.out.println(titulos.length + " total columnas");

        pagina.setColumnWidth(0, 1550);
        pagina.setColumnWidth(1, 15000);
        pagina.setColumnWidth(2, 4200);
        pagina.setColumnWidth(3, 4200);
        pagina.setColumnWidth(4, 4200);

        // Creamos el encabezado
        for (int i = 0; i < titulos.length; i++) {
            // Creamos una celda en esa fila, en la posicion 
            // indicada por el contador del ciclo
            HSSFCell celda = fila.createCell(i);

            // Indicamos el estilo que deseamos 
            // usar en la celda, en este caso el unico 
            // que hemos creado
            celda.setCellValue(titulos[i]);
        }

        //se hace el recorrido de la base de datos para cargar lo vaores en las celdas
        HSSFCellStyle style = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));

        HSSFCellStyle stylehora = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        stylehora.setDataFormat(HSSFDataFormat.getBuiltinFormat("[HH]:mm"));

        int filanro = 1;
        //    System.out.println(listafilas.size());
        for (int i = 0; i < listafilas.size(); i++) {

            // Ahora creamos una fila en la posicion 1
            fila = pagina.createRow(filanro);
            // Y colocamos los datos en esa fila
            Object filita[] = (Object[]) listafilas.get(i);
            //System.out.println(Arrays.toString(filita));
            int totalcolumnas = idias + 11;

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);

                if (j < 5) {
                    celda.setCellValue(filita[j] + "");
                }

                if (j == 5 || j == 6) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(style);
                    celda.setCellType(NUMERIC);
                }

                if (j > 6 && j < totalcolumnas - 3) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(stylehora);
                    celda.setCellType(NUMERIC);
                }

                if (j > totalcolumnas - 4) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(style);
                    celda.setCellType(NUMERIC);
                }
            }

            filanro++;
        }
        // Ahora guardaremos el archivo
        try {
            FileOutputStream salida = new FileOutputStream(archivo);
            workbook.write(salida);
            salida.close();

            System.out.println("Archivo creado existosamente en " + archivo.getAbsolutePath());
            Notification.show("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        }
        //System.out.println(sql);
    }

    public void rptPagoJornalsinHoras(String fecini, String fecfin) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        long diff = 0;
        try {
            Date date1 = myFormat.parse(fecini);
            Date date2 = myFormat.parse(fecfin);
            diff = date2.getTime() - date1.getTime();
            //System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        dias = dias + 1;
        String subquery = "";

        String[] titulos;
        int idias = Integer.parseInt(dias + "");

        titulos = new String[idias + 5];
        titulos[0] = "Item";
        titulos[1] = "Empleado";
        titulos[2] = "Nro Documento";
        titulos[3] = "Nro Cuenta";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);

            subquery += " IFNULL((select (reintegro - descuento) "
                    + "FROM jornal_dia "
                    + "WHERE idcliente = jd.idcliente and idjornal = jd.idjornal AND fecha = '" + date + "' and idtipo = jd.idtipo),0) as '" + date + "', ";

            titulos[4 + i] = varios.fecha_usuario(date);
        }

        String sql = "SELECT jd.idjornal, j.datos, j.nrodocumento,  "
                + subquery
                + "j.nrocuenta "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "where jd.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and jd.idcliente = '" + this.idcliente + "' and jd.idtipo = '" + this.idtipo + "'  "
                + "GROUP by jd.idjornal "
                + "order by datos asc";

        // System.out.println(sql);
        titulos[idias + 4] = "Total a Pagar";

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);
        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;

            while (rs.next()) {
                Object objectfila[] = new Object[idias + 5];
                int iidjornal = rs.getInt("idjornal");

                double dapagar = 0;

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("datos");
                objectfila[2] = rs.getString("nrodocumento");
                objectfila[3] = rs.getString("nrocuenta");

                for (int j = 4; j < idias + 4; j++) {

                    if (varios.esDecimal(rs.getString(j))) {
                        objectfila[j] = rs.getDouble(j);
                        dapagar += rs.getDouble(j);
                    } else {
                        objectfila[j] = 0;
                    }

                }
                objectfila[idias + 4] = dapagar;
                System.out.println("item " + idias + 4 + " valor = " + objectfila[idias + 4].toString());

                listafilas.add(objectfila);
                System.out.println(Arrays.toString(objectfila));
            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        // System.out.println(Arrays.toString(titulos));
        File dir = new File("");

        String carpeta_reportes = dir.getAbsolutePath() + File.separator + "reportes";

        //String nombre_archivo = carpeta_reportes + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        JFileChooser guardar = new JFileChooser();
        //guardar.showSaveDialog(null);

        guardar.setDialogTitle("Seleccionar Carpeta para guardar Reporte");
        guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //guardar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //guardar.setName("pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls");
        guardar.setAcceptAllFileFilterUsed(false);
        guardar.setApproveButtonText("Sel. Carpeta ");

        if (guardar.showSaveDialog(frm_principal.jTabbedPane1) == JFileChooser.APPROVE_OPTION) {
            String carpetanueva = guardar.getSelectedFile().toString();
            // System.out.println(carpetanueva);
            carpeta_reportes = carpetanueva + File.separator + "jornal_solomonto_" + fecini + "_hasta_" + fecfin;
        } else {
            JOptionPane.showMessageDialog(null, "SE GUARDARA EL REPORTE EN LA CARPETA POR DEFECTO");
            carpeta_reportes += File.separator + "jornal_solomonto_" + fecini + "_hasta_" + fecfin;
        }

        //   System.out.println(guardarComo());
        // Creamos el archivo donde almacenaremos la hoja
        // de calculo, recuerde usar la extension correcta,
        // en este caso .xlsx
        String nombre_archivo = carpeta_reportes + ".xls";// + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        File archivo = new File(nombre_archivo);
        //File archivo =narchivo;

        //  System.out.println(nombre_archivo);
        // Creamos el libro de trabajo de Excel formato OOXML
        HSSFWorkbook workbook = new HSSFWorkbook();

        // La hoja donde pondremos los datos
        HSSFSheet pagina = workbook.createSheet("Resumen");

        // Creamos una fila en la hoja en la posicion 0
        HSSFRow fila = pagina.createRow(0);
        //System.out.println(titulos.length + " total columnas");

        pagina.setColumnWidth(0, 1550);
        pagina.setColumnWidth(1, 15000);
        pagina.setColumnWidth(2, 4200);
        pagina.setColumnWidth(3, 4200);

        // Creamos el encabezado
        for (int i = 0; i < titulos.length; i++) {
            // Creamos una celda en esa fila, en la posicion 
            // indicada por el contador del ciclo
            HSSFCell celda = fila.createCell(i);

            // Indicamos el estilo que deseamos 
            // usar en la celda, en este caso el unico 
            // que hemos creado
            celda.setCellValue(titulos[i]);
        }

        //se hace el recorrido de la base de datos para cargar lo vaores en las celdas
        HSSFCellStyle style = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));

        HSSFCellStyle stylehora = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        stylehora.setDataFormat(HSSFDataFormat.getBuiltinFormat("[HH]:mm"));

        int filanro = 1;
        //    System.out.println(listafilas.size());
        for (int i = 0; i < listafilas.size(); i++) {

            // Ahora creamos una fila en la posicion 1
            fila = pagina.createRow(filanro);
            // Y colocamos los datos en esa fila
            Object filita[] = (Object[]) listafilas.get(i);
            //System.out.println(Arrays.toString(filita));
            int totalcolumnas = idias + 5;

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);

                if (j < 4) {
                    celda.setCellValue(filita[j] + "");
                }

                if (j > 3 && j < totalcolumnas) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(style);
                    celda.setCellType(NUMERIC);
                }

            }

            filanro++;
        }
        // Ahora guardaremos el archivo
        try {
            FileOutputStream salida = new FileOutputStream(archivo);
            workbook.write(salida);
            salida.close();

            System.out.println("Archivo creado existosamente en " + archivo.getAbsolutePath());
            Notification.show("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        }
        //System.out.println(sql);

    }

    public void rptPagoEnvasadoentreDias(String fecini, String fecfin) {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        long diff = 0;
        try {
            Date date1 = myFormat.parse(fecini);
            Date date2 = myFormat.parse(fecfin);
            diff = date2.getTime() - date1.getTime();
            //System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        dias = dias + 1;
        String subquery = "";

        String[] titulos;
        int idias = Integer.parseInt(dias + "");

        titulos = new String[idias + 8];
        titulos[0] = "Item";
        titulos[1] = "Empleado";
        titulos[2] = "Nro Documento";
        titulos[3] = "Nro Cuenta";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
            subquery += "ifnull((select ifnull(envase_detalle.idenvase, 0) "
                    + "from envase_detalle "
                    + "inner join envase_equitativo on envase_equitativo.idenvase = envase_detalle.idenvase  "
                    + "where envase_detalle.idjornal = ed.idjornal and envase_equitativo.fecha= '" + date + "' and envase_equitativo.idcliente = ee.idcliente), 0) "
                    + "as '" + date + "', ";
            titulos[4 + i] = varios.fecha_usuario(date);
        }

        String sql = "select ed.idjornal, j.datos, j.nrodocumento, j.nrocuenta,  "
                + subquery
                + "sum(ed.adicional) as sadicional, sum(ed.descuento) as sdescuento, ee.cant_barriles "
                + "from envase_detalle as ed  "
                + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                + "where ee.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and ee.idcliente = '" + this.idcliente + "' "
                + "group by ed.idjornal "
                + "order by j.datos asc";

        //se procede a hacer la consulta general de acuerdo al rango de fecha
        //System.out.println(sql);
        titulos[idias + 4] = "Total Envasado";
        titulos[idias + 5] = "Reintegro";
        titulos[idias + 6] = "Descuentos";
        titulos[idias + 7] = "Total a Pagar";
        //System.out.println(Arrays.toString(titulos));

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);

        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;
            double sumatotal = 0;
            Object objectfilafinal[] = new Object[idias + 8];
            objectfilafinal[1] = "NRO DE BARRILES - SUMA TOTALES ";
            objectfilafinal[0] = "";
            objectfilafinal[2] = "";
            objectfilafinal[3] = "";

            Object objectbarriles[] = new Object[idias + 9];
            //objectbarriles[0] = "";
            //objectbarriles[1] = "";
            //objectbarriles[2] = "";
            //objectbarriles[3] = "";

            while (rs.next()) {

                Object objectfila[] = new Object[idias + 8];
                double dreintegro = rs.getDouble("sadicional");
                double ddescuento = rs.getDouble("sdescuento");
                double sumapagobarril = 0;

                for (int i = 0; i < dias; i++) {
                    int idenvase = rs.getInt(5 + i);
                    String[] resultado;
                    double monto = 0;
                    int barrilesfechainicial = 0;
                    int barrilesfecha = 0;
                    if (idenvase > 0) {
                        //if (varios.esEntero(validar_idenvase)) {
                        resultado = obtenerTotalBarriles(idenvase);
                        monto = Double.parseDouble(resultado[0]);
                        barrilesfecha = Integer.parseInt(resultado[1] + "");
                        if (barrilesfecha > 0) {
                            barrilesfechainicial = barrilesfecha;
                            objectbarriles[4 + i] = barrilesfechainicial;
                        }
                    }
                    sumapagobarril += monto;
                    objectfila[4 + i] = monto;
                }

                double dapagar = sumapagobarril + dreintegro - ddescuento;
                sumatotal += dapagar;

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("datos");
                objectfila[2] = rs.getString("nrodocumento");
                objectfila[3] = rs.getString("nrocuenta");
                objectfila[idias + 4] = varios.formato_numero(sumapagobarril);
                objectfila[idias + 5] = dreintegro;
                objectfila[idias + 6] = ddescuento;
                objectfila[idias + 7] = varios.formato_numero(dapagar);

                //objectfilafinal[4 + idias] = rs.getString("cant_barriles");
                //System.out.println(rs.getString("cant_barriles") + " cantidad de barriles " + idias + "\n");
                listafilas.add(objectfila);
                // System.out.println(Arrays.toString(objectfila));
            }

            int x = 0;
            for (Object objectbarrile : objectbarriles) {
                //System.out.println("valor de objeto " + objectbarrile);
                int valorobjeto;
                if (objectbarrile == null) {
                    valorobjeto = 0;
                } else {
                    valorobjeto = Integer.parseInt(objectbarrile.toString());
                }
                //System.out.println("nuevo valor de objeto " + valorobjeto);
                if (x < idias + 4) {
                    if (x > 3) {
                        objectfilafinal[x] = valorobjeto;
                    }
                    x++;
                }

            }
            //System.out.println("largo de array = " + objectbarriles.length);
            objectfilafinal[idias + 4] = 0;
            objectfilafinal[idias + 5] = 0;
            objectfilafinal[idias + 6] = 0;
            objectfilafinal[idias + 7] = varios.formato_numero(sumatotal);
            listafilas.add(objectfilafinal);

            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        // System.out.println(Arrays.toString(titulos));
        File dir = new File("");

        String carpeta_reportes = dir.getAbsolutePath() + File.separator + "reportes";

        //String nombre_archivo = carpeta_reportes + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        JFileChooser guardar = new JFileChooser();
        //guardar.showSaveDialog(null);

        guardar.setDialogTitle("Seleccionar Carpeta para guardar Reporte");
        guardar.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //guardar.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //guardar.setName("pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls");
        guardar.setAcceptAllFileFilterUsed(false);
        guardar.setApproveButtonText("Sel. Carpeta ");

        if (guardar.showSaveDialog(frm_principal.jTabbedPane1) == JFileChooser.APPROVE_OPTION) {
            String carpetanueva = guardar.getSelectedFile().toString();
            // System.out.println(carpetanueva);
            carpeta_reportes = carpetanueva + File.separator + "jornal_envase_" + fecini + "_hasta_" + fecfin;
        } else {
            JOptionPane.showMessageDialog(null, "SE GUARDARA EL REPORTE EN LA CARPETA POR DEFECTO");
            carpeta_reportes += File.separator + "jornal_envase_" + fecini + "_hasta_" + fecfin;
        }

        //   System.out.println(guardarComo());
        /*
        File directorio = new File(carpeta_reportes);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
         */
        // Creamos el archivo donde almacenaremos la hoja
        // de calculo, recuerde usar la extension correcta,
        // en este caso .xlsx
        String nombre_archivo = carpeta_reportes + ".xls";// + File.separator + "pesaje_" + fecha_inicio + "_hasta_" + date_final + ".xls";
        File archivo = new File(nombre_archivo);
        //File archivo =narchivo;

        //  System.out.println(nombre_archivo);
        // Creamos el libro de trabajo de Excel formato OOXML
        HSSFWorkbook workbook = new HSSFWorkbook();

        // La hoja donde pondremos los datos
        HSSFSheet pagina = workbook.createSheet("Resumen");

        // Creamos una fila en la hoja en la posicion 0
        HSSFRow fila = pagina.createRow(0);
        //System.out.println(titulos.length + " total columnas");

        pagina.setColumnWidth(0, 1550);
        pagina.setColumnWidth(1, 15000);
        pagina.setColumnWidth(2, 4200);
        pagina.setColumnWidth(3, 4200);

        // Creamos el encabezado
        for (int i = 0; i < titulos.length; i++) {
            // Creamos una celda en esa fila, en la posicion 
            // indicada por el contador del ciclo
            HSSFCell celda = fila.createCell(i);

            // Indicamos el estilo que deseamos 
            // usar en la celda, en este caso el unico 
            // que hemos creado
            celda.setCellValue(titulos[i]);
        }

        //se hace el recorrido de la base de datos para cargar lo vaores en las celdas
        HSSFCellStyle style = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));

        HSSFCellStyle stylehora = workbook.createCellStyle();
        // style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0.00"));
        stylehora.setDataFormat(HSSFDataFormat.getBuiltinFormat("[HH]:mm"));

        int filanro = 1;
        //  System.out.println(listafilas.size());
        for (int i = 0; i < listafilas.size(); i++) {

            // Ahora creamos una fila en la posicion 1
            fila = pagina.createRow(filanro);
            // Y colocamos los datos en esa fila
            Object filita[] = (Object[]) listafilas.get(i);
            //System.out.println(Arrays.toString(filita));
            int totalcolumnas = idias + 8;
            //  System.out.println(totalcolumnas + " es el total de columnas");

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);
                // System.out.println(filita[j] + " es de la fila " + j);

                if (j < 4) {
                    celda.setCellValue(filita[j] + "");
                }

                if (j > 3) {
                    celda.setCellValue(Double.parseDouble(filita[j] + ""));
                    celda.setCellStyle(style);
                    celda.setCellType(NUMERIC);
                }
            }

            filanro++;
        }

        // Ahora guardaremos el archivo
        try {
            FileOutputStream salida = new FileOutputStream(archivo);
            workbook.write(salida);
            salida.close();

            System.out.println("Archivo creado existosamente en " + archivo.getAbsolutePath());
            Notification.show("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        }
        //System.out.println(sql);
    }

    private double obtenerHorasDia(int idjornal, String fecha, String hora_pago, String dia_pago) {
        double horas = 0;
        fecha = varios.fecha_myql(fecha);
        String sql_hora = "select jd.hora_inicio, jd.hora_salida "
                + "from jornal_dia as jd "
                + "where jd.fecha = '" + fecha + "' and jd.idjornal = '" + idjornal + "' and jd.hora_pago = '" + hora_pago + "' and jd.dia_pago = '" + dia_pago + "' and jd.idcliente = '" + this.idcliente + "' ";
        // System.out.println(sql_hora);
        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql_hora);
        try {
            while (rs.next()) {
                String horainicio = rs.getString("hora_inicio");
                String horasalida = rs.getString("hora_salida");
                horas = varios.restarHoras(horainicio, horasalida);
            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        return horas;
    }

    public String[] obtenerTotalBarriles(int idenvase) {
        String resultado[] = new String[2];
        try {
            Statement st = conectar.conexion();
            String query = "select ee.cant_barriles, (ee.cant_barriles * ee.monto_pagar / COUNT(ed.idjornal)) as monto "
                    + "from envase_equitativo as ee "
                    + "inner join envase_detalle as ed  on ee.idenvase = ed.idenvase "
                    + "where ee.idenvase  = '" + idenvase + "' ";
            //System.out.println(query);
            ResultSet rs = conectar.consulta(st, query);
            if (rs.next()) {
                resultado[0] = rs.getString("monto");
                resultado[1] = rs.getString("cant_barriles");
                /*
                resultado[0] = rs.getDouble("monto");
                resultado[1] = rs.getDouble("cant_barriles");
                 */
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return resultado;
    }

}
