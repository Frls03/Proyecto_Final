/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parcial;
import java.sql.*;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

public class DB {
    public Connection con;
    
    //metodo constructor
    public DB() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        String server = "jdbc:mysql://localhost:3306/";
        String database = "ventas";
        String user = "root";
        String password = "";
        this.con = DriverManager.getConnection(server + database, user, password);
    }

    public boolean isOpen(){
        return this.con != null;
    }
    
    
    public ResultSet getData() throws SQLException{
        if(this.isOpen()){
            String sql = "select * from producto";
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        }
        else{
            return null;
        }
    }
    
    public void Insert(String nit, String cliente,String codigo, String producto,String cantidad, String precio) throws SQLException{
        double PRECIO = Double.valueOf(precio);
        double CANTIDAD=Double.valueOf(cantidad);
        double PrecioF;
        String sql = "insert into producto(Nit,Cliente,Codigo,nombre,cantidad,precio,preciof)values(?, ?, ?, ?, ?, ?,?)";
        PreparedStatement cursor = this.con.prepareCall(sql); 
        cursor.setString(1, nit);
        cursor.setString(2, cliente);
        cursor.setString(3, codigo);
        cursor.setString(4, producto);
        cursor.setDouble(5, CANTIDAD);
        cursor.setDouble(6, PRECIO);
        PrecioF=CANTIDAD*PRECIO;
        cursor.setDouble(7, PrecioF);
        
        
        int result = cursor.executeUpdate();
        if(result > 0){
            JOptionPane.showMessageDialog(null, "Se ha insertado la venta correctamente");
        }else{
             JOptionPane.showMessageDialog(null, "Error al insertar venta en la base de datos");
        }
    }
    
    public void Actualizar(String nit, String cliente,String codigo, String producto,String cantidad, String precio) throws SQLException{
        double PRECIO = Double.valueOf(precio);
        double CANTIDAD=Double.valueOf(cantidad);
        String sql = "update producto set Cliente=?, Codigo=?, nombre=?,cantidad=?, precio=? where Nit=?";
        PreparedStatement cursor = this.con.prepareCall(sql);
        cursor.setString(1, cliente);
        cursor.setString(2, codigo);
        cursor.setString(3, producto);
        cursor.setDouble(4, CANTIDAD);
        cursor.setDouble(5, PRECIO);
        cursor.setString(6, nit);
int result = cursor.executeUpdate();
        if(result > 0){
            JOptionPane.showMessageDialog(null, "Se ha modificado el producto correctamente");
            
        }else{
            JOptionPane.showMessageDialog(null, "Error al modificar producto en la base de datos");
            
        }
    }
    
    public void Eliminar(String codigo) throws SQLException{
        try{
            String query = "delete from producto where Codigo = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, codigo);
            preparedStmt.execute();
           JOptionPane.showMessageDialog(null, "Venta eliminada correctamente");
        }catch(Exception e){
            showMessageDialog(null, e.getMessage());
        }
        
      
    }
    
    
    public void CloseConnection() throws SQLException{
        this.con.close();
    }


}

