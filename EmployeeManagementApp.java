import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagementApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, ageField, positionField;

    public EmployeeManagementApp() {
        frame = new JFrame("Employee Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Position"}, 0);
        table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));
        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();
        positionField = new JTextField();

        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Position:"));
        inputPanel.add(positionField);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{idField.getText(), nameField.getText(), ageField.getText(), positionField.getText()});
                clearFields();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.setValueAt(idField.getText(), selectedRow, 0);
                    model.setValueAt(nameField.getText(), selectedRow, 1);
                    model.setValueAt(ageField.getText(), selectedRow, 2);
                    model.setValueAt(positionField.getText(), selectedRow, 3);
                    clearFields();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                    clearFields();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        positionField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManagementApp::new);
    }
}