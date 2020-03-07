/**
 * This file is part of VisiCut.
 * Copyright (C) 2011 - 2013 Thomas Oster <thomas.oster@rwth-aachen.de>
 * RWTH Aachen University - 52062 Aachen, Germany
 *
 *     VisiCut is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     VisiCut is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with VisiCut.  If not, see <http://www.gnu.org/licenses/>.
 **/
package de.thomas_oster.visicut.gui.propertypanel;

import de.thomas_oster.liblasercut.LaserProperty;
import de.thomas_oster.uicomponents.EditableTableProvider;
import de.thomas_oster.visicut.VisicutModel;
import de.thomas_oster.visicut.gui.MainView;
import de.thomas_oster.visicut.model.LaserDevice;
import de.thomas_oster.visicut.model.LaserProfile;
import de.thomas_oster.visicut.model.Raster3dProfile;
import de.thomas_oster.visicut.model.RasterProfile;
import de.thomas_oster.visicut.model.VectorProfile;
import de.thomas_oster.visicut.model.mapping.Mapping;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Thomas Oster <thomas.oster@rwth-aachen.de>
 */
public class PropertyPanel extends javax.swing.JPanel implements EditableTableProvider
{

  private LaserPropertiesTableModel model;
  private List<LaserProperty> laserproperties;
  private LaserProfile lp;
  
  /**
   * Creates new form PropertyPanel
   */
  public PropertyPanel()
  {
    initComponents();
    model = new LaserPropertiesTableModel();
    this.editableTablePanel1.setTableModel(model);
    this.editableTablePanel1.setMoveButtonsVisible(true);
    this.editableTablePanel1.setProvider(this);
    this.model.addTableModelListener(new TableModelListener(){
      public void tableChanged(TableModelEvent tme)
      {
        PropertyPanel.this.setModified(true);
      }
    });
  }
  
  private String allfilters = "";
  private String text = "title";
  private String title = java.util.ResourceBundle.getBundle("de.thomas_oster/visicut/gui/resources/PropertyPanel").getString("PROPERTY_TITLE");
  private String unused = java.util.ResourceBundle.getBundle("de.thomas_oster/visicut/gui/resources/PropertyPanel").getString("UNUSED");

  public void resetPanel()
  {
    // Panels have state and get re-used by updatePanels(). reloadPanels() avoids re-using.
    allfilters = "";
  }

  public void setMapping(Mapping m, boolean isUnused)
  {
    
    String profile = m.getProfile() != null ? m.getProfile().getName() : java.util.ResourceBundle.getBundle("de.thomas_oster/visicut/gui/mapping/resources/CustomMappingPanel").getString("IGNORE");
    String filters = m.getFilterSet() != null ? m.getFilterSet().toString() : java.util.ResourceBundle.getBundle("de.thomas_oster/visicut/gui/mapping/resources/CustomMappingPanel").getString("EVERYTHING_ELSE");
    allfilters += (allfilters == "" ? "" : ", ") + filters;
    text = title.replace("$profile", profile).replace("$mapping", allfilters);

    //change colors to their html representation
    text = text.replaceAll("#([0-9a-fA-F]+)", "<span bgcolor='$1' color='$1'>bla</span>");
    if (isUnused)
    {
      text += " "+unused;
    }
    this.lp = m.getProfile();
    this.editableTablePanel1.setSaveButtonVisible(modified);
    this.jLabel1.setText(modified ? "<html><b>"+text+"*</b></html>" : "<html>"+text+"</html>");
    this.jLabel2.setText(m.getProfile().settingsToString());
  }
  
  boolean modified = false;
  /**
   * Changes THE APPEARANCE of this table so it will indicate to the user,
   * that the settings are modified. (i.e. a * will be appended to the title
   * and the save button will become visible)
   * @param modified 
   */
  public void setModified(boolean modified)
  {
    if (this.modified != modified)
    {
      this.modified = modified;
      this.editableTablePanel1.setSaveButtonVisible(modified);
      this.editableTablePanel1.setRevertButtonVisible(modified);
      this.jLabel1.setText(modified ? "<html><b>"+text+"*</b></html>" : "<html>"+text+"</html>");
    }
  }
  
  public void setLaserProperties(List<LaserProperty> props)
  {
    if (props != null)
    {
      this.laserproperties = props;
    }
    else
    {
      this.laserproperties = new LinkedList<LaserProperty>();
      LaserProperty n = (LaserProperty) this.getNewInstance();
      if (n != null)
      {
        this.laserproperties.add((LaserProperty) this.getNewInstance());
      }
    }
    this.model.setLaserProperties(this.laserproperties);
    this.editableTablePanel1.setObjects((List) this.laserproperties);
    this.setModified(false);
  }
  
  public List<LaserProperty> getLaserProperties()
  {
    return laserproperties;
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

    jLabel1 = new javax.swing.JLabel();
    editableTablePanel1 = new de.thomas_oster.uicomponents.EditableTablePanel();
    editMappingButton = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();

    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de.thomas_oster/visicut/gui/resources/PropertyPanel"); // NOI18N
    jLabel1.setText(bundle.getString("PROPERTY_TITLE")); // NOI18N

    editableTablePanel1.setEditButtonVisible(false);

    editMappingButton.setText("Edit");
    editMappingButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        editMappingButtonActionPerformed(evt);
      }
    });

    jLabel2.setText("<PROPERTY_PROFILE_SETTINGS>");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(editableTablePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(editMappingButton))))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(editMappingButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(editableTablePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void editMappingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMappingButtonActionPerformed
      LaserProfile changedProfile = MainView.getInstance().editLaserProfile(lp);
      if (changedProfile == null)
      {
        return;
      }
      lp = changedProfile;
      // TODO save the result
    }//GEN-LAST:event_editMappingButtonActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton editMappingButton;
  private de.thomas_oster.uicomponents.EditableTablePanel editableTablePanel1;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  // End of variables declaration//GEN-END:variables

  public Object getNewInstance()
  {
    LaserDevice ld = VisicutModel.getInstance().getSelectedLaserDevice();
    if (lp != null && ld != null)
    {
      if (lp instanceof VectorProfile)
      {
        return ld.getLaserCutter().getLaserPropertyForVectorPart();
      }
      else if (lp instanceof RasterProfile)
      {
        return ld.getLaserCutter().getLaserPropertyForRasterPart();
      }
      else if (lp instanceof Raster3dProfile)
      {
        return ld.getLaserCutter().getLaserPropertyForRaster3dPart();
      }
    }
    return null;
  }

  public Object editObject(Object o)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  void addSaveListener(final ActionListener saveListener)
  {
    this.editableTablePanel1.getSaveButton().addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent ae)
      {
        saveListener.actionPerformed(new ActionEvent(PropertyPanel.this, ae.getID(), ae.getActionCommand()));
      }
    });
  }
  
  void addRevertListener(final ActionListener saveListener)
  {
    this.editableTablePanel1.getRevertButton().addActionListener(new ActionListener(){

      public void actionPerformed(ActionEvent ae)
      {
        saveListener.actionPerformed(new ActionEvent(PropertyPanel.this, ae.getID(), ae.getActionCommand()));
      }
    });
  }

}
