import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame{
    private JPanel panelLog;
    private JTextField usuarioText;
    private JPasswordField passText;
    private JButton agregarAdministradorButton;
    private JButton validarButton;
    private JLabel USUARIO;
    private JLabel CONTRASEÑA;
    Connection conexion;
    PreparedStatement ps;
    Statement st;
    ResultSet r;

    public Login() {
        agregarAdministradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    ingresarAdministradores();
                    JOptionPane.showMessageDialog(null, "Registro Exitoso");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Registro fallido " + e.getMessage() );
                }
            }
        });
        validarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                validarAdministrador();
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
    void ingresarAdministradores() throws SQLException {
        conectar();
        ps = conexion.prepareStatement("insert into administradores(usuario, pass) values (?,?)");
        ps.setString(1, usuarioText.getText());
        ps.setString(2, String.valueOf(passText.getText()));
        ps.executeUpdate();

    }

    void validarAdministrador(){
        conectar();
        int validacion=0;
        String usuario = usuarioText.getText();
        String pass = String.valueOf(passText.getText());

        try{
            st = conexion.createStatement();
            r = st.executeQuery("Select * from administradores where usuario = '" +usuario+ "' and pass='"+pass+"'");
            if(r.next()){
                validacion=1;
                if(validacion==1){
                    Usuario enlazar = new Usuario();
                    enlazar.mostrarVentana();
                }
            }else {
                JOptionPane.showMessageDialog(null, "Usuario sin permisos de administración");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error "+ e.getMessage());

        }




    }
    public static void main(String[] args) {
        Login login1 = new Login();
        login1.setContentPane(new Login().panelLog);
        login1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login1.setVisible(true);
        login1.pack();
    }


}
