/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.lunasystems.liquidacion.frm_principal;
import java.awt.AWTException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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

    Notificacion NotifyI = new Notificacion();

    private int idcliente;
    private int idtipo;
    private final String empresa;

    public ReporteJornal() {
        empresa = frm_principal.cliente.getSede();
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

        titulos = new String[idias + 12];
        titulos[0] = "Item";
        titulos[1] = "DNI Trabajador";
        titulos[2] = "Empleado";
        titulos[3] = "DNI Cuenta";
        titulos[4] = "Nro Cuenta";
        titulos[5] = "Cargo";
        titulos[6] = "Dia Pago";
        titulos[7] = "Hora Pago";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
            /*
                    subquery += "IFNULL((SELECT SEC_TO_TIME(TIMESTAMPDIFF(SECOND,STR_TO_DATE(hora_inicio, '%H:%i'), STR_TO_DATE(hora_salida, '%H:%i'))) "
                    + "FROM jornal_dia "
                    + "WHERE idjornal = jd.idjornal AND fecha = '" + date + "' AND idcargo = jd.idcargo AND hora_pago = jd.hora_pago),0) as '" + date + "', ";
             */
            titulos[8 + i] = varios.fecha_usuario(date);
        }

        String sql = "SELECT jd.idjornal, j.dni_trabajador, j.datos, j.dni_cuenta, j.nrocuenta, car.descripcion, jd.dia_pago, jd.hora_pago, sum(jd.reintegro) as sreintegro, sum(jd.descuento) as sdescuento "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "inner join parametros_detalle as car on car.iddetalle = j.idcargo "
                + "where jd.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and jd.idcliente = '" + this.idcliente + "' and jd.idtipo = '" + this.idtipo + "'  "
                + "group by jd.idjornal, jd.hora_pago, jd.dia_pago "
                + "order by datos asc, hora_pago asc";

        //   
        titulos[idias + 8] = "Total Horas";
        titulos[idias + 9] = "Reintegro";
        titulos[idias + 10] = "Descuentos";
        titulos[idias + 11] = "Total a Pagar";

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);
        System.out.println(sql);
        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;

            while (rs.next()) {
                Object objectfila[] = new Object[idias + 12];
                int iidjornal = rs.getInt("idjornal");
                double totalhorasjornal = 0;
                int contardias = 0;
                double dreintegro = rs.getDouble("sreintegro");
                double ddescuento = rs.getDouble("sdescuento");
                double dpagojornal = rs.getDouble("dia_pago");
                double hpagojornal = rs.getDouble("hora_pago");

                for (int i = 0; i < dias; i++) {
                    Date fecha_temp = varios.suma_dia(fecini, i);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
                    String fechafinal = varios.fecha_usuario(date);
                    double horatotal = obtenerHorasDia(iidjornal, fechafinal, rs.getString("hora_pago"), rs.getString("dia_pago"));
                    horatotal = horatotal / 24;
                    totalhorasjornal += horatotal;
                    contardias++;
                    objectfila[8 + i] = horatotal;
                }
                double dapagar = 0;

                if (dpagojornal == 0) {
                    dapagar = (totalhorasjornal * 24 * hpagojornal) + dreintegro - ddescuento;
                }
                if (hpagojornal == 0) {
                    dapagar = (contardias * dpagojornal) + dreintegro - ddescuento;
                }

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("dni_trabajador");
                objectfila[2] = rs.getString("datos");
                objectfila[3] = rs.getString("dni_cuenta");
                objectfila[4] = rs.getString("nrocuenta");
                objectfila[5] = rs.getString("descripcion");
                objectfila[6] = rs.getDouble("dia_pago");
                objectfila[7] = rs.getDouble("hora_pago");
                objectfila[idias + 8] = totalhorasjornal;
                objectfila[idias + 9] = dreintegro;
                objectfila[idias + 10] = ddescuento;
                objectfila[idias + 11] = dapagar;

                listafilas.add(objectfila);
                //System.out.println(Arrays.toString(objectfila));
            }
            conectar.cerrar(st);
            conectar.cerrar(rs);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

        System.out.println(Arrays.toString(titulos));
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
            carpeta_reportes = carpetanueva + File.separator + empresa + "_jornal" + fecini + "_hasta_" + fecfin;
        } else {
            JOptionPane.showMessageDialog(null, "SE GUARDARA EL REPORTE EN LA CARPETA POR DEFECTO");
            carpeta_reportes += File.separator + "jornal_" + this.idtipo + "_" + fecini + "_hasta_" + fecfin;
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
        pagina.setColumnWidth(1, 2500);
        pagina.setColumnWidth(2, 15000);
        pagina.setColumnWidth(3, 2500);
        pagina.setColumnWidth(4, 4500);

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
            int totalcolumnas = idias + 12;

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);

