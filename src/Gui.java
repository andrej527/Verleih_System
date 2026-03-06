import javax.swing.*;
import java.awt.*;


public class Gui extends JFrame{


    public Gui() {
        super("Bibliothek Verleih");

        // alle bueacher
        JPanel centerJPanel = new JPanel();
        JTextArea buchArea = new JTextArea(20, 60);
        buchArea.setEditable(false);
        buchArea.setText(DataBaseControl.getBuch());
        centerJPanel.add(new JScrollPane(buchArea));

        // alle nutzer
        JTextArea nutzerArea = new JTextArea(20, 60);
        nutzerArea.setEditable(false);
        nutzerArea.setText(DataBaseControl.getNutzer());
        centerJPanel.add(new JScrollPane(nutzerArea));

        // verliehene bueacher
        JTextArea verleihArea = new JTextArea(20, 60);
        verleihArea.setEditable(false);
        verleihArea.setText(DataBaseControl.getVerlieheneBuch());
        centerJPanel.add(new JScrollPane(verleihArea));

        this.add(centerJPanel, BorderLayout.CENTER);

        JPanel bottomJPanel = new JPanel();
        bottomJPanel.setLayout(new BoxLayout(bottomJPanel, BoxLayout.Y_AXIS));

        //verleih edit
        

        //nutzer edit
        JPanel userEditPanel = new JPanel();
        JLabel eingabeLabeluser = new JLabel("Nutzer hinzufügen (NutzerID / Name):");
        userEditPanel.add(eingabeLabeluser);
        JTextField userIdEingabeAdd = new JTextField(10);
        userEditPanel.add(userIdEingabeAdd);
        JTextField nameEingabeAdd = new JTextField(10);
        userEditPanel.add(nameEingabeAdd);
        

        JButton addButtonUser = new JButton("Add");
        userEditPanel.add(addButtonUser);

        addButtonUser.addActionListener(e -> {
            int userId = Integer.parseInt(userIdEingabeAdd.getText());
            DataBaseControl.addNutzer(userId, nameEingabeAdd.getText());
            nutzerArea.setText(DataBaseControl.getNutzer());
        });


        JLabel loeschenUserLabel = new JLabel("Nutzer löschen (NutzerID): ");
        userEditPanel.add(loeschenUserLabel);
        JTextField userIdEingabeDelete = new JTextField(10);
        userEditPanel.add(userIdEingabeDelete);

        JButton userDeleteButton = new JButton("Delete");
        userEditPanel.add(userDeleteButton);

        
        userDeleteButton.addActionListener(e -> {
            int userId = Integer.parseInt(userIdEingabeDelete.getText());
            DataBaseControl.deleteUser(userId);
            nutzerArea.setText(DataBaseControl.getNutzer());
        });



        // buch edit
        JPanel buchEditPanel = new JPanel();
        JLabel eingabeLabel = new JLabel("Buch hinzufügen (ISBN / Titel / verliehen):");
        buchEditPanel.add(eingabeLabel);
        JTextField isbnEingabeAdd = new JTextField(10);
        buchEditPanel.add(isbnEingabeAdd);
        JTextField titelEingabeAdd = new JTextField(10);
        buchEditPanel.add(titelEingabeAdd);
        JTextField verliehenEingabeAdd = new JTextField(1);
        buchEditPanel.add(verliehenEingabeAdd);

        JButton addButton = new JButton("Add");
        buchEditPanel.add(addButton);

        addButton.addActionListener(e -> {
            int isbnNr = Integer.parseInt(isbnEingabeAdd.getText());
            int verliehen = Integer.parseInt(verliehenEingabeAdd.getText());
            DataBaseControl.addBuch(isbnNr, titelEingabeAdd.getText(), verliehen);
            buchArea.setText(DataBaseControl.getBuch());
        });


        JLabel loeschenLabel = new JLabel("Buch löschen (ISBN): ");
        buchEditPanel.add(loeschenLabel);
        JTextField isbnEingabeDelete = new JTextField(10);
        buchEditPanel.add(isbnEingabeDelete);

        JButton deleteButton = new JButton("Delete");
        buchEditPanel.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int isbnNrDelete = Integer.parseInt(isbnEingabeDelete.getText());
            DataBaseControl.deleteBuch(isbnNrDelete);
            buchArea.setText(DataBaseControl.getBuch());
        });
        
        bottomJPanel.add(userEditPanel);
        bottomJPanel.add(buchEditPanel);
        this.add(bottomJPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,1200);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        setVisible(true);
    }
    
}
