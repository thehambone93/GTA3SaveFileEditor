package thehambone.gtatools.gta3savefileeditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import thehambone.gtatools.gta3savefileeditor.util.GUIUtilities;
import thehambone.gtatools.gta3savefileeditor.util.Logger;
import thehambone.gtatools.gta3savefileeditor.util.ResourceLoader;

/**
 * The {@code AbourDialog} class is an extension of the {@code JDialog} class
 * that displays information about the program.
 * <p>
 * Created on Apr 8, 2015.
 * 
 * @author thehambone
 */
public final class AboutDialog extends JDialog
{
    private static final String ICON_PATH = "META-INF/res/logo1.png";
    private static final String ABOUT_TEXT = "This tool is a work-in-progress "
            + "save editor for Grand Theft Auto III save  files. It currently "
            + "supports Windows, Mac OS X, iOS, and Android saves. "
            + "Support for Xbox and PS2 saves coming soon, along with many "
            + "more features!\n\n"
            + "Thanks to <b>OrionSR</b>, <b>Seemann</b>, <b>Silent</b>, and "
            + "<b>spaceeinstein</b> for helping with research and "
            + "documentation.";
    
    /**
     * Creates a new {@code AboutDialog} instance.
     * 
     * @param parent the dialog parent frame
     */
    public AboutDialog(Frame parent)
    {
        super(parent, "About " + Main.PROGRAM_TITLE, true);
        initComponents();
    }
    
    /**
     * Sets up the components on the dialog.
     */
    private void initComponents()
    {
        GridBagConstraints con = new GridBagConstraints();
        con.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        JLabel iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        try {
            iconLabel.setIcon(new ImageIcon(
                    ResourceLoader.loadImageResource(ICON_PATH)));
        } catch (IOException ex) {
            Logger.error("Failed to load image resource.");
            Logger.stackTrace(ex);
        }
        topPanel.add(iconLabel, BorderLayout.CENTER);

        // Center
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel centerPanelTop = new JPanel(new GridBagLayout());
        JPanel centerPanelMid = new JPanel(new BorderLayout());
        JPanel centerPanelBottom = new JPanel(new BorderLayout());
        centerPanelMid.setBorder(new EmptyBorder(15, 10, 5, 10));
        centerPanelBottom.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel authorLabel1 = new JLabel("Author:");
        authorLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        authorLabel1.setBorder(new EmptyBorder(1, 0, 1, 2));
        
        JLabel authorLabel2 = new JLabel(String.format(
                "<html><a href='%s'>%s</a></html>",
                Main.PROGRAM_AUTHOR_URL, Main.PROGRAM_AUTHOR));
        authorLabel2.setBorder(new EmptyBorder(1, 2, 1, 0));
        authorLabel2.setToolTipText(Main.PROGRAM_AUTHOR_URL);
        authorLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        authorLabel2.addMouseListener(
                getOpenURLMouseAdapter(Main.PROGRAM_AUTHOR_URL));
        
        JLabel versionLabel1 = new JLabel("Version:");
        versionLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        versionLabel1.setBorder(new EmptyBorder(1, 0, 1, 2));
        
        JLabel versionLabel2 = new JLabel(
                Main.PROGRAM_VERSION + " build " + Main.getBuildNumber());
        versionLabel2.setBorder(new EmptyBorder(1, 2, 1, 0));
        
        JLabel buildDate1 = new JLabel("Build date:");
        buildDate1.setHorizontalAlignment(SwingConstants.RIGHT);
        buildDate1.setBorder(new EmptyBorder(1, 0, 1, 2));
        
        JLabel buildDate2 = new JLabel(new SimpleDateFormat("MMMM dd, yyyy")
                .format(Main.getBuildDate()));
        buildDate2.setBorder(new EmptyBorder(1, 2, 1, 1));
        
        JLabel aboutTextLabel = new JLabel(
                GUIUtilities.formatHTMLString(ABOUT_TEXT, 220, false, null));
        aboutTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        aboutTextLabel.setBorder(new EmptyBorder(1, 0, 1, 0));
        
        JLabel bugsLabel1 = new JLabel("Bug reports go to:");
        bugsLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        bugsLabel1.setBorder(new EmptyBorder(1, 0, 1, 2));
        
        JLabel bugsLabel2 = new JLabel(
                String.format("<html><a href='mailto:%s'>%s</a></html>",
                        Main.PROGRAM_AUTHOR_EMAIL, Main.PROGRAM_AUTHOR_EMAIL));
        bugsLabel2.setBorder(new EmptyBorder(1, 2, 1, 0));
        bugsLabel2.setToolTipText(
                String.format("mailto:%s", Main.PROGRAM_AUTHOR_EMAIL));
        bugsLabel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bugsLabel2.addMouseListener(
                getOpenURLMouseAdapter(String.format(
                        "mailto:%s", Main.PROGRAM_AUTHOR_EMAIL)));
        
        con.gridx = 0;
        con.gridy = 0;
        centerPanelTop.add(authorLabel1, con);
        
        con.gridx = 1;
        con.gridy = 0;
        centerPanelTop.add(authorLabel2, con);
        
        con.gridx = 0;
        con.gridy = 1;
        centerPanelTop.add(versionLabel1, con);
        
        con.gridx = 1;
        con.gridy = 1;
        centerPanelTop.add(versionLabel2, con);
        
        con.gridx = 0;
        con.gridy = 2;
        centerPanelTop.add(buildDate1, con);
        
        con.gridx = 1;
        con.gridy = 2;
        centerPanelTop.add(buildDate2, con);
        
        centerPanelMid.add(aboutTextLabel, BorderLayout.CENTER);
        centerPanelBottom.add(bugsLabel1, BorderLayout.WEST);
        centerPanelBottom.add(bugsLabel2, BorderLayout.CENTER);
        
        centerPanel.add(centerPanelTop, BorderLayout.NORTH);
        centerPanel.add(centerPanelMid, BorderLayout.CENTER);
        centerPanel.add(centerPanelBottom, BorderLayout.SOUTH);
        
        //Bottom
        JPanel bottomLeftPanel = new JPanel(new BorderLayout());
        bottomLeftPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        JLabel copyrightLabel = new JLabel(Main.PROGRAM_COPYRIGHT + '.');
        copyrightLabel.setBorder(new EmptyBorder(0, 0, 0, 20));
        bottomLeftPanel.add(copyrightLabel, BorderLayout.WEST);
        
        JButton closeButton = new JButton("Close");
        bottomLeftPanel.add(closeButton, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

        add(mainPanel);

        closeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        
        AbstractAction closeAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        };
        getRootPane().registerKeyboardAction(closeAction,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false); 
    }
    
    private MouseAdapter getOpenURLMouseAdapter(final String url)
    {
        final Component parent = this;
        
        return new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (IOException | URISyntaxException ex) {
                    Logger.warn("Unable to open Internet browser!", ex);
                    Logger.stackTrace(ex);
                    GUIUtilities.showErrorMessageBox(parent,
                            "Unable to open Internet browser!",
                            "Failed to Open Browser");
                }
            }
        };
    }
}
