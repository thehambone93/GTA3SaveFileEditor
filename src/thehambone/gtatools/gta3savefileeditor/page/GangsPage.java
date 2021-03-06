package thehambone.gtatools.gta3savefileeditor.page;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import thehambone.gtatools.gta3savefileeditor.game.GameConstants;
import thehambone.gtatools.gta3savefileeditor.savefile.SaveFile;
import thehambone.gtatools.gta3savefileeditor.savefile.struct.Gang;
import thehambone.gtatools.gta3savefileeditor.savefile.struct.PedType;
import thehambone.gtatools.gta3savefileeditor.savefile.var.VarArray;
import thehambone.gtatools.gta3savefileeditor.savefile.var.component.VariableComboBoxItem;
import thehambone.gtatools.gta3savefileeditor.util.Logger;

/**
 * This page contains features for editing gang-related data.
 * <p>
 * Created on Mar 16, 2015.
 * 
 * @author thehambone
 */
public final class GangsPage extends Page
{
    private VarArray<Gang> aGang;
    private VarArray<PedType> aPedType;
    
    /**
     * Creates a new {@code GangsPage} object.
     */
    public GangsPage()
    {
        super("Gangs", Visibility.VISIBLE_WHEN_FILE_LOADED_ONLY);
        
        initComponents();
        
        initGangComboBox();
        initVehicleComboBox();
        initWeaponComboBoxes();
    }
    
