import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class InvoiceGUIFrame extends JFrame {

    private JTextArea orderTextArea;
    private ArrayList<ItemEntry> itemList;

    public InvoiceGUIFrame() {
        setTitle("Invoice Generator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
        itemList = new ArrayList<>();
    }

    private void createGUI() {
        // Title
        JLabel titleLabel = new JLabel("Invoice Generator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Crust Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Input invoice information:"));
        infoPanel.setLayout(new GridLayout(4, 2));
        JLabel itemNameLabel = new JLabel("Enter Item Name-");
        JTextField itemName = new JTextField();
        JLabel itemQuanLabel = new JLabel("Enter Item Quantity-");
        JTextField itemQuan = new JTextField();
        JLabel itemPriceLabel = new JLabel("Enter Item Price-");
        JTextField itemPrice = new JTextField();
        JButton submitInfo = new JButton("Submit Info");
        JButton quit = new JButton("Quit");
        infoPanel.add(itemNameLabel);
        infoPanel.add(itemName);
        infoPanel.add(itemQuanLabel);
        infoPanel.add(itemQuan);
        infoPanel.add(itemPriceLabel);
        infoPanel.add(itemPrice);
        infoPanel.add(submitInfo);
        infoPanel.add(quit);
        add(infoPanel, BorderLayout.CENTER);


        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Invoice Output:"));
        statsPanel.setLayout(new GridLayout(1, 2));
        orderTextArea = new JTextArea(10, 25);
        orderTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        JButton submitOrder = new JButton("SubmitOrder");
        statsPanel.add(scrollPane);
        statsPanel.add(submitOrder);
        add(statsPanel, BorderLayout.SOUTH);

        // Action Listeners

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(InvoiceGUIFrame.this, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        submitInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs from JTextFields
                String itemNameText = itemName.getText();
                String itemQuanText = itemQuan.getText();
                String itemPriceText = itemPrice.getText();

                // Validate input
                if (itemNameText.isEmpty() || itemQuanText.isEmpty() || itemPriceText.isEmpty()) {
                    JOptionPane.showMessageDialog(InvoiceGUIFrame.this, "Please fill in all fields.");
                    return;
                }

                // Parse input values
                try {
                    double itemPriceValue = Double.parseDouble(itemPriceText);
                    int itemQuanValue = Integer.parseInt(itemQuanText);

                    // Create an ItemEntry and add it to the ArrayList
                    ItemEntry itemEntry = new ItemEntry(itemNameText, itemQuanValue, itemPriceValue);
                    itemList.add(itemEntry);

                    // Clear the input fields for the next entry
                    itemName.setText("");
                    itemQuan.setText("");
                    itemPrice.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InvoiceGUIFrame.this, "Please enter valid numeric values for Quantity and Price.");
                }
            }
        });

        // Action Listeners
        submitOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the total of all items
                double grandTotal = 0.0;
                //Setting up the invoice
                orderTextArea.setText("");
                orderTextArea.append("Invoice" + "\n");
                orderTextArea.append("Item                Qty    Price   Total" + "\n");
                orderTextArea.append("___________________________________" + "\n");
                //Logic for the middle
                for (ItemEntry itemEntry : itemList) {
                    String itemName = itemEntry.getName();
                    int itemQuantity = itemEntry.getQuantity();
                    double itemPrice = itemEntry.getPrice();
                    double itemTotal = itemEntry.getTotal();

                    String formattedLine = String.format("%-20s %4d %7.2f %8.2f", itemName, itemQuantity, itemPrice, itemTotal);
                    orderTextArea.append(formattedLine + "\n");

                    grandTotal += itemTotal;
                }

                orderTextArea.append("___________________________________" + "\n");
                String totalEntry = String.format("Grand Total: $%.2f", grandTotal);
                orderTextArea.append(totalEntry);
            }
        });


    }
}


