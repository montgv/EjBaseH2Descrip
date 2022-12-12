import java.sql.*;

public class EjBaseH2Descrip {
    public static void main(String[] args) {
        try {
            //Antes de comenzar debemos de añadir la depencecia de hsqldb

            //Declaramos la variable para guardar la direccion de la base de datos y como se llama
            String urlConexion = "jdbc:h2:C:/Users/MateBook/Desktop/DAM/2ºDAM/ACDAT/TEMA 2/baseH2/pracEmbedida";

            //Establecemos la conexion con la base de datos
            Connection conexion = DriverManager.getConnection(urlConexion, "montse", "");

            //Instaciamos DatabaseMetaData que nos proporciona información sobre la BBDD mediante sus métodos
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet rs = null;

            //Obtenemos la información a cerca de la conexión de la BBDD
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();

            System.out.println ("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.println ("-----------------------------------");
            System.out.printf("Nombre : %s %n", nombre);
            System.out.printf("Driver : %s %n", driver);
            System.out.printf("URL    : %s %n", url );
            System.out.printf("Usuario: %s %n", usuario);

            //Obtenemos información de las tablas y vistas que contiene la BBDD
            rs = dbmd.getTables(null, "pracEmbedida", null, null);
            while (rs.next()){
                String catalogo = rs.getString("TABLE_CAT");
                String esquema = rs.getString("TABLE_SCHEM");
                String tabla= rs.getString("TABLE_NAME");
                String tipo= rs.getString("TABLE_TYPE");
                System.out.printf("%s - Catáloqo : %s, Esquema: %s, Nombre: %s %n", tipo, catalogo, esquema, tabla);
            }

            //Obtenemos información sobre las columnas de la tabla empleados de la BBDD
            System.out.println("COLUMNAS TABLA EMPLEADOS:");
            System.out.println("=========================");
            ResultSet columnas=null;
            columnas = dbmd.getColumns (null, "practica", "empleados", null);
            while (columnas. next ()) {
                String nombCol = columnas.getString("COLUMN_NAME");
                String tipoCol = columnas.getString("TYPE_NAME");
                String tamCol = columnas.getString("COLUMN_SIZE");
                String nula = columnas.getString("IS_NULLABLE");
                System.out.printf("Columna: %s, Tipo: %s, Tamaño: %s, ¿Puede ser Nula:? %s %n", nombCol, tipoCol, tamCol, nula);
            }

            //Obtenemos información sobre la clave primaria de la tabla empleados de la BBDD
            ResultSet pk = dbmd.getPrimaryKeys(null, "practica", "empleados");
            String pkDep = "", separador ="";
            while(pk.next()){
                pkDep = pkDep + separador + pk.getString("COLUMN_NAME");
                separador = "+";
            }
            System.out.println("Clave Primaria: "+ pkDep);

            //Obtenemos información sobre las clave ajenas de la tabla empleados de la BBDD
            ResultSet fk = dbmd.getExportedKeys (null, "practica", "empleados") ;
            while(fk.next()) {
                String fk_name = fk.getString("FKCOLUMN_NAME");
                String pk_name = fk.getString("PKCOLUMN_NAME");
                String pk_tablename = fk.getString("PKTABLE_NAME");
                String fk_tablename = fk.getString ("FKTABLE_NAME");
                System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
                System.out.printf ("Tabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
            }

            //Obtenemos información sobre los procedimientos de la BBDD
            ResultSet proc = dbmd.getProcedures(null, "practica", null);
            while(proc.next()){
                String proc_name = proc.getString("PROCEDURE_NAME");
                String proc_type = proc.getString("PROCEDURE_TYPE");
                System.out.printf("Nombre Procedimiento: %s - Tipo: %s %n", proc_name, proc_type);
            }

            //Cerramos los recursos utilizados
            conexion.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
