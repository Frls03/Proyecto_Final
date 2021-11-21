package parcial;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Reporte {
    public void CrearReporte(){

        String nombreArchivo = "Productos.xlsx";
        
        String hoja = "INFORME";
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(hoja);
        
        // Cabecera de la hoja de excel
        String[] header = new String[] {"Nit", "Cliente", "Codigo", "Producto", "Cantidad", "Precio Unitario", "Precio Final sin iva", "Precio final"};
        
        // Contenido de la hoja de excel
        //String[][] document = new String[][];
        
        // Poner en negrita la cabecera
        CellStyle style = libro.createCellStyle();
        Font font = libro.createFont();
        font.setBold(true);
        style.setFont(font);
        
        
        Row headersRow = hoja1.createRow(0);
        for(int i = 0; i < header.length; i++){
            Cell headerCell = headersRow.createCell(i);
            headerCell.setCellValue(header[i]);
        }
        int tam = 0;
        try {
            DB data = new DB();
            ResultSet rs = data.getData();
            int cell_pos = 1;
            int n = rs.getMetaData().getColumnCount();
            tam = n;
            while(rs.next()){
                Row current = hoja1.createRow(cell_pos);
                for(int i = 0; i < n; i++){
                    Cell mycell = current.createCell(i);
                    if(i >= 4){
                        mycell.setCellValue(rs.getDouble(i+1));
                    }else{
                        mycell.setCellValue(rs.getString(i+1));
                    }
                }
                tam = cell_pos;
                cell_pos++;
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i = 0; i <= tam+1; i++){
            hoja1.autoSizeColumn(i);
        }
        
        System.out.println("Rows: " + tam);
        // Crear el archivo
        try (OutputStream fileOut = new FileOutputStream(nombreArchivo)){
            JOptionPane.showMessageDialog(null, "Archivo Creado Correctamente!");
            libro.write(fileOut);
            fileOut.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
}
