package thehambone.gtatools.gta3savefileeditor.page;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import thehambone.gtatools.gta3savefileeditor.Main;
import thehambone.gtatools.gta3savefileeditor.Settings;
import thehambone.gtatools.gta3savefileeditor.util.GUIUtilities;
import thehambone.gtatools.gta3savefileeditor.util.Logger;

/**
 * 
 * @author thehambone
 * @version 0.1
 * @since 0.1, March 31, 2015
 */
public class OptionsPage extends Page
{
    private static final String WIN32_FILE_PATH_REGEX = "^([A-Z]|[a-z]):((\\\\|/).*)$";
    
    public OptionsPage()
    {
        super("Options", Visibility.ALWAYS_VISIBLE);
        initComponents();
        initFocusListeners();
    }
    
    private void initFocusListeners()
    {
        setTextAreaFocusListener(Settings.Key.GTA3_USER_DIR, saveFileFolderTextField);
    }
    
    private void setTextAreaFocusListener(final Settings.Key propertyKey,
            final JTextField textField)
    {
        final JPanel parent = this;
        FocusListener listener = new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                // Do nothing
            }
            
            @Override
            public void focusLost(FocusEvent e)
            {
                if (textField.getText().isEmpty()) {
                    textField.setText(Settings.getDefault(propertyKey));
                }
                if (Main.getOperatingSystem() == Main.OperatingSystem.WINDOWS) {
                    if (textField.getText().matches(WIN32_FILE_PATH_REGEX)) {
                        Settings.set(propertyKey, textField.getText());
                    } else {
                        GUIUtilities.showInformationMessageBox(
                                parent, "Invalid path!", "Invalid Path");
                        textField.selectAll();
                        textField.requestFocus();
                    }
                }
            }
        };
        textField.addFocusListener(listener);
    }
    
    @Override
    public void loadPage()
    {
        Logger.debug("Loading page: %s...\n", getPageTitle());
        
        saveFileFolderTextField.setText(Settings.get(Settings.Key.GTA3_USER_DIR));
        backupCheckBox.setSelected(Boolean.parseBoolean(Settings.get(Settings.Key.MAKE_BACKUPS)));
        updateTimestampCheckBox.setSelected(Boolean.parseBoolean(Settings.get(Settings.Key.TIMESTAMP_FILES)));
    }
    
    private File chooseDir()
    {
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = jfc.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return jfc.getSelectedFile();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        scrollPane = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        saveFileFolderPanel = new javax.swing.JPanel();
        saveFileFolderTextField = new javax.swing.JTextField();
        saveFileFolderBrowseButton = new javax.swing.JButton();
        backupPanel = new javax.swing.JPanel();
        backupCheckBox = new javax.swing.JCheckBox();
        timestampPanel = new javax.swing.JPanel();
        updateTimestampCheckBox = new javax.swing.JCheckBox();

        saveFileFolderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("GTA III Save File Folder"));

        saveFileFolderBrowseButton.setText("Browse...");
        saveFileFolderBrowseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveFileFolderBrowseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout saveFileFolderPanelLayout = new javax.swing.GroupLayout(saveFileFolderPanel);
        saveFileFolderPanel.setLayout(saveFileFolderPanelLayout);
        saveFileFolderPanelLayout.setHorizontalGroup(
            saveFileFolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveFileFolderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveFileFolderTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveFileFolderBrowseButton)
                .addContainerGap())
        );
        saveFileFolderPanelLayout.setVerticalGroup(
            saveFileFolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveFileFolderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(saveFileFolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveFileFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveFileFolderBrowseButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        backupPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Save File Backup"));

        backupCheckBox.setText("Make backups when file is loaded");
        backupCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                backupCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backupPanelLayout = new javax.swing.GroupLayout(backupPanel);
        backupPanel.setLayout(backupPanelLayout);
        backupPanelLayout.setHorizontalGroup(
            backupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backupCheckBox)
                .addContainerGap(311, Short.MAX_VALUE))
        );
        backupPanelLayout.setVerticalGroup(
            backupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backupCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        timestampPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Timestamp"));

        updateTimestampCheckBox.setText("Update timestamp on save (shows up in in-game load menu)");
        updateTimestampCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                updateTimestampCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timestampPanelLayout = new javax.swing.GroupLayout(timestampPanel);
        timestampPanel.setLayout(timestampPanelLayout);
        timestampPanelLayout.setHorizontalGroup(
            timestampPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timestampPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateTimestampCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        timestampPanelLayout.setVerticalGroup(
            timestampPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timestampPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateTimestampCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(saveFileFolderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timestampPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveFileFolderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timestampPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveFileFolderBrowseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveFileFolderBrowseButtonActionPerformed
    {//GEN-HEADEREND:event_saveFileFolderBrowseButtonActionPerformed
        File dir = chooseDir();
        if (dir != null) {
            saveFileFolderTextField.setText(dir.getAbsolutePath());
            Settings.set(Settings.Key.GTA3_USER_DIR, dir.getAbsolutePath());
        }
    }//GEN-LAST:event_saveFileFolderBrowseButtonActionPerformed

    private void backupCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_backupCheckBoxActionPerformed
    {//GEN-HEADEREND:event_backupCheckBoxActionPerformed
        Settings.set(Settings.Key.MAKE_BACKUPS, Boolean.toString(backupCheckBox.isSelected()));
    }//GEN-LAST:event_backupCheckBoxActionPerformed

    private void updateTimestampCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_updateTimestampCheckBoxActionPerformed
    {//GEN-HEADEREND:event_updateTimestampCheckBoxActionPerformed
        Settings.set(Settings.Key.TIMESTAMP_FILES, Boolean.toString(updateTimestampCheckBox.isSelected()));
    }//GEN-LAST:event_updateTimestampCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox backupCheckBox;
    private javax.swing.JPanel backupPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton saveFileFolderBrowseButton;
    private javax.swing.JPanel saveFileFolderPanel;
    private javax.swing.JTextField saveFileFolderTextField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel timestampPanel;
    private javax.swing.JCheckBox updateTimestampCheckBox;
    // End of variables declaration//GEN-END:variables
}