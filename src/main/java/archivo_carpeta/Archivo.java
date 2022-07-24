package archivo_carpeta;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Archivo extends ArchivoCarpeta {

    private String nombre_id;
    private long tamano;
    private String ruta;
    private SecretKey llave;

    public Archivo() {
    }

    public void encriptar(Archivo archivo) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        archivo.setLlave(keyGenerator.generateKey());
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, archivo.getLlave());
        byte[] bytes = Files.readAllBytes(Paths.get(archivo.getRuta()));
        byte[] bytesEncrypted = cipher.doFinal(bytes);
        Files.write(Paths.get(archivo.getRuta()), bytesEncrypted).toFile();
    }

    public void desencriptar(Archivo archivo) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, archivo.getLlave());
        byte[] bytes = Files.readAllBytes(Paths.get(archivo.getRuta()));
        byte[] bytesDescrypted = cipher.doFinal(bytes);
        Files.write(Paths.get(archivo.getRuta()), bytesDescrypted).toFile();
    }

    public void copiar(Archivo archivo_seleccionado, File carpeta_destino) throws Exception {
        archivo_seleccionado.desencriptar(archivo_seleccionado);
        File archivo_origen = new File(archivo_seleccionado.getRuta());
        String[] archivos_carpeta = carpeta_destino.list(); //se obtiene la lista de archivos de la carpeta destino
        int cantidad_archivos = 0; //se inicializa la cantidad de archivos en 0
        for (int i = 0; i < archivos_carpeta.length; i++) {
            if (archivos_carpeta[i].startsWith(archivo_seleccionado.getNombre()) && archivos_carpeta[i].endsWith(archivo_seleccionado.getTipo())) {
                cantidad_archivos++;
            }
        }
        String ruta_copia = "";
        if (cantidad_archivos != 0) {
            ruta_copia = carpeta_destino.getAbsolutePath() + "\\" + archivo_seleccionado.getNombre() + " (" + cantidad_archivos + ")" + archivo_seleccionado.getTipo();
        } else {
            ruta_copia = carpeta_destino.getAbsolutePath() + "\\" + archivo_seleccionado.getNombre() + archivo_seleccionado.getTipo();
        }
        File archivo_copia = new File(ruta_copia);
        Files.copy(archivo_origen.toPath(), archivo_copia.toPath());
        archivo_seleccionado.encriptar(archivo_seleccionado);
    }

    public String getNombre_id() {
        return nombre_id;
    }

    public void setNombre_id(String nombre_id) {
        this.nombre_id = nombre_id;
    }

    public long getTamano() {
        return tamano;
    }

    public void setTamano(long tamano) {
        this.tamano = tamano / 1000;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public SecretKey getLlave() {
        return llave;
    }

    public void setLlave(SecretKey llave) {
        this.llave = llave;
    }

    public String toString() {
        return "Archivo: " + this.nombre;
    }
}
