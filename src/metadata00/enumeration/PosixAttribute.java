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
public enum PosixAttribute
{
    /* instances enum */
    GROUP("posix:group"),
    PERMISSIONS("posix:permissions");
    
    /* instance variable */
    private String value;
    
    /* constructor */
    private PosixAttribute(String value)
    {
        this.value = value;
    }
    
    /* methods */
    public String get()
    {
        return this.value;
    }
}
