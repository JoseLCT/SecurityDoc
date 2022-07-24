package interfaz;

import arbol.Arbol;
import archivo_carpeta.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;

public class Panel_principal extends JPanel {

    private JButton btn_volver;
    private JButton btn_cargarArbol;
    private JButton btn_guardarArbol;
    private JButton btn_agregarArchivo;
    private JButton btn_agregarCarpeta;
    private JButton btn_setDirectorio;

    private JLabel lb_guardarArbol;
    private JLabel lb_agregarArchivo;
    private JLabel lb_agregarCarpeta;
    private JLabel lb_setDirectorio;

    private ImageIcon icono_volver = new ImageIcon("volver.png");
    private ImageIcon icono_cargarArbol = new ImageIcon("subir.png");
    private ImageIcon icono_guardarArbol = new ImageIcon("guardar.png");
    private ImageIcon icono_agregarArchivo = new ImageIcon("agregar-archivo.png");
    private ImageIcon icono_agregarCarpeta = new ImageIcon("agregar-carpeta.png");
    private ImageIcon icono_setDirectorio = new ImageIcon("actualizar.png");
    private ImageIcon icono_carpeta = new ImageIcon("carpeta.png");

    private JLabel lb_ruta;
    private JLabel lb_carpetaActual;
    private JLabel lb_imgCarpeta;

    private JSeparator separador1;
    private JSeparator separador2;
    private JSeparator separador3;
    private JSeparator separador4;
    private JSeparator separador5;

    private JTable tabla;
    private JScrollPane jScrollPane;
    private DefaultTableModel modelo;
    private Arbol arbol;

    public String[] columnNames = {"Nombre ID", "Nombre original", "Tipo", "Tamaño"};
    public Object[][] data = {};

    public Panel_principal() {
        setSize(900, 600);
        setLayout(null);
        init();
    }

