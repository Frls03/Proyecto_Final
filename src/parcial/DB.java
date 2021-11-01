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
    
    public void Insert(String codigo, String producto, String precio) throws SQLException{
        double PRECIO = Double.valueOf(precio);
        String sql = "insert into producto(Codigo,nombre,precio)values(?, ?, ?)";
        PreparedStatement cursor = this.con.prepareCall(sql);
        cursor.setString(1, codigo);
        cursor.setString(2, producto);
        cursor.setDouble(3, PRECIO);
        int result = cursor.executeUpdate();
        if(result > 0){
            JOptionPane.showMessageDialog(null, "Se ha insertado el producto correctamente");
        }else{
             JOptionPane.showMessageDialog(null, "Error al insertar producto en la base de datos");
        }
    }
    
    public void Actualizar(String codigo, String producto, String precio) throws SQLException{
        double PRECIO = Double.valueOf(precio);
        String sql = "update producto set nombre=?, precio=? where Codigo=?";
        PreparedStatement cursor = this.con.prepareCall(sql);
        cursor.setString(1, producto);
        cursor.setDouble(2, PRECIO);
        cursor.setString(3, codigo);
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
           JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
        }catch(Exception e){
            showMessageDialog(null, e.getMessage());
        }
        
      
    }
    
    
    public void CloseConnection() throws SQLException{
        this.con.close();
    }


}

