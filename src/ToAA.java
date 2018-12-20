import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.File;

/**
 * Created by yuh105 on 2016/10/10.
 */

public class ToAA extends JFrame implements Runnable {

    private ImageIcon img = new ImageIcon("./hello.gif");
    private JTextArea jta = new JTextArea();
    private JTextField textField;
    private JComboBox<Integer> comboBox;
    private JFileChooser fileChooser;

    private int fontSize = 6;
    private Integer[] fontSizeArray = {2,4,6,8,12,16,32};;
    private String _str = "もじれつ";

    public static void main(String[] args) {
        new ToAA();
    }

    public ToAA() {
        this.setLocation(200, 200);
        this.setSize(1200, 900);
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1000, 40));
        this.add("North", topPanel);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("jpg,png,gif", "png", "jpg", "gif"));

        JButton fileButton = new JButton("画像ファイル選択");
        fileButton.addActionListener(e -> fileChooser.showOpenDialog(this));
        topPanel.add(fileButton);

        topPanel.add(new JLabel("フォントサイズ"));
        comboBox = new JComboBox(fontSizeArray);
        comboBox.setSelectedIndex(3);
        topPanel.add(comboBox);

        topPanel.add(new JLabel("     生成する文字列: "));
        textField = new JTextField(12);
        topPanel.add(textField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            createAA();
        });
        topPanel.add(createButton);

        jta.setFont(new Font("MSゴシック", 0, fontSize));
        this.add("Center", new JScrollPane(jta));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        new Thread(this).start();
    }

    public void run() {
        int w = img.getIconWidth();
        int h = img.getIconHeight();
        int[] data = new int[w * h];
        jta.setText("");

        PixelGrabber pg = new PixelGrabber(img.getImage(), 0, 0, w, h, true);

        try {
            pg.grabPixels();
            data = (int[]) pg.getPixels();
        } catch (Exception e) {
        }

        for (int i = 0; i < h; i += 3) {
            for (int j = 0; j < w; j += 2) {
                Color col = new Color(data[i * w + j]);
                int gray = (col.getRed() + col.getGreen() + col.getBlue()) / 3;
                String str = _str + "・・・　　　";
                jta.append("" + str.charAt(gray * str.length() / 256));
            }
            jta.append("\n");
        }
    }

    public void createAA() {
        _str = textField.getText();
        if (_str.length() == 0)
            _str = "もじれつ";

        fontSize = Integer.parseInt(comboBox.getSelectedItem().toString());
        jta.setFont(new Font("MSゴシック", 0, fontSize));
        textField.setText("");
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            img = new ImageIcon(file.getAbsolutePath());
        }
        run();
    }
}
