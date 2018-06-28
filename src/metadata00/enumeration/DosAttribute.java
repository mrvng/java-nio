/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata00.enumeration;

/**
 *
 * @author gb
 */
public enum DosAttribute
{
    /* instances enum */
     HIDDEN("dos:hidden"),
     READONLY("dos:readonly"),
     SYSTEM("dos:system"),
     ARCHIVE("dos:archive");
     
     /* instance variable */ 
     private String value;
     
     /* constructor */
     private DosAttribute(String value)
     {
         this.value = value;
     }
     
     /* methods */
     public String get()
     {
         return this.value;
     }
}
