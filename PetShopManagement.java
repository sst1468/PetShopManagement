import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;


class Purchase {
    private String customerName;
    private String paymentMode;
    private String itemName;
    private String username; // added to store the registered user's username

    public Purchase(String customerName, String paymentMode, String itemName, String username) {
        this.customerName = customerName;
        this.paymentMode = paymentMode;
        this.itemName = itemName;
        this.username = username;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUsername() {
        return username;
    }
}

public class PetShopManagement extends JFrame implements ActionListener {
    private ArrayList<Purchase> petPurchases;
    private ArrayList<Purchase> foodPurchases;
    private ArrayList<Purchase> accessoryPurchases;
    private HashMap<String, String> registeredUsers;
    private String loggedInUser;
    private JPanel currentPanel;
    private CardLayout cardLayout;
    private JLabel errorLabel;

    private static final String ADMIN_USERNAME = "team";
    private static final String ADMIN_PASSWORD = "sowmya@123";

    public PetShopManagement() {
        petPurchases = new ArrayList<>();
        foodPurchases = new ArrayList<>();
        accessoryPurchases = new ArrayList<>();
        registeredUsers = new HashMap<>();
        cardLayout = new CardLayout();
        currentPanel = new JPanel(cardLayout);

        // Add main panel
        JPanel mainPanel = createMainPanel();
        mainPanel.setOpaque(false);
        currentPanel.add(mainPanel, "Main");

        add(currentPanel);
        setTitle("Pet Shop Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createMainPanel() {
          JPanel mainPanel = new JPanel(new GridBagLayout());
          mainPanel.setBackground(Color.CYAN);
            

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some vertical spacing between the components

        JButton adminButton = new JButton("Admin");
        adminButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        adminButton.addActionListener(this);
        mainPanel.add(adminButton, gbc);

        gbc.gridy++;
        JButton customerButton = new JButton("Customer");
        customerButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        customerButton.addActionListener(this);
        mainPanel.add(customerButton, gbc);

        gbc.gridy++;
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(120, 30)); // Set button size
        registerButton.addActionListener(this);
        mainPanel.add(registerButton, gbc);

        return mainPanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
          loginPanel.setBackground(Color.BLUE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some vertical spacing between the components

        JLabel usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20)); // Set text field size
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20)); // Set text field size
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    // Successful login, show admin panel
                    showAdminPanel();
                } else if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
                    // Successful login, show customer panel
                    loggedInUser = username;
                    showCustomerPanel();
                } else {
                    // Incorrect credentials, show error message
                    showError("Wrong username or password");
                }
            }
        });
        loginPanel.add(loginButton, gbc);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        gbc.gridy++;
        loginPanel.add(errorLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(currentPanel, "Main");
            }
        });
        gbc.gridy++;
        loginPanel.add(backButton, gbc);

        return loginPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Color.BLUE);
            
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some vertical spacing between the components

        JLabel usernameLabel = new JLabel("Username:");
        registerPanel.add(usernameLabel, gbc);

        gbc.gridx++;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20)); // Set text field size
        registerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        registerPanel.add(passwordLabel, gbc);

        gbc.gridx++;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20)); // Set text field size
        registerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (!registeredUsers.containsKey(username)) {
                    registeredUsers.put(username, password);
                    showError("Registration successful!");
                } else {
                    showError("Username already exists!");
                }
            }
        });
        registerPanel.add(registerButton, gbc);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        gbc.gridy++;
        registerPanel.add(errorLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(currentPanel, "Main");
            }
        });
        gbc.gridy++;
        registerPanel.add(backButton, gbc);

        return registerPanel;
    }

    private void showAdminPanel() {
        // Show admin panel
        JPanel adminPanel = createAdminPanel();
        currentPanel.add(adminPanel, "Admin");
        cardLayout.show(currentPanel, "Admin");
    }

    private void showCustomerPanel() {
        // Show customer panel
        JPanel customerPanel = createCustomerPanel();
        currentPanel.add(customerPanel, "Customer");
        cardLayout.show(currentPanel, "Customer");
    }

    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    private JPanel createAdminPanel() {
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setBackground(Color.GREEN); // Set background color to green

        JTextArea purchaseDetailsArea = new JTextArea();
        purchaseDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(purchaseDetailsArea);
        adminPanel.add(scrollPane, BorderLayout.CENTER);

        // Display pet purchases
        StringBuilder petPurchaseDetails = new StringBuilder("Pet Purchases:\n");
        for (Purchase purchase : petPurchases) {
            petPurchaseDetails.append("Customer: ").append(purchase.getCustomerName())
                    .append(", Payment Mode: ").append(purchase.getPaymentMode())
                    .append(", Animal: ").append(purchase.getItemName())
                    .append(", Username: ").append(purchase.getUsername()).append("\n");
        }

        // Display food purchases
        StringBuilder foodPurchaseDetails = new StringBuilder("Pet Food Purchases:\n");
        for (Purchase purchase : foodPurchases) {
            foodPurchaseDetails.append("Customer: ").append(purchase.getCustomerName())
                    .append(", Payment Mode: ").append(purchase.getPaymentMode())
                    .append(", Food: ").append(purchase.getItemName())
                    .append(", Username: ").append(purchase.getUsername()).append("\n");
        }

        // Display accessory purchases
        StringBuilder accessoryPurchaseDetails = new StringBuilder("Pet Accessory Purchases:\n");
        for (Purchase purchase : accessoryPurchases) {
            accessoryPurchaseDetails.append("Customer: ").append(purchase.getCustomerName())
                    .append(", Payment Mode: ").append(purchase.getPaymentMode())
                    .append(", Accessory: ").append(purchase.getItemName())
                    .append(", Username: ").append(purchase.getUsername()).append("\n");
        }

        // Display registered users
        StringBuilder registeredUsersDetails = new StringBuilder("Registered Users:\n");
        for (String username : registeredUsers.keySet()) {
            registeredUsersDetails.append("Username: ").append(username).append("\n");
        }

        // Append all details to the text area
        purchaseDetailsArea.setText(petPurchaseDetails.toString() + "\n" + foodPurchaseDetails.toString() + "\n" + accessoryPurchaseDetails.toString() + "\n" + registeredUsersDetails.toString());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(currentPanel, "Main");
            }
        });
        adminPanel.add(backButton, BorderLayout.SOUTH);

        return adminPanel;
    }

    private JPanel createCustomerPanel() {
        JPanel customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBackground(Color.BLUE); // Set background color to blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some vertical spacing between the components

        JButton buyPetButton = new JButton("Buy Pet");
        buyPetButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        buyPetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel buyPetPanel = createBuyPanel("Pet");
                currentPanel.add(buyPetPanel, "BuyPet");
                cardLayout.show(currentPanel, "BuyPet");
            }
        });
        customerPanel.add(buyPetButton, gbc);

        gbc.gridy++;
        JButton buyFoodButton = new JButton("Buy Pet Food");
        buyFoodButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        buyFoodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel buyFoodPanel = createBuyPanel("Pet Food");
                currentPanel.add(buyFoodPanel, "BuyFood");
                cardLayout.show(currentPanel, "BuyFood");
            }
        });
        customerPanel.add(buyFoodButton, gbc);

        gbc.gridy++;
        JButton buyAccessoryButton = new JButton("Buy Pet Accessory");
        buyAccessoryButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        buyAccessoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel buyAccessoryPanel = createBuyPanel("Pet Accessory");
                currentPanel.add(buyAccessoryPanel, "BuyAccessory");
                cardLayout.show(currentPanel, "BuyAccessory");
            }
        });
        customerPanel.add(buyAccessoryButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(currentPanel, "Main");
            }
        });
        gbc.gridy++;
        customerPanel.add(backButton, gbc);

        return customerPanel;
    }

    private JPanel createBuyPanel(String itemType) {
        JPanel buyPanel = new JPanel(new GridBagLayout());
        buyPanel.setBackground(Color.RED); // Set background color to red

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add some spacing between the components

        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setPreferredSize(new Dimension(80, 20)); // Set label size
        buyPanel.add(nameLabel, gbc);

        gbc.gridx++;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(120, 20)); // Set text field size
        buyPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel paymentLabel = new JLabel("Payment Mode:");
        paymentLabel.setPreferredSize(new Dimension(80, 20)); // Set label size
        buyPanel.add(paymentLabel, gbc);

        gbc.gridx++;
        JComboBox<String> paymentOptions = new JComboBox<>(new String[]{"Cash", "Credit Card", "PayPal"});
        paymentOptions.setPreferredSize(new Dimension(120, 20)); // Set combo box size
        buyPanel.add(paymentOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel itemLabel = new JLabel(itemType + " Name:");
        itemLabel.setPreferredSize(new Dimension(80, 20)); // Set label size
        buyPanel.add(itemLabel, gbc);

        gbc.gridx++;
        JComboBox<String> itemOptions;
        if (itemType.equals("Pet")) {
            itemOptions = new JComboBox<>(new String[]{"Dog", "Cat", "Bird"});
        } else if (itemType.equals("Pet Food")) {
            itemOptions = new JComboBox<>(new String[]{"Dog Food", "Cat Food", "Bird Seed"});
        } else {
            itemOptions = new JComboBox<>(new String[]{"Leash", "Collar", "Toy"});
        }
        itemOptions.setPreferredSize(new Dimension(120, 20)); // Set combo box size
        buyPanel.add(itemOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton displayButton = new JButton("Display");
        displayButton.setPreferredSize(new Dimension(200, 30)); // Set button size
        buyPanel.add(displayButton, gbc);

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String payment = (String) paymentOptions.getSelectedItem();
                String item = (String) itemOptions.getSelectedItem();
                String message = "Purchase Details:\n" +
                        "Name: " + name + "\n" +
                        "Payment Mode: " + payment + "\n" +
                        itemType + ": " + item;
                JOptionPane.showMessageDialog(buyPanel, message);

                // Store the purchase details
                Purchase purchase = new Purchase(name, payment, item, loggedInUser);
                if (itemType.equals("Pet")) {
                    petPurchases.add(purchase);
                } else if (itemType.equals("Pet Food")) {
                    foodPurchases.add(purchase);
                } else if (itemType.equals("Pet Accessory")) {
                    accessoryPurchases.add(purchase);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(currentPanel, "Customer");
            }
        });
        gbc.gridy++;
        buyPanel.add(backButton, gbc);

        return buyPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Admin")) {
            // Show login panel for admin
            JPanel loginPanel = createLoginPanel();
            currentPanel.add(loginPanel, "Login");
            cardLayout.show(currentPanel, "Login");
        } else if (command.equals("Customer")) {
            // Show login panel for customer
            JPanel loginPanel = createLoginPanel();
            currentPanel.add(loginPanel, "Login");
            cardLayout.show(currentPanel, "Login");
        } else if (command.equals("Register")) {
            // Show registration panel
            JPanel registerPanel = createRegisterPanel();
            currentPanel.add(registerPanel, "Register");
            cardLayout.show(currentPanel, "Register");
        }
    }

    public static void main(String[] args) {
        new PetShopManagement();
    }
}