import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {
    public Demo() throws HeadlessException {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Hoi"), gbc);

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