    /**
     * Sets up the gang selection combo box.
     */
    @SuppressWarnings("unchecked")
    private void initGangComboBox()
    {
        DefaultComboBoxModel<String> gangComboBoxModel
                = new DefaultComboBoxModel<>();
        
        for (GameConstants.Gang g : GameConstants.Gang.values()) {
            if (g != GameConstants.Gang.GANG08
                    && g != GameConstants.Gang.GANG09) {
                gangComboBoxModel.addElement(g.getFriendlyName());
            }
        }
        
        gangComboBox.setModel(gangComboBoxModel);
        
        gangComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                gangComboBoxItemStateChanged();
            }
        });
    }
    
    /**
     * Sets up the vehicle selection combo box.
     */
    private void initVehicleComboBox()
    {
        List<VariableComboBoxItem> vehicles = new ArrayList<>();
        for (GameConstants.Vehicle v : GameConstants.Vehicle.values()) {
            vehicles.add(
                    new VariableComboBoxItem(v.getID(), v.getFriendlyName()));
        }
        Collections.sort(vehicles);
        
        DefaultComboBoxModel<VariableComboBoxItem> vehicleComboBoxModel
                = new DefaultComboBoxModel<>(
                        vehicles.toArray(new VariableComboBoxItem[0]));
        
        vehicleComboBox.setModel(vehicleComboBoxModel);
    }
    
    /**
     * Sets up the two weapon selection combo boxes.
     */
    private void initWeaponComboBoxes()
    {
        DefaultComboBoxModel<VariableComboBoxItem> weapon1ComboBoxModel
                = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<VariableComboBoxItem> weapon2ComboBoxModel
                = new DefaultComboBoxModel<>();
        
        for (GameConstants.Weapon w : GameConstants.Weapon.values()) {
            if (w == GameConstants.Weapon.DETONATOR) {
                continue;
            }
            weapon1ComboBoxModel.addElement(
                    new VariableComboBoxItem(w.getID(), w.getFriendlyName()));
            weapon2ComboBoxModel.addElement(
                    new VariableComboBoxItem(w.getID(), w.getFriendlyName()));
        }
        
        weapon1ComboBox.setModel(weapon1ComboBoxModel);
        weapon2ComboBox.setModel(weapon2ComboBoxModel);
    }
    
    /**
     * Defines the action to perform when a new item is selected in the gang
     * selection combo box.
     */
    private void gangComboBoxItemStateChanged()
    {
        int selectedGangIndex = gangComboBox.getSelectedIndex();
        if (selectedGangIndex == -1) {
            return;
        }
        
        Gang g = aGang.getElementAt(selectedGangIndex);
        PedType pt = aPedType.getElementAt(selectedGangIndex + 7);
        
        // Reload gang-dependent compoents
        vehicleComboBox.setVariable(g.nVehicleModelID);
        weapon1ComboBox.setVariable(g.nWeaponID1);
        weapon2ComboBox.setVariable(g.nWeaponID2);
        hostileTowardsPlayerCheckBox
                .setMask(GameConstants.PedType.PLAYER01.getPedTypeMask());
        hostileTowardsPlayerCheckBox.setVariable(pt.nThreatFlags);
    }
    
    @Override
    public void loadPage()
    {
        Logger.debug("Loading page: %s...\n", getPageTitle());
        
        aGang = SaveFile.getCurrentSaveFile().gangs.aGang;
        aPedType = SaveFile.getCurrentSaveFile().pedTypes.aPedType;
        
        // Fire gang combo box selection listener
        gangComboBox.setSelectedIndex(-1);
        gangComboBox.setSelectedIndex(0);
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
        gangLabel = new javax.swing.JLabel();
        gangComboBox = new javax.swing.JComboBox();
        vehiclePanel = new javax.swing.JPanel();
        vehicleComboBox = new thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox();
        weaponsPanel = new javax.swing.JPanel();
        weapon1Label = new javax.swing.JLabel();
        weapon1ComboBox = new thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox();
        weapon2Label = new javax.swing.JLabel();
        weapon2ComboBox = new thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox();
        hostilityPanel = new javax.swing.JPanel();
        hostileTowardsPlayerCheckBox = new thehambone.gtatools.gta3savefileeditor.savefile.var.component.BitmaskVariableCheckBox();

        gangLabel.setText("Gang:");

        gangComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<gang_name>" }));

        vehiclePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Vehicle"));

        vehicleComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<vehicle_name>" }));

        javax.swing.GroupLayout vehiclePanelLayout = new javax.swing.GroupLayout(vehiclePanel);
        vehiclePanel.setLayout(vehiclePanelLayout);
        vehiclePanelLayout.setHorizontalGroup(
            vehiclePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vehicleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vehiclePanelLayout.setVerticalGroup(
            vehiclePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vehiclePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vehicleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        weaponsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Weapons"));

        weapon1Label.setText("Weapon 1:");

        weapon1ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<weapon_name>" }));

        weapon2Label.setText("Weapon 2:");

        weapon2ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<weapon_name>" }));

        javax.swing.GroupLayout weaponsPanelLayout = new javax.swing.GroupLayout(weaponsPanel);
        weaponsPanel.setLayout(weaponsPanelLayout);
        weaponsPanelLayout.setHorizontalGroup(
            weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weaponsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(weapon1Label)
                    .addComponent(weapon2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weapon1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weapon2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        weaponsPanelLayout.setVerticalGroup(
            weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weaponsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weapon1Label)
                    .addComponent(weapon1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(weaponsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weapon2Label)
                    .addComponent(weapon2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        hostilityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Hostility"));

        hostileTowardsPlayerCheckBox.setText("Hostile towards player");

        javax.swing.GroupLayout hostilityPanelLayout = new javax.swing.GroupLayout(hostilityPanel);
        hostilityPanel.setLayout(hostilityPanelLayout);
        hostilityPanelLayout.setHorizontalGroup(
            hostilityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hostilityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hostileTowardsPlayerCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        hostilityPanelLayout.setVerticalGroup(
            hostilityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hostilityPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hostileTowardsPlayerCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(gangLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(vehiclePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weaponsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hostilityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gangLabel)
                    .addComponent(gangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hostilityPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(weaponsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vehiclePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox gangComboBox;
    private javax.swing.JLabel gangLabel;
    private thehambone.gtatools.gta3savefileeditor.savefile.var.component.BitmaskVariableCheckBox hostileTowardsPlayerCheckBox;
    private javax.swing.JPanel hostilityPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane scrollPane;
    private thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox vehicleComboBox;
    private javax.swing.JPanel vehiclePanel;
    private thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox weapon1ComboBox;
    private javax.swing.JLabel weapon1Label;
    private thehambone.gtatools.gta3savefileeditor.savefile.var.component.IntegerVariableComboBox weapon2ComboBox;
    private javax.swing.JLabel weapon2Label;
    private javax.swing.JPanel weaponsPanel;
    // End of variables declaration//GEN-END:variables
}
