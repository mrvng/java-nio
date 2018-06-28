package metadata00.enumeration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gb
 */
public enum BasicAttribute
{
    // instance enum
    CREATION_TIME("basic:creationTime"),
    LAST_ACCES_TIME("basic:lastAccessTime"),
    LAST_MODIFIED_TIME("basic:lastModifiedTime"),
    DIRECTORY("basic:isDirectory"),
    REGULAR_FILE("basic:isRegularFile"),
    SYMBOLIC_LINK("basic:isSymbolicLink"),
    OTHER("basic:isOther"),
    SIZE("basic:size"),
    FILE_KEY("basic:fileKey");
    
    // variable instance
    private final String value;
    
    // constructor
    private BasicAttribute(String value)
    {
        this.value = value;
    }
    
    // method
    public String get()
    {
        return this.value;
    }
}
