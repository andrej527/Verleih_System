import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{

    public Gui() {
        super("Bibliothek Verleih");

        JPanel centerJPanel = new JPanel();
        JLabel Buch = new JLabel(DataBaseControl.getBuch());
        centerJPanel.add(Buch);

        this.add(centerJPanel, BorderLayout.CENTER);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setVisible(true);
    }
    
}
