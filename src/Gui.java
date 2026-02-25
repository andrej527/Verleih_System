import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{


    public Gui() {
        super("Bibliothek Verleih");

        JPanel centerJPanel = new JPanel();
        JTextArea buchArea = new JTextArea(20, 60);
        buchArea.setEditable(false);
        buchArea.setText(DataBaseControl.getBuch());
        centerJPanel.add(new JScrollPane(buchArea));

        this.add(centerJPanel, BorderLayout.CENTER);

        JPanel bottomJPanel = new JPanel();
        JLabel eingabeLabel = new JLabel("Buch hinzufügen (ISBN / Titel / verliehen):");
        bottomJPanel.add(eingabeLabel);
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
            DataBaseControl.addBuch(isbnNr, titelEingabeAdd.getText(), verliehen);
            buchArea.setText(DataBaseControl.getBuch());
        });


        JLabel loeschenLabel = new JLabel("Buch löschen (ISBN): ");
        bottomJPanel.add(loeschenLabel);
        JTextField isbnEingabeDelete = new JTextField(10);
        bottomJPanel.add(isbnEingabeDelete);

        JButton deleteButton = new JButton("Delete");
        bottomJPanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int isbnNrDelete = Integer.parseInt(isbnEingabeDelete.getText());
            DataBaseControl.deleteBuch(isbnNrDelete);
            buchArea.setText(DataBaseControl.getBuch());
        });
        

        this.add(bottomJPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setVisible(true);
    }
    
}
