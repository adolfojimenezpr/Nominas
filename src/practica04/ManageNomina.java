/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package practica04;

import java.util.Iterator;
import java.util.List;
import nominas.entity.Nomina;
import nominas.entity.Trabajadorbbdd;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author jimen
 */
public class ManageNomina {
    
    
    public Integer addNomina(Trabajadorbbdd trabajadorbbdd, int mes, int anio, int numeroTrienios, double importeTrienios, double importeSalarioMes, double importeComplementoMes, double valorProrrateo, double brutoAnual, double irpf, double importeIrpf, double baseEmpresario, double seguridadSocialEmpresario, double importeSeguridadSocialEmpresario, double desempleoEmpresario, double importeDesempleoEmpresario, double formacionEmpresario, double importeFormacionEmpresario, double accidentesTrabajoEmpresario, double importeAccidentesTrabajoEmpresario, double fogasaempresario, double importeFogasaempresario, double seguridadSocialTrabajador, double importeSeguridadSocialTrabajador, double desempleoTrabajador, double importeDesempleoTrabajador, double formacionTrabajador, double importeFormacionTrabajador, double brutoNomina, double liquidoNomina, double costeTotalEmpresario){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer nominaID = null;
        
        
        try{
            tx = session.beginTransaction();
            Nomina nomina = new Nomina(trabajadorbbdd, mes, anio, numeroTrienios, importeTrienios, importeSalarioMes, importeComplementoMes, valorProrrateo, brutoAnual, irpf, importeIrpf, baseEmpresario, seguridadSocialEmpresario, importeSeguridadSocialEmpresario, desempleoEmpresario, importeDesempleoEmpresario, formacionEmpresario, importeFormacionEmpresario, accidentesTrabajoEmpresario, importeAccidentesTrabajoEmpresario, fogasaempresario, importeFogasaempresario, seguridadSocialTrabajador, importeSeguridadSocialTrabajador, desempleoTrabajador, importeDesempleoTrabajador, formacionTrabajador, importeFormacionTrabajador, brutoNomina, liquidoNomina, costeTotalEmpresario);
            nominaID = (Integer) session.save(nomina);
            tx.commit();
        }catch(HibernateException e){
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        
        
        return nominaID;
        
    }
    
    public List listaNomina(){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List nominasList = null;
        
        try{
            
            tx = session.beginTransaction();
            nominasList = session.createQuery("FROM Nomina").list();
            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
        }
        
        return nominasList;
    }
    
    
    public boolean existeEnBBDDNomina(int mes, int anio, Trabajadorbbdd trabajadorbbdd, double brutoNomina, double liquidoNomina){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        List nominasList = null;
        boolean flag = false;
        
        
        try{
            
            tx = session.beginTransaction();
            nominasList = session.createQuery("FROM Nomina").list();
            
            for(Iterator iterator = nominasList.iterator(); iterator.hasNext();){
                
                Nomina nomina = (Nomina) iterator.next();
                
                if(nomina.getMes() == mes && nomina.getAnio() == anio && nomina.getTrabajadorbbdd().getIdTrabajador().equals(trabajadorbbdd.getIdTrabajador()) && nomina.getBrutoNomina() == brutoNomina && nomina.getLiquidoNomina() == liquidoNomina){
                    
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
    
    
    public void updateNomina(Integer nominaID, Trabajadorbbdd trabajadorbbdd, int mes, int anio, int numeroTrienios, double importeTrienios, double importeSalarioMes, double importeComplementoMes, double valorProrrateo, double brutoAnual, double irpf, double importeIrpf, double baseEmpresario, double seguridadSocialEmpresario, double importeSeguridadSocialEmpresario, double desempleoEmpresario, double importeDesempleoEmpresario, double formacionEmpresario, double importeFormacionEmpresario, double accidentesTrabajoEmpresario, double importeAccidentesTrabajoEmpresario, double fogasaempresario, double importeFogasaempresario, double seguridadSocialTrabajador, double importeSeguridadSocialTrabajador, double desempleoTrabajador, double importeDesempleoTrabajador, double formacionTrabajador, double importeFormacionTrabajador, double brutoNomina, double liquidoNomina, double costeTotalEmpresario){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        
        try{
            
            tx = session.beginTransaction();
            
            Nomina nomina = (Nomina)session.get(Nomina.class, nominaID);
            
            nomina.setTrabajadorbbdd(trabajadorbbdd);
            nomina.setMes(mes);
            nomina.setAnio(anio);
            nomina.setNumeroTrienios(numeroTrienios);
            nomina.setImporteTrienios(importeTrienios);
            nomina.setImporteSalarioMes(importeSalarioMes);
            nomina.setImporteComplementoMes(importeComplementoMes);
            nomina.setValorProrrateo(valorProrrateo);
            nomina.setBrutoAnual(brutoAnual);
            nomina.setIrpf(irpf);
            nomina.setImporteIrpf(importeIrpf);
            nomina.setBaseEmpresario(baseEmpresario);
            nomina.setSeguridadSocialEmpresario(seguridadSocialEmpresario);
            nomina.setImporteSeguridadSocialEmpresario(importeSeguridadSocialEmpresario);
            nomina.setDesempleoEmpresario(desempleoEmpresario);
            nomina.setImporteDesempleoEmpresario(importeDesempleoEmpresario);
            nomina.setFormacionEmpresario(formacionEmpresario);
            nomina.setImporteFogasaempresario(importeFogasaempresario);
            nomina.setAccidentesTrabajoEmpresario(accidentesTrabajoEmpresario);
            nomina.setImporteAccidentesTrabajoEmpresario(importeAccidentesTrabajoEmpresario);
            nomina.setFogasaempresario(fogasaempresario);
            nomina.setImporteFogasaempresario(importeFogasaempresario);
            nomina.setSeguridadSocialTrabajador(seguridadSocialTrabajador);
            nomina.setImporteSeguridadSocialTrabajador(importeSeguridadSocialTrabajador);
            nomina.setDesempleoTrabajador(desempleoTrabajador);
            nomina.setImporteDesempleoTrabajador(importeDesempleoTrabajador);
            nomina.setFormacionTrabajador(formacionTrabajador);
            nomina.setImporteFormacionTrabajador(importeFormacionTrabajador);
            nomina.setBrutoNomina(brutoNomina);
            nomina.setLiquidoNomina(liquidoNomina);
            nomina.setCosteTotalEmpresario(costeTotalEmpresario);
            
            
            session.update(nomina);
            tx.commit();
            
        }catch(HibernateException e){
            if(tx!=null) tx.rollback();
            e.printStackTrace();
        }finally{
            session.close();
            
        }
        
        
        
    }
    
}
