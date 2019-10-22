package platformio.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Main extends JFrame {
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new FlowLayout());

        Vector v = new Vector();
        v.add("Europe");
        v.add(new JCheckBox("Brussels", false));
        v.add(new JCheckBox("Paris", false));
        v.add(new JCheckBox("London", false));
        v.add("United States");
        v.add(new JCheckBox("New York", false));
        v.add(new JCheckBox("Detroit", false));
        v.add(new JCheckBox("San Francisco", false));

        getContentPane().add(new JComboCheckBox(v));
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(300, 300);
        main.setVisible(true);
    }
}


class JComboCheckBox extends JComboBox {
    public JComboCheckBox() {
        init();
    }

    public JComboCheckBox(JCheckBox[] items) {
        super(items);
        init();
    }

    public JComboCheckBox(Vector items) {
        super(items);
        init();
    }

    public JComboCheckBox(ComboBoxModel aModel) {
        super(aModel);
        init();
    }

    private void init() {
        setRenderer(new ComboBoxRenderer());
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                itemSelected();
            }
        });
    }

    private void itemSelected() {
        if (getSelectedItem() instanceof JCheckBox) {
            JCheckBox jcb = (JCheckBox) getSelectedItem();
            jcb.setSelected(!jcb.isSelected());
        }
    }

    class ComboBoxRenderer implements ListCellRenderer {
        private JLabel label;

        public ComboBoxRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Component) {
                Component c = (Component) value;
                if (isSelected) {
                    c.setBackground(list.getSelectionBackground());
                    c.setForeground(list.getSelectionForeground());
                } else {
                    c.setBackground(list.getBackground());
                    c.setForeground(list.getForeground());
                }

                return c;
            } else {
                if (label == null) {
                    label = new JLabel(value.toString());
                } else {
                    label.setText(value.toString());
                }

                return label;
            }
        }
    }
}