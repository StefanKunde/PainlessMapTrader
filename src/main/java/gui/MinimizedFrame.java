package gui;

import com.stefank.Main;
import listener.MaximizeButtonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MinimizedFrame extends JFrame implements IHideable {

    private static final Logger LOG = LoggerFactory.getLogger(MinimizedFrame.class);

    private static final long serialVersionUID = 1L;
    private JTextField txtTest;
    private boolean userWantsMinimize;
    private boolean isVisible;
    private MainFrame mainFrame;


    public MinimizedFrame(MainFrame mainFrame) {
        this.setTitle("MapTrado Mini");
        getContentPane().setForeground(Color.GRAY);
        getContentPane().setBackground(Color.DARK_GRAY);
        setResizable(false);
        setAutoRequestFocus(false);
        setForeground(Color.GRAY);
        setFont(new Font("Calibri", Font.PLAIN, 12));
        setBackground(Color.GRAY);

        this.mainFrame = mainFrame;

        JFrame.setDefaultLookAndFeelDecorated(true);

        JButton btnMaximize = new JButton("");
        btnMaximize.setLocation(14, 14);
        btnMaximize.setOpaque(false);
        btnMaximize.setForeground(Color.BLACK);
        btnMaximize.setContentAreaFilled(false);
        btnMaximize.setBorderPainted(false);
        Image maximizeIcon = null;
        try {
            maximizeIcon = ImageIO.read(Main.class.getResourceAsStream("maximize.png"));
        } catch (IOException e) {
            LOG.error("MinimizedFrame:(loading maximizeIcon)", e);
        }
        btnMaximize.setIcon(new ImageIcon(maximizeIcon));
        btnMaximize.setFocusPainted(false);
        btnMaximize.setSize(32, 32);

        MaximizeButtonListener maxBtnListener = new MaximizeButtonListener(this, mainFrame);
        btnMaximize.addActionListener(maxBtnListener);
        getContentPane().setLayout(null);
        getContentPane().add(btnMaximize);


        this.setSize(60, 60);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.getContentPane().requestFocusInWindow();
        FrameDragListener frameDragListener = new FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);
        this.setAlwaysOnTop(true);
        //frame.setMinimumSize(new Dimension(300, 300));
        isVisible = false;
        this.setVisible(false);

    }

    @Override
    public void setFrameVisible() {
        this.setVisible(true);
        isVisible = true;
    }

    @Override
    public boolean isUserWantsMinimize() {
        return userWantsMinimize;
    }

    public void setUserWantsMinimize(boolean userWantsMinimize) {
        this.userWantsMinimize = userWantsMinimize;
    }

    @Override
    public void setFrameInvisible() {
        this.setVisible(false);
        isVisible = false;
    }

    @Override
    public boolean isFrameVisible() {
        return isVisible;
    }
}
