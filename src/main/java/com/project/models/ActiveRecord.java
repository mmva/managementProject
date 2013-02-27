package com.project.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;

public class ActiveRecord <T extends ActiveRecord> {
    
    public boolean create(EntityManager em){ 
        EntityTransaction et = em.getTransaction();
        
        try{
            et.begin(); // Importante: Iniciar la transaccion
            
            this.createNoTransaction(em);

            et.commit();                    
        } catch (Exception e) {
            if(et.isActive()) et.rollback();
            System.err.println("ERROR TRANSACTION: " + e.getMessage());
            return Boolean.FALSE;
        }                        
        
        return Boolean.TRUE;
    }
    
    public void createNoTransaction(EntityManager em){        
        em.persist(this);
        em.flush();
    } 
    
    public T update(EntityManager em){
        EntityTransaction et = em.getTransaction();
        T object = null;
        
        try{
            et.begin(); // Importante: Iniciar la transaccion
            object = this.updateNoTransaction(em);
            et.commit();                    
        } catch(IllegalStateException ise) { 
            et.rollback();
            System.err.println("ERROR STATE TRANSACCION: " + ise.getMessage());            
        } catch(TransactionRequiredException tre) { 
            et.rollback();
            System.err.println("ERROR STATE TRANSACCION: " + tre.getMessage());            
        }catch (Exception e) {
            et.rollback();
            System.err.println("ERROR GENERICO: " + e.getMessage());
        }        
        
        return object;
    }
    
    public T updateNoTransaction(EntityManager em){        
        T updatedObject = (T) em.merge(this); // Tal y como esta escrito, no seria necesario. Esta en Managed (gestionado)        
        em.flush(); 
        
        System.out.println("UPDATE: " + updatedObject);
        
        return updatedObject;
    } 
    
    public boolean delete(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        
        try{
            et.begin(); // Importante: Iniciar la transaccion
            this.deleteNoTransaction(em);
            et.commit();                    
        } catch(IllegalStateException ise) { 
            if(et.isActive()) et.rollback();
            System.err.println("ERROR STATE TRANSACCION: " + ise.getMessage()); 
            return Boolean.FALSE;
        } catch(TransactionRequiredException tre) { 
            if(et.isActive()) et.rollback();
            System.err.println("ERROR STATE TRANSACCION: " + tre.getMessage()); 
            return Boolean.FALSE;
        }catch (Exception e) {
            if(et.isActive()) et.rollback();
            System.err.println("ERROR GENERICO: " + e.getMessage());
            return Boolean.FALSE;
        }        
        
        return Boolean.TRUE;
    }
    
    public void deleteNoTransaction(EntityManager em) {
        em.remove(this); // Tal y como esta escrito, no seria necesario. Esta en Managed (gestionado)        
        em.flush();        
    }    
}
