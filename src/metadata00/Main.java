/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata00;




import metadata00.enumeration.BasicAttribute;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import metadata00.enumeration.DosAttribute;
import metadata00.enumeration.OwnerAttribute;
import metadata00.enumeration.PosixAttribute;

/**
 *
 * @author gb
 */
public class Main
{

    public static void main(String[] args) throws IOException
    {
        /*  Metadata datos acerca de los datos  */
        
        /*
            BasicFileAttributeView
            DosFileAttributeView
            PosixFileAttributeView
            FileOwnerAttributeView
            AclFileAttributeView
        */
        
        /*
            FileStore torage for files. 
            A FileStore represents a 
                storage pool, 
                device, 
                partition, 
                volume, 
                concrete file system 
                or other implementation specific means of file storage. 
            The FileStore for where a file is stored is obtained by invoking getFileStore , 
            or all file stores can be enumerated by invoking the getFileStores 
        */
       
        System.out.println("----------------supportedFileAttributeViews------------------");
        FileSystems.getDefault().supportedFileAttributeViews().forEach(System.out::println);
        
        System.out.println("==========File Store Attributes=============");
        System.out.println("----Listado File Store del FileSystems.getDefault()-----");
        FileSystems.getDefault().getFileStores().forEach( s ->
        {
            try
            {
                System.out.printf("name: %s  Total Space: %d   Usable Space: %d  %s%n",
                                   s.name(), s.getTotalSpace() / 1024,
                                   s.getUsableSpace()/1024,
                                   s.supportsFileAttributeView(BasicFileAttributeView.class)
                                 );
                
            } catch (IOException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        System.out.println("-----Invocando Files.getFileStore(path)------");
        System.out.println(" para saber a que FileStore pertenece un archivo");
        System.out.println("---------------------------------------------------");
        System.out.println(Files.getFileStore(Paths.get("res")));
        
        /*
            Most file system implementations support a set of common attributes 
            (size, creation time, last accessed time, last modified time, etc.). 
            These attributes are grouped into a view named BasicFileAttributeView
        */
        
        
        /*  interface BasicFileAttributes
            Basic attributes associated with a file in a file system.
            Basic file attributes are attributes that are common to many file systems and consist 
            of mandatory and optional file attributes as defined by this interface.
            BasicFileAttributes
                public static <A extends BasicFileAttributes>
            
        */
        BasicFileAttributes bfa = null;     // snapshot not dynamic
        bfa = Files.readAttributes(Paths.get("res"), BasicFileAttributes.class);
        
        System.out.println("\n-------BasicFileAttributes------");
        System.out.println("directorio: " + bfa.isDirectory());
        System.out.println("fichero regular: " + bfa.isRegularFile());
        System.out.println("otro:  " + bfa.isOther());
        System.out.println("enlace simbolico: " + bfa.isSymbolicLink());
        System.out.println("tamaño: " + bfa.size());
        System.out.println("time creation:  "  + bfa.creationTime());
        System.out.println("last access:  " + bfa.lastAccessTime());
        System.out.println("last modified: " + bfa.lastModifiedTime());
        System.out.println("file key:  " + bfa.fileKey());
        
        System.out.println("\n------Other way--------");
        Arrays.asList(BasicAttribute.values())
                .forEach(baf->
                    {
                        try
                        {
                            System.out.println (baf + ": "  + baf.get() + ": " +                        
                                                Files.getAttribute(Paths.get("res"), baf.get()));
                        } catch (IOException ex)
                        {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    } );
        
        //(Files.getAttribute(Paths.get("res"), BasicAttribute.LAST_MODIFIED_TIME));
        /*
            Actualizamos los atributos de tiempo mediante 
            el metodo setTimes de BasicFileAttributeView que tiene como 
            parametros  3 objetos FileTime
            Podemos crear FileTime a partir de FileTime.fromXXX(yyyy)
        */
        System.out.println("\n----Actualizando attributes time ----");
        FileTime ft = FileTime.fromMillis(System.currentTimeMillis());
        Files.getFileAttributeView(Paths.get("res"), BasicFileAttributeView.class)
                .setTimes(ft, ft, ft);
        
        //bfa = Files.readAttributes(Paths.get("res"), BasicFileAttributes.class);
        System.out.println("time creation:  "  + bfa.creationTime());
        System.out.println("last access:  " + bfa.lastAccessTime());
        System.out.println("last modified: " + bfa.lastModifiedTime());
        
        //<editor-fold defaultstate="collapsed" desc="summary">
        /*  interface AttributeView
        An object that provides a read-only or updatable view of non-opaque values associated
        with an object in a filesystem.
        This interface is extended or implemented by specific attribute views that define
        the attributes supported by the view. A specific attribute view will typically define
        type-safe methods to read or update the attributes that it supports
        AttributeView
            FileAttributeView
                V extends FileAttributeView
        */
        /*
        Files.getFileAttributeView(Paths.get("res"), BasicFileAttributeView.class);
        AttributeView
        FileAttributeView
        V extends FileAttributeView


        Files.readAttributes(Paths.get("res"), BasicFileAttributes.class);
        BasicFileAttributes
            public static <A extends BasicFileAttributes>

        */
//</editor-fold>
        /*
            Tambien podemos actualizar estos atributos mediante el metodo static
            setAttibutes de la clase Files.
            Y consultarlos mediante su compañereos static
            getAttributes
        */
        System.out.println("\n-------Other Way------");
        ft = FileTime.fromMillis(System.currentTimeMillis());
        //Files.setAttribute(path, attribute, args, options)
        //Files.setAttribute(Paths.get("res"), BasicFileAttributestribute, args, options)
         Files.setAttribute(Paths.get("res"), BasicAttribute.CREATION_TIME.get(), ft);
         Files.setAttribute(Paths.get("res"), BasicAttribute.LAST_ACCES_TIME.get(), ft);
         Files.setAttribute(Paths.get("res"), BasicAttribute.LAST_MODIFIED_TIME.get(), ft);
         
         BasicAttribute baf = BasicAttribute.CREATION_TIME;
         System.out.println(baf.get() + " : " + Files.getAttribute(Paths.get("res"), baf.get()));
         
         baf = BasicAttribute.LAST_ACCES_TIME;
         System.out.println(baf.get() + " : " + Files.getAttribute(Paths.get("res"), baf.get()));
         
         baf = BasicAttribute.LAST_MODIFIED_TIME;
         System.out.println(baf.get() + " : " + Files.getAttribute(Paths.get("res"), baf.get()));
               
         
        System.out.println("\nAparentemente, una vez creado el enum BasicFileAttribute \n"
                + "parece mas comodo utilizar Files.setAttribute y Files.getAttribute\n" );
        
        
        System.out.println("\n\n============DosFileAttributeView====================");
        /*
            al igual que antes haciamos
                BasicFileAttributes bfa = null;     // snapshot not dynamic
                bfa = Files.readAttributes(Paths.get("res"), BasicFileAttributes.class);
            ahora hacemos
        */
        DosFileAttributes dfa = null;
        Path path = Paths.get("res", "dir_file", "another_file" );
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        
        System.out.println("Is read only ? "+ dfa.isReadOnly()); 
        System.out.println("Is Hidden ? " + dfa.isHidden());
        System.out.println("Is archive ? " + dfa.isArchive());
        System.out.println("Is system ? " + dfa.isSystem());
        
        /*
            set-get
                dos:hidden
                dos:readonly
                dos:system
                dos:archive
        */
        /*
            remember
                snapshot not dynamic
        */
        System.out.println("===============================");
        Files.setAttribute(path, "dos:hidden", true);
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        System.out.println("Is Hidden ? " + dfa.isHidden());
        
        Files.setAttribute(path, "dos:hidden", false);
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        System.out.println("Is Hidden ? " + dfa.isHidden());
        
        Files.setAttribute(path, "dos:readonly", true);
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        System.out.println("Is read only ? "+ dfa.isReadOnly()); 
        
        Files.setAttribute(path, "dos:readonly", false);
        dfa = Files.readAttributes(path, DosFileAttributes.class);
        System.out.println("Is read only ? "+ dfa.isReadOnly()); 
        System.out.println("===========================");
        Arrays.asList(DosAttribute.values()).stream()
                .forEach(dos -> 
                {
                    try
                    {
                        System.out.println (dos + ": "  + dos.get() + ": " 
                                + Files.getAttribute(Paths.get("res", "dir_file", "another_file" ) 
                                                     , dos.get()));
                    } catch (IOException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        
        System.out.println("\n====================File Owner View=========================");
        /*
            interface Principal representing an identity used to determine access rights to objects 
            in a file system
            interface UserPrincipal extends Principal
            UserPrincipal object is an abstract representation of an identity. 
            It has a name that is typically the username or account name that it represents.
            User principal objects may be obtained using a UserPrincipalLookupService, 
            User principal objects may be obtained using   FileAttributeView
            FileOwnerAttributeView extends FileAttributeView
            
            al igual que antes haciamos
                BasicFileAttributes bfa = null;     // snapshot not dynamic
                bfa = Files.readAttributes(Paths.get("res"), BasicFileAttributes.class);
            y tambien haciamos
                DosFileAttributes dfa = null;
                Path path = Paths.get("res", "dir_file", "another_file" );
                dfa = Files.readAttributes(path, DosFileAttributes.class);
            ahora hacemos
        
        */
        
        path = Paths.get("res", "dir_file", "another_file" );
        FileOwnerAttributeView foav;
        foav = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        System.out.println(foav.getOwner().getName());
        
        System.out.println("------Other way------");
        System.out.println(Files.getAttribute(path, OwnerAttribute.OWNER.get()));
        
        UserPrincipal owner = null;
        owner = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("gb");
        /*UserPrincipalNotFoundException */
        Files.setAttribute(path, OwnerAttribute.OWNER.get(), owner);
        
        System.out.println("\n==============Posix view===============");
        System.out.println(Files.getAttribute(path, "posix:group"));
        System.out.println(Files.getAttribute(path, "posix:permissions"));
        
        System.out.println("\n------------Other Way--------------");
        System.out.print(PosixAttribute.GROUP + ": ");
        System.out.println(Files.getAttribute(path, PosixAttribute.GROUP.get()));
        System.out.print(PosixAttribute.PERMISSIONS + ": ");
        System.out.println(Files.getAttribute(path, PosixAttribute.PERMISSIONS.get()));
        
        GroupPrincipal group = null;
        group = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName("gb");
        Files.setAttribute(path, PosixAttribute.GROUP.get(), group);
        
        /* U G O ------ User Group Other */
        
        System.out.println("-----Change permission-----");
        Set<PosixFilePermission> permissionSet = PosixFilePermissions.fromString("rw-r--r--");
        Files.setPosixFilePermissions(path, permissionSet);
        System.out.print(PosixAttribute.PERMISSIONS + ": ");
        System.out.println(Files.getAttribute(path, PosixAttribute.PERMISSIONS.get()));
        
        System.out.println("-----Again, Change permission-----");
        permissionSet = PosixFilePermissions.fromString("rw-rw-r--");
        Files.setPosixFilePermissions(path, permissionSet);
        System.out.print(PosixAttribute.PERMISSIONS + ": ");
        System.out.println(Files.getAttribute(path, PosixAttribute.PERMISSIONS.get()));
        
        System.out.println("\n==========ACL Access Control List===========");
        System.out.print("Soporta ACL ? : ");
        System.out.println(Files.getFileStore(path).supportsFileAttributeView(AclFileAttributeView.class));
        
        System.out.println("\n==========User-Defined Attributes===========");
        System.out.print("User-Defined Attributes ? : ");
        System.out.println(Files.getFileStore(path).supportsFileAttributeView(UserDefinedFileAttributeView.class));
        
        
        System.out.println("\n\n\n===========Resumen===========");
        /*
            Files.setAttribute(path, XxxAttribute.yyyy.get(), value);
            Files.getAttribute(path, XxxAttribute.yyyy.get());
                                     BasicAttribute
                                     DosAttribute
                                     OwnerAttribute
                                     PosixAtribute
        */
        
        
        
        
    }
    
}
