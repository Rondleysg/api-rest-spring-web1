package br.edu.ifs.project_web;
import  java.sql.*;

public class Main {
    public static void main(String[] p) {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/projeto_web";
            String usuario = "root";
            String senha = "";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, usuario, senha);
            
            //Insercao
            String login = "zezinho1";
            String nome = "Jose Tavares1";
            String email = "ze@tavares.com";
            String pass = "123456";
            String sqlInsert = "insert into usuario (usu_tx_nome, usu_tx_login, usu_tx_email, usu_tx_senha) values (?, ? ,? ,? )";
            PreparedStatement psInsert = con.prepareStatement(sqlInsert);
            psInsert.setObject(1, nome);
            psInsert.setObject(2, login);
            psInsert.setObject(3, email);
            psInsert.setObject(4, pass);
            //psInsert.execute();

            /*String sqlUpdate = "update seguranca.usu_usuario set usu_tx_email=? where usu_nr_id=?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
            psUpdate.setObject(1, "teste@email.com");
            psUpdate.setObject(2, 1);
            psUpdate.execute();

            String sqlDelete = "delete from seguranca.usu_usuario where usu_nr_id=?";
            PreparedStatement psDelete = con.prepareStatement(sqlDelete);
            psDelete.setObject(1, 1);
            psDelete.execute();*/


            String sql = "select * from usuario";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("usu_tx_nome"));
                System.out.println(rs.getInt("usu_nr_id"));
            }
            rs.close();
            ps.close();
            con.close();
            System.out.println(con.isClosed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
