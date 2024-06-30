/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        
        
        conn = new conectaDAO().connectDB();
        
        if(conn!=null){
            
            try{
               String sql = "INSERT INTO produtos (nome,valor,status) VALUES(?,?,?) ";
            
               prep = this.conn.prepareStatement(sql);
               prep.setString(1, produto.getNome());
               prep.setInt(2, produto.getValor());
               prep.setString(3, produto.getStatus());
               prep.executeUpdate();
               
               JOptionPane.showMessageDialog(null, "produto cadastrado com sucesso");
               
           }
           catch(SQLException e){
               System.out.println("Erro :" + e.getMessage());
           }
            
        }
        else{
            
            JOptionPane.showMessageDialog(null,"erro de conexão com o sistema");
            
        }
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        conn = new conectaDAO().connectDB();
        
        if(conn!=null){
            
            try{
                String busca = "SELECT * FROM produtos";
                prep = this.conn.prepareStatement(busca);
                resultset = prep.executeQuery();
                
                while(resultset.next()){
                    ProdutosDTO produto = new ProdutosDTO();
                    
                    produto.setId(resultset.getInt("id"));
                    produto.setNome(resultset.getString("nome"));
                    produto.setValor(resultset.getInt("valor"));
                    produto.setStatus(resultset.getString("status"));
                    
                    listagem.add(produto);
                }
            }
            catch(SQLException e){
                System.out.println("erro " +e.getMessage());
            }
            
            
        }
        
        return listagem;
    }
    
    
    public boolean venderProduto(ProdutosDTO produto){
        
        boolean status=false;
        String venda = "UPDATE produtos SET status =? WHERE id=?";
        conn = new conectaDAO().connectDB();
        
       if(conn!=null){
           try{
               prep = this.conn.prepareStatement(venda);
               prep.setString(1, "Vendido");
               prep.setInt(2, produto.getId());
               prep.executeUpdate();
               status = true;
           }
           catch(SQLException e){
               System.out.println("Erro :" + e.getMessage());
               return status;
           }
           
       }
       else{
         JOptionPane.showMessageDialog(null,"erro de conexão com o sistema");  
       }
       return status;
        
    }
    
    public ArrayList <ProdutosDTO> listarProdutosVendidos(){
        
        String busca = "SELECT * FROM produtos WHERE status LIKE ?";
        conn = new conectaDAO().connectDB();
        
        try{
             prep = this.conn.prepareStatement(busca);
             prep.setString(1, "Vendido");
             resultset = prep.executeQuery();
            
             
             while(resultset.next()){
                 ProdutosDTO produto = new ProdutosDTO();
                 produto.setId(resultset.getInt("id"));
                 produto.setNome(resultset.getString("nome"));
                 produto.setValor(resultset.getInt("valor"));
                 //produto.setStatus(resultset.getString("status"));
                 
                 listagem.add(produto);
             }
             return listagem;
        }
        catch(SQLException e){
            System.out.println("erro " +e.getMessage());
            return null;
        }
        
    }
    
    public ProdutosDTO buscarProduto(int id){
        
        String busca = "SELECT * FROM produtos WHERE id=?";
        conn = new conectaDAO().connectDB();
        
        try{
            prep = this.conn.prepareStatement(busca);
            prep.setInt(1, id);
            
            resultset = prep.executeQuery();
            ProdutosDTO produto = new ProdutosDTO();
            resultset.next();
            
            produto.setId(id);
            produto.setNome(resultset.getString("nome"));
            produto.setStatus(resultset.getString("status"));
            produto.setValor(resultset.getInt("valor"));
            return produto;
        }
        catch(SQLException e){
            System.out.println("erro " +e.getMessage());
                return null;
        }
        
        
    }
    
    
        
}

