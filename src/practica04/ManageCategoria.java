/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package practica04;

import java.time.Clock;
import java.util.Iterator;
import java.util.List;
import nominas.entity.Categorias;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;




/**
 *
 * @author jimen
 */
public class ManageCategoria {
    
    
    public Categorias addCategoria(String nombreCategoria, double salarioBaseCategoria, double complementoCategoria){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer categoriaID = null;
        Categorias categoria = null;
        
        try{
            tx = session.beginTransaction();
            categoria = new Categorias(nombreCategoria, salarioBaseCategoria, complementoCategoria);
            categoriaID = (Integer) session.save(categoria);
            tx.commit();
        } catch(HibernateException e){
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        //HibernateUtil.getSessionFactory().close();
        return categoria;
    }
    
    public List listaCategoria(){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List categoriasList = null;
        
        try{
            tx = session.beginTransaction();
            categoriasList = session.createQuery("FROM Categorias").list();
            

            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return categoriasList;
    }
    
    
    public boolean existeEnBBDDCategoria(String categoriaAñadir){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List categoriasList = null;
        boolean flag = false;
        
        try{
            tx = session.beginTransaction();
            categoriasList = session.createQuery("FROM Categorias").list();
            
            for(Iterator iterator = categoriasList.iterator(); iterator.hasNext();){
                
                Categorias categoria = (Categorias) iterator.next();
                
                if(categoria.getNombreCategoria().equalsIgnoreCase(categoriaAñadir)){
                    flag = true;
                    break;
                }else{
                    flag = false;
                }
                
            }
            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return flag;
    }
    
    public Categorias updateCategoria(Integer categoriaID, String nombreCategoria, double salarioBaseCategoria, double complementoCategoria){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Categorias categoria = null;
        try{
            tx = session.beginTransaction();
            
            categoria = (Categorias)session.get(Categorias.class, categoriaID);
            categoria.setSalarioBaseCategoria(salarioBaseCategoria);
            categoria.setComplementoCategoria(complementoCategoria);
            
            session.update(categoria);
            
            tx.commit();
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return categoria;
    }
}
