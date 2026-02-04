import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Shared data and helper methods for all ATM screens.
 */
public class ATMBase {
    // Constants
    public static final String FONT_NAME = "Arial";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_HEIGHT_SMALL = 500;
    public static final Color TEXT_COLOR = Color.WHITE;
    private static final int ACCOUNT_BASE = 100000;

    // Data storage
    public static ArrayList<Account> accounts = new ArrayList<>();

    public static String generateAccountNumber() {
        return String.valueOf(ACCOUNT_BASE + accounts.size() + 1);
    }

    public static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void switchFrame(JFrame current, JFrame next) {
        next.setVisible(true);
        if (current != null) current.dispose();
    }

    // UI Helper Methods
    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public static JLabel createLabel(String text, int x, int y, int width, int height, int fontSize, boolean bold) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font(FONT_NAME, bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        return label;
    }

    public static JButton createButton(String text, int x, int y, int width, int height, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font(FONT_NAME, Font.BOLD, fontSize));
        button.setBounds(x, y, width, height);
        return button;
    }

    /**
     * Background panel that paints an image from local file.
     * If image is missing, uses gradient background.
     */
    public static class ImagePanel extends JPanel {
        private Image image;
        private boolean hasImage;

        public ImagePanel(String imagePath) {
            setLayout(null); // allow absolute positioning for "buttons over background"
            loadImage(imagePath);
        }

        /**
         * Load image from local file.
         * If file doesn't exist, will use gradient fallback.
         */
        private void loadImage(String imagePath) {
            try {
                File file = new File(imagePath);
                if (file.exists()) {
                    image = new ImageIcon(imagePath).getImage();
                    hasImage = true;
                }
            } catch (Exception e) {
                hasImage = false;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            if (hasImage && image != null) {
                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = image.getWidth(this);
                int imageHeight = image.getHeight(this);
                
                double scale = Math.max((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);
                int scaledWidth = (int) (imageWidth * scale);
                int scaledHeight = (int) (imageHeight * scale);
                int x = (panelWidth - scaledWidth) / 2;
                int y = (panelHeight - scaledHeight) / 2;
                
                g2.drawImage(image, x, y, scaledWidth, scaledHeight, this);
            } else {
                GradientPaint gradient = new GradientPaint(0, 0, new Color(15, 25, 45),
                    0, getHeight(), new Color(30, 60, 100));
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}

