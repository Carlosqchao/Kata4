package software.ulpgc.kata4.app;


import software.ulpgc.kata4.commands.ImportCommand;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ;
    public MainFrame() throws HeadlessException {
        this.setTitle("Kata 4");
        this.setSize(1024, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel());
        this.add(Box.createHorizontalStrut(this.getWidth()));
        this.add(button());
        this.add(Box.createHorizontalStrut(this.getWidth()));
    }

    private Component panel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel("Pulse para Obtener un Pokemon Aleatorio");
        label.setBounds(50, 50, 300, 30);
        panel.add(label);
        panel.setPreferredSize(new Dimension(400, 100));
        return panel;
    }


    private Component button() {
        JButton button = new JButton("Play");
        button.addActionListener(event ->{
            try{
                ImportCommand command = new ImportCommand();
                this.add(new JLabel(command.executeReading()));
                this.revalidate();
                this.repaint();
                this.add(Box.createHorizontalStrut(this.getWidth()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return button;
    }
}
