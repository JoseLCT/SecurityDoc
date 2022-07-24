package archivo_carpeta;

import java.io.Serializable;
import java.util.Random;

public class ArchivoCarpeta implements Serializable {

    protected String nombre;
    protected String tipo;
    protected ArchivoCarpeta padre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArchivoCarpeta getPadre() {
        return padre;
    }

    public void setPadre(ArchivoCarpeta padre) {
        this.padre = padre;
    }

    public String generarId() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder id = new StringBuilder();
        Random random = new Random();
        while (id.length() < 18) {
            // obtenemos un caracter aleatorio de la cadena de caracteres
            int posicion = (int) (random.nextFloat() * caracteres.length());
            id.append(caracteres.charAt(posicion));
        }
        return id.toString();
    }
}
