import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Usuario extends JFrame {
    private JPanel panel;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField rolText;
    private JButton consultarBoton;
    private JButton ingresarBoton;
    private JList lista;
    private JTable tablaDatos;
    Connection conexion;
    PreparedStatement ps;
    String[] campos={"id", "nombre", "rol"};
    String[] registros = new String[10];
    DefaultListModel mod = new DefaultListModel();
    DefaultTableModel modTab = new DefaultTableModel(null, campos);
    Statement st;
    ResultSet r;


    public Usuario() {
        consultarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    consultar();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ingresarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    insertar();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Usuarios","root","1234");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    void consultar() throws SQLException {
        conectar();
        //lista.setModel(mod);
        tablaDatos.setModel(modTab);
        st = conexion.createStatement();
        r = st.executeQuery("Select id, nombre, rol from usuario");
        //mod.removeAllElements();
        while (r.next()){
            registros[0]= r.getString("id");
            registros[1]= r.getString("nombre");
            registros[2]= r.getString("rol");
            modTab.addRow(registros);
        }

    }
    void insertar() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("insert into usuario(id, nombre, rol) values (?,?,?)");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        ps.setString(2, nombreText.getText());
        ps.setString(3, rolText.getText());
        if (ps.executeUpdate()>0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Elemento agregado");

            idText.setText("");
            nombreText.setText("");
            rolText.setText("");
        }

    }

   public void mostrarVentana() {
        Usuario usuario1 = new Usuario();
        usuario1.setContentPane(new Usuario().panel);
        usuario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario1.setVisible(true);
        usuario1.pack();
    }

}
