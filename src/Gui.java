import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{

    public Gui() {
        super("Bibliothek Verleih");

        JPanel centerJPanel = new JPanel();
        JLabel Buch = new JLabel(DataBaseControl.getBuch());
        centerJPanel.add(Buch);

        this.add(centerJPanel, BorderLayout.CENTER);

        JPanel bottomJPanel = new JPanel();
        JTextField isbnEingabeAdd = new JTextField(10);
        bottomJPanel.add(isbnEingabeAdd);
        JTextField titelEingabeAdd = new JTextField(10);
        bottomJPanel.add(titelEingabeAdd);
        JTextField verliehenEingabeAdd = new JTextField(1);
        bottomJPanel.add(verliehenEingabeAdd);

        JButton addButton = new JButton("Add");
        bottomJPanel.add(addButton);

        addButton.addActionListener(e -> {
            int isbnNr = Integer.parseInt(isbnEingabeAdd.getText());
            int verliehen = Integer.parseInt(verliehenEingabeAdd.getText());
            DataBaseControl.setBuch(isbnNr, titelEingabeAdd.getText(), verliehen);
        });


        JTextField isbnEingabeDelete = new JTextField(10);
        bottomJPanel.add(isbnEingabeDelete);

        JButton deleteButton = new JButton("Delete");
        bottomJPanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int isbnNrDelete = Integer.parseInt(isbnEingabeDelete.getText());
            DataBaseControl.deleteBuch(isbnNrDelete);
        });
        

        this.add(bottomJPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setVisible(true);
    }
    
}