    private void init() {
        arbol = new Arbol<ArchivoCarpeta>();
        arbol.insertar(Configuracion.getInstance(), "Directorio_base", null, "Directorio");
        tabla = new JTable() {
            //para que no se pueda editar los datos de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.addMouseListener(new MouseAdapter() {
            //se verifica si dio doble click en la tabla
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.getSelectedRow(); //se obtiene la fila seleccionada
                    if (tabla.getValueAt(fila, 2).equals("Carpeta")) {
                        String id = tabla.getValueAt(fila, 0).toString(); //se obtiene el id de la carpeta
                        arbol.setActual(arbol.buscar(id));
                        lb_ruta.setText(lb_ruta.getText() + id + "\\");
                        btn_volver.setEnabled(true);
                        lb_carpetaActual.setText(id);
                        actualizarTabla();
                    } else {
                        String id = tabla.getValueAt(fila, 0).toString(); //se obtiene el id del archivo
                        JFileChooser fc_copiarArchivo = new JFileChooser();
                        fc_copiarArchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        fc_copiarArchivo.setCurrentDirectory(new File("C:\\Users\\Usuario\\Desktop\\Universidad\\3er semestre\\Programacion 3"));
                        int opcion = fc_copiarArchivo.showOpenDialog(null);
                        if (opcion == JFileChooser.APPROVE_OPTION) {
                            Archivo archivo_seleccionado = (Archivo) arbol.buscar(id).getContenido();
                            File carpeta_destino = fc_copiarArchivo.getSelectedFile();
                            try {
                                archivo_seleccionado.copiar(archivo_seleccionado, carpeta_destino);
                            } catch (Exception error) {
                                error.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        tabla.setFocusable(false); //para que no se pueda seleccionar una celda de la tabla
        tabla.getTableHeader().setReorderingAllowed(false); //para que no se pueda reordenar la tabla
        jScrollPane = new JScrollPane(tabla);
        btn_volver = new JButton();
        btn_cargarArbol = new JButton();
        btn_guardarArbol = new JButton();
        btn_agregarArchivo = new JButton();
        btn_agregarCarpeta = new JButton();
        btn_setDirectorio = new JButton();
        lb_ruta = new JLabel(Configuracion.getInstance().getDirectorioBase());
        lb_carpetaActual = new JLabel(arbol.getActual().getId());
        lb_imgCarpeta = new JLabel();

        lb_guardarArbol = new JLabel("GUARDAR", SwingConstants.CENTER);
        lb_agregarArchivo = new JLabel("<html><center>AGREGAR ARCHIVO");
        lb_agregarCarpeta = new JLabel("<html><center>AGREGAR CARPETA");
        lb_setDirectorio = new JLabel("<html><center>CAMBIAR DIRECTORIO");

        lb_ruta.setFont(new Font("Poppins", Font.ITALIC, 12));
        lb_carpetaActual.setFont(new Font("Poppins", Font.PLAIN, 14));

        lb_guardarArbol.setFont(new Font("Poppins", Font.PLAIN, 12));
        lb_agregarArchivo.setFont(new Font("Poppins", Font.PLAIN, 12));
        lb_agregarCarpeta.setFont(new Font("Poppins", Font.PLAIN, 12));
        lb_setDirectorio.setFont(new Font("Poppins", Font.PLAIN, 12));

        lb_ruta.setForeground(Color.WHITE);
        lb_carpetaActual.setForeground(Color.WHITE);

        lb_guardarArbol.setForeground(Color.WHITE);
        lb_agregarArchivo.setForeground(Color.WHITE);
        lb_agregarCarpeta.setForeground(Color.WHITE);
        lb_setDirectorio.setForeground(Color.WHITE);

        lb_ruta.setOpaque(true);
        lb_ruta.setBackground(new Color(46, 46, 46));

        separador1 = new JSeparator();
        separador2 = new JSeparator();
        separador3 = new JSeparator();
        separador4 = new JSeparator();
        separador5 = new JSeparator();

        separador1.setOrientation(SwingConstants.VERTICAL);
        separador2.setOrientation(SwingConstants.VERTICAL);
        separador3.setOrientation(SwingConstants.VERTICAL);
        separador4.setOrientation(SwingConstants.VERTICAL);
        separador5.setOrientation(SwingConstants.VERTICAL);

        tabla.setModel(new DefaultTableModel(data, columnNames));
        jScrollPane.setBounds(200, 125, 787, 438);

        jScrollPane.getViewport().setBackground(Color.DARK_GRAY); //cambiar color del fondo del scrollpane
        tabla.getTableHeader().setBackground(Color.DARK_GRAY); //cambiar color del fondo de la cabecera de la tabla
        tabla.getTableHeader().setFont(new Font("Poppins", Font.PLAIN, 14)); //cambiar fuente de la cabecera de la tabla
        tabla.getTableHeader().setForeground(Color.WHITE); //cambiar color del texto de la cabecera de la tabla
        tabla.setBackground(Color.gray); //cambiar color del fondo de la tabla
        tabla.setForeground(Color.white); //cambiar color del texto de la tabla
        tabla.setFont(new Font("Poppins", Font.PLAIN, 12)); //cambiar fuente de la tabla
        tabla.setShowVerticalLines(false); //para que no se muestre la linea vertical de la tabla
        tabla.setShowHorizontalLines(false); //para que no se muestre la linea horizontal de la tabla

        btn_volver.setBounds(100, 100, 20, 20);
        btn_cargarArbol.setBounds(150, 100, 20, 20);
        btn_guardarArbol.setBounds(625, 15, 35, 35);
        btn_agregarArchivo.setBounds(245, 10, 40, 40);
        btn_agregarCarpeta.setBounds(365, 10, 45, 45);
        btn_setDirectorio.setBounds(495, 7, 50, 50);
        lb_ruta.setBounds(200, 100, 784, 25);
        lb_carpetaActual.setBounds(40, 126, 160, 25);
        lb_imgCarpeta.setBounds(5, 123, 25, 25);

        lb_guardarArbol.setBounds(610, 55, 70, 30);
        lb_agregarArchivo.setBounds(227, 55, 70, 40);
        lb_agregarCarpeta.setBounds(355, 55, 70, 40);
        lb_setDirectorio.setBounds(485, 55, 70, 40);

        separador1.setBounds(200, 10, 20, 50);
        separador2.setBounds(320, 10, 20, 50);
        separador3.setBounds(450, 10, 20, 50);
        separador4.setBounds(580, 10, 20, 50);
        separador5.setBounds(705, 10, 20, 50);

        btn_volver.setContentAreaFilled(false);
        btn_cargarArbol.setContentAreaFilled(false);
        btn_guardarArbol.setContentAreaFilled(false);
        btn_agregarArchivo.setContentAreaFilled(false);
        btn_agregarCarpeta.setContentAreaFilled(false);
        btn_setDirectorio.setContentAreaFilled(false);

        btn_volver.setBorder(null);
        btn_cargarArbol.setBorder(null);
        btn_guardarArbol.setBorder(null);
        btn_agregarArchivo.setBorder(null);
        btn_agregarCarpeta.setBorder(null);
        btn_setDirectorio.setBorder(null);

        btn_volver.setIcon(new ImageIcon(icono_volver.getImage().getScaledInstance(btn_volver.getWidth(), btn_volver.getHeight(), Image.SCALE_SMOOTH)));
        btn_cargarArbol.setIcon(new ImageIcon(icono_cargarArbol.getImage().getScaledInstance(btn_cargarArbol.getWidth(), btn_cargarArbol.getHeight(), Image.SCALE_SMOOTH)));
        btn_guardarArbol.setIcon(new ImageIcon(icono_guardarArbol.getImage().getScaledInstance(btn_guardarArbol.getWidth(), btn_guardarArbol.getHeight(), Image.SCALE_SMOOTH)));
        btn_agregarArchivo.setIcon(new ImageIcon(icono_agregarArchivo.getImage().getScaledInstance(btn_agregarArchivo.getWidth(), btn_agregarArchivo.getHeight(), Image.SCALE_SMOOTH)));
        btn_agregarCarpeta.setIcon(new ImageIcon(icono_agregarCarpeta.getImage().getScaledInstance(btn_agregarCarpeta.getWidth(), btn_agregarCarpeta.getHeight(), Image.SCALE_SMOOTH)));
        btn_setDirectorio.setIcon(new ImageIcon(icono_setDirectorio.getImage().getScaledInstance(btn_setDirectorio.getWidth(), btn_setDirectorio.getHeight(), Image.SCALE_SMOOTH)));
        lb_imgCarpeta.setIcon(new ImageIcon(icono_carpeta.getImage().getScaledInstance(lb_imgCarpeta.getWidth(), lb_imgCarpeta.getHeight(), Image.SCALE_SMOOTH)));

        btn_volver.setEnabled(false);

        setBackground(new Color(58, 58, 58));
        add(jScrollPane);
        add(btn_volver);
        add(btn_cargarArbol);
        add(btn_guardarArbol);
        add(btn_agregarArchivo);
        add(btn_agregarCarpeta);
        add(btn_setDirectorio);
        add(lb_ruta);
        add(lb_carpetaActual);
        add(lb_guardarArbol);
        add(lb_agregarArchivo);
        add(lb_agregarCarpeta);
        add(lb_setDirectorio);
        add(lb_imgCarpeta);
        add(separador1);
        add(separador2);
        add(separador3);
        add(separador4);
        add(separador5);

        btn_volver.addActionListener(e -> {
            btn_anteriorClicked();
        });
        btn_cargarArbol.addActionListener(e -> {
            btn_cargarArbolClicked();
        });
        btn_guardarArbol.addActionListener(e -> {
            btn_guardarArbolClicked();
        });
        btn_agregarArchivo.addActionListener(e -> {
            btn_agregarArchivoClicked();
        });
        btn_agregarCarpeta.addActionListener(e -> {
            btn_agregarCarpetaClicked();
        });
        btn_setDirectorio.addActionListener(e -> {
            btn_setDirectorioClicked();
        });
    }

    private void btn_anteriorClicked() {
        arbol.setActual(arbol.buscar(arbol.getActual().getPadreId()));
        int verificador = 0;
        for (int i = lb_ruta.getText().length() - 1; i > 0; i--) {
            if (lb_ruta.getText().charAt(i) == '\\') {
                verificador++;
            }
            if (verificador == 2) {
                lb_ruta.setText(lb_ruta.getText().substring(0, i + 1));
                break;
            }
        }
        if (Configuracion.getInstance().getDirectorioBase().equals(lb_ruta.getText())) {
            btn_volver.setEnabled(false);
        }
        lb_carpetaActual.setText(arbol.getActual().getId());
        actualizarTabla();
    }

    private void btn_cargarArbolClicked() {
        JFileChooser fc_arbol = new JFileChooser();
        fc_arbol.setCurrentDirectory(new File(Configuracion.getInstance().getDirectorioBase()));
        int seleccion = fc_arbol.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File arbolNuevo = fc_arbol.getSelectedFile();
            try {
                arbol = arbol.cargarArbol(arbolNuevo);
                actualizarTabla();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cargar el arbol");
            }
        }
    }

    private void btn_guardarArbolClicked() {
        try {
            arbol.guardarArbol(arbol, Configuracion.getInstance().getDirectorioBase());
            JOptionPane.showMessageDialog(null, "Arbol guardado \n" + "Archivo: " + arbol.getNombre_guardado() + " \n" + "Ruta: " + arbol.getRuta_guardado());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el arbol");
        }
    }

    private void btn_agregarArchivoClicked() {
        Archivo archivo = new Archivo();
        boolean verificador = true;
        String nombre_id = "";
        JFileChooser fc_archivo = new JFileChooser();
        fc_archivo.setCurrentDirectory(new File(Configuracion.getInstance().getDirectorioBase()));
        int seleccion = fc_archivo.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo_seleccionado = fc_archivo.getSelectedFile();
            String nombre = archivo_seleccionado.getName();
            try {
                //se genera el id del archivo
                while (verificador == true) {
                    nombre_id = new Archivo().generarId();
                    if (arbol.buscar(nombre_id) == null) {
                        archivo.setNombre_id(nombre_id);
                        verificador = false;
                    } else {
                        verificador = true;
                    }
                }
                //se obtiene el nombre y la extension del archivo seleccionado
                for (int i = nombre.length() - 1; i > 0; i--) {
                    if (nombre.charAt(i) == '.') {
                        archivo.setNombre(nombre.substring(0, i));
                        archivo.setTipo(nombre.substring(i, nombre.length()));
                        System.out.println(archivo.getNombre() + " " + archivo.getTipo());
                        break;
                    }
                }
                //se copia el archivo seleccionado en el directorio base
                File archivo_nuevo = new File(Configuracion.getInstance().getDirectorioBase() + "\\" + nombre_id);
                Files.copy(archivo_seleccionado.toPath(), archivo_nuevo.toPath());
                //se agregan los datos al archivo
                archivo.setTamano(archivo_seleccionado.length());
                archivo.setPadre((ArchivoCarpeta) arbol.getActual().getContenido());
                archivo.setRuta(archivo_nuevo.getAbsolutePath());
                archivo.encriptar(archivo);
                arbol.insertar(archivo, archivo.getNombre_id(), arbol.getActual().getId(), archivo.getTipo());
                actualizarTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo para agregar una carpeta al arbol y que se actualice la tabla
     * En caso de que el nombre de esa carpeta se repita se agrega el numero que le corresponde al final del nombre
     */
    private void btn_agregarCarpetaClicked() {
        Carpeta carpeta = new Carpeta();
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la carpeta");
        nombre = carpeta.eliminarEspacios(nombre);
        if (nombre.equals("")) {
            return;
        }
        int cantidad = arbol.getActual().encontrar_actual(arbol.getActual(), nombre);
        if (cantidad != 0) {
            carpeta.setNombre(nombre + " (" + cantidad + ")");
        } else {
            carpeta.setNombre(nombre);
        }
        carpeta.setTipo("Carpeta");
        carpeta.setPadre((ArchivoCarpeta) arbol.getActual().getContenido());
        arbol.insertar(carpeta, carpeta.getNombre(), arbol.getActual().getId(), carpeta.getTipo());
        actualizarTabla();
        System.out.println(arbol.toString());
    }

    private void btn_setDirectorioClicked() {
        JFileChooser fc_directorio = new JFileChooser();
        fc_directorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc_directorio.setAcceptAllFileFilterUsed(false);
        fc_directorio.setCurrentDirectory(new File(Configuracion.getInstance().getDirectorioBase()));
        int seleccion = fc_directorio.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            Configuracion.getInstance().setDirectorioBase(fc_directorio.getSelectedFile().getAbsolutePath() + "\\");
            arbol.setRaiz(null);
            arbol.insertar(Configuracion.getInstance(), fc_directorio.getSelectedFile().getName(), null, "Directorio");
            arbol.setActual(arbol.getRaiz());
            lb_ruta.setText(Configuracion.getInstance().getDirectorioBase());
            lb_carpetaActual.setText(arbol.getActual().getId());
            System.out.println(arbol.toString());
        }
    }

    /**
     * Metodo para actualizar la tabla con los datos del arbol, busca el nodo actual
     * y añade los datos de sus hijos a la tabla
     */
    private void actualizarTabla() {
        modelo = new DefaultTableModel(data, columnNames);
        int cantidadHijos = arbol.getActual().getHijos().tamano();
        for (int i = 0; i < cantidadHijos; i++) {
            //se obtiene el nodo hijo y se castea a "Arbol.Nodo" para tener su ID, luego se busca en el arbol
            //el nodo con ese ID y se obtiene su contenido, ese contenido se debe castear a "ArchivoCarpeta"
            //para poder obtener los datos de la carpeta o archivo
            ArchivoCarpeta elemento_nuevo = (ArchivoCarpeta) arbol.buscar(((Arbol.Nodo) arbol.getActual().getHijos().obtener(i)).getId()).getContenido();
            if (elemento_nuevo.getTipo().equals("Carpeta")) {
                modelo.addRow(new Object[]{elemento_nuevo.getNombre(), null, elemento_nuevo.getTipo(), null});
            } else {
                Archivo archivo = (Archivo) elemento_nuevo;
                modelo.addRow(new Object[]{archivo.getNombre_id(), archivo.getNombre(), archivo.getTipo(), archivo.getTamano() + " KB"});
            }
        }
        tabla.setModel(modelo);
    }
}