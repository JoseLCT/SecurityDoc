package archivo_carpeta;

public class Carpeta extends ArchivoCarpeta {

    /**
     * Se eliminan los espacios que tiene el nombre en el principio y final
     */
    public String eliminarEspacios(String nombre) {
        String nombre_sin_espacios = "";
        int p_inicial = 0;
        int p_final = 0;
        for (int i = 0; i < nombre.length(); i++) {
            if (nombre.charAt(i) != ' ') {
                p_inicial = i;
                break;
            }
        }
        for (int i = nombre.length() - 1; i > 0; i--) {
            if (nombre.charAt(i) != ' ') {
                p_final = i;
                break;
            }
        }
        nombre_sin_espacios = nombre.substring(p_inicial, p_final + 1);
        return nombre_sin_espacios;
    }

    public String toString() {
        return "Carpeta: " + this.nombre;
    }
}
