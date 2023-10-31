import javax.swing.*;

public class InvoiceGUIRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InvoiceGUIFrame();
        });
    }
}