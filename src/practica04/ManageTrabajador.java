/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica04;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import nominas.entity.Categorias;
import nominas.entity.Empresas;
import nominas.entity.Trabajadorbbdd;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author jimen
 */
public class ManageTrabajador {
    
    public Trabajadorbbdd addTrabajador(Categorias categorias, Empresas empresas, String nombre, String apellido1, String apellido2, String nifnie, String email, Date fechaAlta, String codigoCuenta, String iban){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer trabajadorID = null;
        Trabajadorbbdd trabajador = null;
        
        try{
            
            tx = session.beginTransaction();
            trabajador = new Trabajadorbbdd(categorias, empresas, nombre, apellido1, apellido2, nifnie, email, fechaAlta, codigoCuenta, iban, null);

            
            
            trabajadorID = (Integer) session.save(trabajador);
            tx.commit();
            
        }catch(HibernateException e){
            if (tx!=null) tx.rollback();
            e.printStackTrace();            
        }finally{
            session.close();
            
        }

        return trabajador;
    }
    
    public List listaTrabajador(){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List trabajadorList = null;        
        
        
        try{
            tx = session.beginTransaction();
            trabajadorList = session.createQuery("FROM Trabajadorbbdd").list();
            

            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return trabajadorList;        
        
        
    }
    
    public boolean existeEnBBDDTrabajador(String nombre, String NIF, String fechaAlta){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List trabajadorList = null;
        boolean flag = false;        
        
        try{
            tx = session.beginTransaction();
            trabajadorList = session.createQuery("FROM Trabajadorbbdd").list();
            
            
            
            for(Iterator iterator = trabajadorList.iterator(); iterator.hasNext();){
                
                Trabajadorbbdd trabajador = (Trabajadorbbdd) iterator.next();
                
                if(trabajador.getNombre().equalsIgnoreCase(nombre) && trabajador.getNifnie().equalsIgnoreCase(NIF) && trabajador.getFechaAlta().toString().equalsIgnoreCase(fechaAlta)){
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
    
    public Trabajadorbbdd updateTrabajador(Integer trabajadorID, Categorias categorias, Empresas empresas, String nombre, String apellido1, String apellido2, String nifnie, String email, Date fechaAlta, String codigoCuenta, String iban){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Trabajadorbbdd trabajador = null;
        
        
        try{
            tx = session.beginTransaction();
            
            trabajador = (Trabajadorbbdd)session.get(Trabajadorbbdd.class, trabajadorID);
            
            trabajador.setCategorias(categorias);
            trabajador.setEmpresas(empresas);
            trabajador.setNombre(nombre);
            trabajador.setApellido1(apellido1);
            trabajador.setApellido2(apellido2);
            trabajador.setNifnie(nifnie);
            trabajador.setEmail(email);
            trabajador.setFechaAlta(fechaAlta);
            trabajador.setCodigoCuenta(codigoCuenta);
            trabajador.setIban(iban);
            trabajador.setNominas(null);
            
            
            
            session.update(trabajador);
            
            tx.commit();
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }        
        
        return trabajador;
        
    }
    
    
}
