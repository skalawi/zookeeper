package testapp;
public class ChildrenGUI extends javax.swing.JFrame {

    public ChildrenGUI() {
        initComponents();
    }

    private void initComponents() {
        fahrenheitLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Celsius Converter");

        fahrenheitLabel.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(fahrenheitLabel)))
                                .addContainerGap(27, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fahrenheitLabel))
                                .addContainerGap(21, Short.MAX_VALUE))
        );
        pack();
    }
    public void changeChildrenCount(int count){
        fahrenheitLabel.setText(Integer.toString(count));
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChildrenGUI().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel fahrenheitLabel;


}