//                if (filita[j] == null) {
//                    System.out.println("fila nro " + j + " es nulo");
//                }
                if (j < 6) {
                    if (filita[j] == null) {
                        celda.setCellValue("");
                    } else {
                        celda.setCellValue(filita[j] + "");
                    }
                }

                if (j == 6 || j == 7) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(style);
                    celda.setCellType(NUMERIC);
                }

                if (j > 7 && j < totalcolumnas - 3) {
                    celda.setCellValue((double) filita[j]);
                    celda.setCellStyle(stylehora);
                    celda.setCellType(NUMERIC);
                }

                if (j > totalcolumnas - 4) {
                    if (filita[j] == null) {
                        celda.setCellValue(0);
                        celda.setCellStyle(style);
                        celda.setCellType(NUMERIC);
                    } else {
                        celda.setCellValue((double) filita[j]);
                        celda.setCellStyle(style);
                        celda.setCellType(NUMERIC);
                    }
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
            NotifyI.displayTray("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        } catch (AWTException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        //System.out.println(sql);

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

        titulos = new String[idias + 6];
        titulos[0] = "Item";
        titulos[1] = "DNI Trabajador";
        titulos[2] = "Jornalero";
        titulos[3] = "DNI Cuenta";
        titulos[4] = "Nro Cuenta";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);

            subquery += " IFNULL((select (reintegro - descuento) "
                    + "FROM jornal_dia "
                    + "WHERE idcliente = jd.idcliente and idjornal = jd.idjornal AND fecha = '" + date + "' and idtipo = jd.idtipo),0) as '" + date + "', ";

            titulos[5 + i] = varios.fecha_usuario(date);
        }

        String sql = "SELECT jd.idjornal, j.datos, j.dni_trabajador, j.dni_cuenta, "
                + subquery
                + "j.nrocuenta "
                + "from jornal_dia as jd "
                + "inner join jornaleros as j on j.idjornal = jd.idjornal "
                + "where jd.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and jd.idcliente = '" + this.idcliente + "' and jd.idtipo = '" + this.idtipo + "'  "
                + "GROUP by jd.idjornal "
                + "order by datos asc";

        // System.out.println(sql);
        titulos[idias + 5] = "Total a Pagar";

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);
        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;

            while (rs.next()) {
                Object objectfila[] = new Object[idias + 6];
                int iidjornal = rs.getInt("idjornal");

                double dapagar = 0;

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("dni_trabajador");
                objectfila[2] = rs.getString("datos");
                objectfila[3] = rs.getString("dni_cuenta");
                objectfila[4] = rs.getString("nrocuenta");

                for (int j = 5; j < idias + 5; j++) {

                    if (varios.esDecimal(rs.getString(j))) {
                        objectfila[j] = rs.getDouble(j);
                        dapagar += rs.getDouble(j);
                    } else {
                        objectfila[j] = 0;
                    }

                }
                objectfila[idias + 5] = dapagar;
//                System.out.println("item " + idias + 5 + " valor = " + objectfila[idias + 5].toString());

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
            carpeta_reportes = carpetanueva + File.separator + empresa + "_jornal_solomonto_" + fecini + "_hasta_" + fecfin;
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
        pagina.setColumnWidth(1, 2500);
        pagina.setColumnWidth(2, 10500);
        pagina.setColumnWidth(3, 2500);
        pagina.setColumnWidth(4, 3800);

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
            int totalcolumnas = idias + 6;

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);

                if (j < 5) {
                    celda.setCellValue(filita[j] + "");
                }

                if (j > 4 && j < totalcolumnas) {
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
            NotifyI.displayTray("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        } catch (AWTException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        //System.out.println(sql);

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

        titulos = new String[idias + 9];
        titulos[0] = "Item";
        titulos[1] = "DNI Trabajador";
        titulos[2] = "Empleado";
        titulos[3] = "DNI Cuenta";
        titulos[4] = "Nro Cuenta";

        for (int i = 0; i < dias; i++) {
            Date fecha_temp = varios.suma_dia(fecini, i);
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fecha_temp);
            subquery += "ifnull((select ifnull(envase_detalle.idenvase, 0) "
                    + "from envase_detalle "
                    + "inner join envase_equitativo on envase_equitativo.idenvase = envase_detalle.idenvase  "
                    + "where envase_detalle.idjornal = ed.idjornal and envase_equitativo.fecha= '" + date + "' and envase_equitativo.idcliente = ee.idcliente), 0) "
                    + "as '" + date + "', ";
            titulos[5 + i] = varios.fecha_usuario(date);
        }

        String sql = "select ed.idjornal, j.datos, j.dni_trabajador, j.dni_cuenta, j.nrocuenta,  "
                + subquery
                + "sum(ed.adicional) as sadicional, sum(ed.descuento) as sdescuento, ee.cant_barriles "
                + "from envase_detalle as ed  "
                + "inner join jornaleros as j on j.idjornal = ed.idjornal "
                + "inner join envase_equitativo as ee on ee.idenvase = ed.idenvase "
                + "where ee.fecha BETWEEN '" + fecini + "' and '" + fecfin + "' and ee.idcliente = '" + this.idcliente + "' "
                + "group by ed.idjornal "
                + "order by j.datos asc";

        //se procede a hacer la consulta general de acuerdo al rango de fecha
        System.out.println(sql);
        titulos[idias + 5] = "Total Envasado";
        titulos[idias + 6] = "Reintegro";
        titulos[idias + 7] = "Descuentos";
        titulos[idias + 8] = "Total a Pagar";
        //System.out.println(Arrays.toString(titulos));

        Statement st = conectar.conexion();
        ResultSet rs = conectar.consulta(st, sql);

        //ArrayList listafilas = new ArrayList();
        ArrayList<Object> listafilas = new ArrayList<>();
        try {
            int nroitems = 1;
            double sumatotal = 0;
            Object objectfilafinal[] = new Object[idias + 9];
            objectfilafinal[0] = "";
            objectfilafinal[1] = "";
            objectfilafinal[2] = "NRO DE BARRILES - SUMA TOTALES ";
            objectfilafinal[3] = "";
            objectfilafinal[4] = "";

            Object objectbarriles[] = new Object[idias + 10];
            //objectbarriles[0] = "";
            //objectbarriles[1] = "";
            //objectbarriles[2] = "";
            //objectbarriles[3] = "";

            while (rs.next()) {

                Object objectfila[] = new Object[idias + 9];
                double dreintegro = rs.getDouble("sadicional");
                double ddescuento = rs.getDouble("sdescuento");
                double sumapagobarril = 0;

                for (int i = 0; i < dias; i++) {
                    int idenvase = rs.getInt(6 + i);
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
                            objectbarriles[5 + i] = barrilesfechainicial;
                        }
                    }
                    sumapagobarril += monto;
                    objectfila[5 + i] = monto;
                }

                double dapagar = sumapagobarril + dreintegro - ddescuento;
                sumatotal += dapagar;

                objectfila[0] = nroitems;
                nroitems++;
                objectfila[1] = rs.getString("dni_trabajador");
                objectfila[2] = rs.getString("datos");
                objectfila[3] = rs.getString("dni_cuenta");
                objectfila[4] = rs.getString("nrocuenta");
                objectfila[idias + 5] = varios.formato_numero(sumapagobarril);
                objectfila[idias + 6] = dreintegro;
                objectfila[idias + 7] = ddescuento;
                objectfila[idias + 8] = varios.formato_numero(dapagar);

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
                if (x < idias + 5) {
                    if (x > 4) {
                        objectfilafinal[x] = valorobjeto;
                    }
                    x++;
                }

            }
            //System.out.println("largo de array = " + objectbarriles.length);
            objectfilafinal[idias + 5] = 0;
            objectfilafinal[idias + 6] = 0;
            objectfilafinal[idias + 7] = 0;
            objectfilafinal[idias + 8] = varios.formato_numero(sumatotal);
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
            carpeta_reportes = carpetanueva + File.separator + empresa + "_jornal_envase_" + fecini + "_hasta_" + fecfin;
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
        pagina.setColumnWidth(1, 3200);
        pagina.setColumnWidth(2, 10500);
        pagina.setColumnWidth(3, 3200);
        pagina.setColumnWidth(4, 4300);

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
            int totalcolumnas = idias + 9;
            //  System.out.println(totalcolumnas + " es el total de columnas");

            for (int j = 0; j < (totalcolumnas); j++) {
                // Creamos una celda en esa fila, en la
                // posicion indicada por el contador del ciclo
                HSSFCell celda = fila.createCell(j);
                // System.out.println(filita[j] + " es de la fila " + j);

                if (j < 5) {
                    celda.setCellValue(filita[j] + "");
                }

                if (j > 4) {
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
            NotifyI.displayTray("Creado", "Archivo creado existosamente en " + archivo.getAbsolutePath());

            Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Archivo no localizable en sistema de archivos");
            JOptionPane.showMessageDialog(null, "ERROR AL GENERAR EL ARCHIVO \n" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
            System.out.println("Error de entrada/salida");
            JOptionPane.showMessageDialog(null, "Error de entrada/salida \n" + ex.getLocalizedMessage());
        } catch (AWTException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        //System.out.println(sql);
    }

    private double obtenerHorasDia(int idjornal, String fecha, String hora_pago, String dia_pago) {
        double horas = 0;
        fecha = varios.fecha_myql(fecha);
        String sql_hora = "select jd.hora_inicio, jd.hora_salida "
                + "from jornal_dia as jd "
                + "where jd.fecha = '" + fecha + "' and jd.idjornal = '" + idjornal + "' and jd.hora_pago = '" + hora_pago + "' and jd.dia_pago = '" + dia_pago + "' and jd.idcliente = '" + this.idcliente + "' and jd.idtipo = '" + this.idtipo + "'";
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
