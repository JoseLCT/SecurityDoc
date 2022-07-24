package arbol;

import java.io.*;

public class Arbol<E> implements Serializable {

    protected Nodo<E> raiz;
    protected Nodo<E> actual;
    protected String ruta_guardado;
    protected String nombre_guardado;

    public Arbol() {
        this.raiz = null;
        this.actual = null;
        this.ruta_guardado = "";
        this.nombre_guardado = "";
    }

    public void insertar(E o, String id, String idPadre, String tipo) {
        Nodo<E> nuevo = new Nodo<>(o, id, idPadre);
        if (raiz == null && idPadre == null) {
            raiz = nuevo;
            return;
        }
        Nodo<E> aux = buscar(idPadre);
        aux.getHijos().adicionar(nuevo);
    }

    public Nodo<E> buscar(String id) {
        return raiz.encontrar(id);
    }

    @Override
    public String toString() {
        if (raiz == null) {
            return "[VACIO]";
        }
        return raiz.toString();
    }

    public Nodo<E> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<E> raiz) {
        this.raiz = raiz;
    }

    public Nodo<E> getActual() {
        if (actual == null) {
            actual = raiz;
        }
        return actual;
    }

    public void setActual(Nodo<E> actual) {
        this.actual = actual;
    }

    public boolean verificar(String id) {
        Nodo<E> aux = raiz.encontrar(id);
        if (aux == null) {
            return false;
        }
        return true;
    }

    public void guardarArbol(Arbol<E> arbolActual, String ruta_directorio) throws Exception {
        if (arbolActual.getRuta_guardado() == "") {
            File directorio = new File(ruta_directorio);
            String[] lista = directorio.list();
            int cantidad_arboles = 0;
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].startsWith("arbol") && lista[i].endsWith(".dat")) {
                    cantidad_arboles++;
                }
            }
            if (cantidad_arboles != 0) {
                ruta_guardado = ruta_directorio + "arbol (" + (cantidad_arboles + 1) + ").dat";
                nombre_guardado = "arbol (" + (cantidad_arboles + 1) + ").dat";
            } else {
                ruta_guardado = ruta_directorio + "arbol.dat";
                nombre_guardado = "arbol.dat";
            }
            arbolActual.setRuta_guardado(ruta_guardado);
        }
        ObjectOutputStream escritura = new ObjectOutputStream(new FileOutputStream(arbolActual.getRuta_guardado()));
        escritura.writeObject(arbolActual);
        escritura.close();
    }

    public Arbol<E> cargarArbol(File arbolNuevo) throws Exception {
        ObjectInputStream lectura = new ObjectInputStream(new FileInputStream(arbolNuevo));
        Arbol<E> arbolAux = (Arbol<E>) lectura.readObject();
        lectura.close();
        return arbolAux;
    }

    public void setRuta_guardado(String ruta_guardado) {
        this.ruta_guardado = ruta_guardado;
    }

    public String getRuta_guardado() {
        return ruta_guardado;
    }

    public String getNombre_guardado() {
        return nombre_guardado;
    }

    public void setNombre_guardado(String nombre_guardado) {
        this.nombre_guardado = nombre_guardado;
    }

    public String getCarpetaActual() {
        return actual.getId();
    }

    public static class Nodo<E> implements Serializable {

        private String id;
        private E contenido;
        private Lista<Nodo<E>> hijos;
        private String padreId;

        public Nodo(E c, String id, String padreId) {
            this.hijos = new Lista<>();
            this.contenido = c;
            this.id = id;
            this.padreId = padreId;
        }

        public E getContenido() {
            return contenido;
        }

        public void setContenido(E contenido) {
            this.contenido = contenido;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Lista<Nodo<E>> getHijos() {
            return hijos;
        }

        public void setHijos(Lista<Nodo<E>> hijos) {
            this.hijos = hijos;
        }

        @Override
        public String toString() {
            StringBuilder resultado = new StringBuilder();
            resultado.append(id);
            if (hijos.tamano() == 0) {
                return resultado.toString();
            }
            System.out.println(hijos.tamano());
            resultado.append("(");
            String separador = "";
            for (Nodo<E> hijo :
                    hijos) {
                resultado.append(separador);
                resultado.append(hijo.toString());
                separador = ",";
            }

            resultado.append(")");

            return resultado.toString();
        }

        //se busca en todo el arbol
        public Nodo<E> encontrar(String id) {
            if (this.id.equalsIgnoreCase(id)) {
                return this;
            }
            for (Nodo<E> hijo : hijos) {
                Nodo<E> encontrado = hijo.encontrar(id);
                if (encontrado != null) {
                    return encontrado;
                }
            }
            return null;
        }

        //se busca cuantas veces se repite ese id en los hijos del nodo actual
        public int encontrar_actual(Nodo<E> nodo, String id) {
            int contador = 0;
            String aux = id;
            for (int i = 0; i < nodo.getHijos().tamano(); i++) {
                if (nodo.getHijos().obtener(i).getId().equalsIgnoreCase(aux)) {
                    contador++;
                    aux = id + " (" + contador + ")";
                }
            }
            return contador;
        }

        public String getPadreId() {
            return padreId;
        }

        public void setPadre(Nodo<E> padre) {
            if (padre == null) {
                return;
            }
            for (Nodo<E> hijo :
                    padre.hijos) {
                if (hijo.equals(this)) {
                    return;
                }
            }
            padre.getHijos().adicionar(this);
        }
    }
}


