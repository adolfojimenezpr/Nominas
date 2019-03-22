/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package practica04;

import java.util.Iterator;
import java.util.List;
import nominas.entity.Categorias;
import nominas.entity.Empresas;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author jimen
 */
public class ManageEmpresa {
    
    public Empresas addEmpresa(String nombreEmpresa, String CIF){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer empresaID = null;
        Empresas empresa = null;
        
        try{
            
            tx = session.beginTransaction();
            empresa = new Empresas(nombreEmpresa, CIF);
            empresaID = (Integer) session.save(empresa);
            tx.commit();
            
        }catch(HibernateException e ){
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        }finally{
            session.close();
        }
        
        return empresa;
        
    }
    
    public List listaEmpresa(){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List empresasList = null;
        
        try{
            tx = session.beginTransaction();
            empresasList = session.createQuery("FROM Empresas").list();
            
            
            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return empresasList;
        
        
    }
    
    public boolean existeEnBBDDEmpresa(String CIF){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List empresasList = null;
        boolean flag = false;
        
        try{
            tx = session.beginTransaction();
            empresasList = session.createQuery("FROM Empresas").list();
            
            for(Iterator iterator = empresasList.iterator(); iterator.hasNext();){
                
                Empresas empresa = (Empresas) iterator.next();
                
                if(empresa.getCif().equalsIgnoreCase(CIF)){
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
    
    
    public Empresas updateEmpresa(Integer empresaID, String nombreEmpresa, String CIF){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Empresas empresa = null;
        try{
            tx = session.beginTransaction();
            
            empresa = (Empresas)session.get(Empresas.class, empresaID);
           
            
            empresa.setNombre(nombreEmpresa);
            empresa.setCif(CIF);
            
            session.update(empresa);
            
            tx.commit();
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return empresa;
    }
}
