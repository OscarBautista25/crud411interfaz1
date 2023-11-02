import javax.swing.*;
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
    Connection conexion;
    PreparedStatement ps;
    DefaultListModel mod = new DefaultListModel();
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
        lista.setModel(mod);
        st = conexion.createStatement();
        r = st.executeQuery("Select id, nombre, rol from usuario");
        mod.removeAllElements();
        while (r.next()){
            mod.addElement(r.getString(1)+ " "+ r.getString(2) + " " + r.getString(3));
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

    public static void main(String[] args) {
        Usuario usuario1 = new Usuario();
        usuario1.setContentPane(new Usuario().panel);
        usuario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario1.setVisible(true);
        usuario1.pack();
    }

}
