package archivo_carpeta;
import java.io.Serializable;

public class Configuracion extends ArchivoCarpeta implements Serializable {

    private static Configuracion instance;
    private String directorioBase = "C:\\Users\\Usuario\\Desktop\\Universidad\\3er semestre\\Programacion 3\\Directorio_base\\";

    private Configuracion() {
    }

    public static Configuracion getInstance() {
        if (instance == null) {
            instance = new Configuracion();
        }
        return instance;
    }

    public String getDirectorioBase() {
        return directorioBase;
    }

    public void setDirectorioBase(String directorioBase) {
        this.directorioBase = directorioBase;
    }
}
