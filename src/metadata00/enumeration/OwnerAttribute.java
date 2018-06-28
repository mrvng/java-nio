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
public enum OwnerAttribute
{
    /* instances enum */
    OWNER("owner:owner");
    
    /* instance variable */
    private String value;
    
    /* constructor */
    private OwnerAttribute(String value)
    {
        this.value = value;
    }
    
    /* method */
    public String get()
    {
        return this.value;
    }

}
