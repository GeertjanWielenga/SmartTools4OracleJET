package org.netbeans.modules.st4oj.wizards;

import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

public final class OracleJETVisualPanel extends JPanel {

    private static final String sourceExt = "js"; // NOI18N
    private static final String headerExt = "html"; // NOI18N
    private Project project;
    private SourceGroup[] folders;

    private final ChangeSupport changeSupport = new ChangeSupport(this);

    public OracleJETVisualPanel(Project project, SourceGroup[] folders) {
        this.project = project;
        this.folders = folders;

        initComponents();
        initValues(null, null, null);
    }

    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    @Override
    public String getName() {
        return "Name and Location";
    }

    public String getClassName() {
        return classNameTextField.getText().trim();
    }

    public void initValues(FileObject template, FileObject preselectedFolder, String documentName) {
        assert project != null;

        Sources sources = ProjectUtils.getSources(project);

        folders = sources.getSourceGroups(Sources.TYPE_GENERIC);

        if (folders.length < 2) {
            // one source group i.e. hide Location
            locationLabel.setVisible(false);
            locationComboBox.setVisible(false);
        } else {
            // more source groups user needs to select location
            locationLabel.setVisible(true);
            locationComboBox.setVisible(true);
        }

        locationComboBox.setModel(new DefaultComboBoxModel(folders));
        // Guess the group we want to create the file in
        SourceGroup preselectedGroup = getPreselectedGroup(folders, preselectedFolder);
        locationComboBox.setSelectedItem(preselectedGroup);
        // Create OS dependent relative name
        String relPreselectedFolder = getRelativeNativeName(preselectedGroup.getRootFolder(), preselectedFolder);
        javaScriptFolderField.setText(relPreselectedFolder);

        String displayName = null;
        try {
            if (template != null) {
                DataObject templateDo = DataObject.find(template);
                displayName = templateDo.getNodeDelegate().getDisplayName();
            }
        } catch (DataObjectNotFoundException ex) {
            displayName = template.getName();
        }
        putClientProperty("NewFileWizard_Title", displayName);// NOI18N

        if (template != null) {
            if (documentName == null) {
                final String baseName = getMessage("NewClassSuggestedName");
                documentName = baseName;
                FileObject currentFolder = preselectedFolder != null ? preselectedFolder : getTargetGroup().getRootFolder();
                if (currentFolder != null) {
                    documentName += generateUniqueSuffix(
                            currentFolder, documentName,
                            sourceExt, headerExt);
                }

            }
            classNameTextField.setText(documentName);
            classNameTextField.selectAll();
        }

    }

    public SourceGroup getTargetGroup() {
        Object selectedItem = locationComboBox.getSelectedItem();
        if (selectedItem == null) {
            // workaround for MacOS, see IZ 175457
            selectedItem = locationComboBox.getItemAt(locationComboBox.getSelectedIndex());
            if (selectedItem == null) {
                selectedItem = locationComboBox.getItemAt(0);
            }
        }
        return (SourceGroup) selectedItem;
    }

    public String getTargetFolder() {
        String folderName = javaScriptFolderField.getText().trim();

        if (folderName.length() == 0) {
            return "";
        } else {
            return folderName.replace(File.separatorChar, '/'); // NOI18N
        }
    }

    protected static String getRelativeNativeName(FileObject root, FileObject folder) {
        if (root == null) {
            throw new NullPointerException("null root passed to getRelativeNativeName"); // NOI18N
        }

        String path;

        if (folder == null) {
            path = ""; // NOI18N
        } else {
            path = FileUtil.getRelativePath(root, folder);
        }

        return path == null ? "" : path.replace('/', File.separatorChar); // NOI18N
    }

    protected static SourceGroup getPreselectedGroup(SourceGroup[] groups, FileObject folder) {
        for (int i = 0; folder != null && i < groups.length; i++) {
            if (FileUtil.isParentOf(groups[i].getRootFolder(), folder)
                    || groups[i].getRootFolder().equals(folder)) {
                return groups[i];
            }
        }
        return groups[0];
    }

    protected static String generateUniqueSuffix(FileObject folder, String prefix, String... extensions) {
        for (int i = 0; true; ++i) {
            String suffix = i == 0 ? "" : String.valueOf(i);
            String filename = prefix + suffix;
            boolean unique = true;
            for (String ext : extensions) {
                if (folder.getFileObject(filename, ext) != null) {
                    unique = false;
                    break;
                }
            }
            if (unique) {
                return suffix;
            }
        }
    }

    protected static String getMessage(String name) {
        return NbBundle.getMessage(OracleJETVisualPanel.class, name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        classNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        javaScriptFolderField = new javax.swing.JTextField();
        browseJSButton = new javax.swing.JButton();
        locationComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        locationLabel = new javax.swing.JLabel();

        classNameTextField.setText(org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.classNameTextField.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.jLabel3.text")); // NOI18N

        javaScriptFolderField.setText(org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.javaScriptFolderField.text")); // NOI18N
        javaScriptFolderField.setEnabled(false);
        javaScriptFolderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                javaScriptFolderFieldActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(browseJSButton, org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.browseJSButton.text")); // NOI18N
        browseJSButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseJSButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(locationLabel, org.openide.util.NbBundle.getMessage(OracleJETVisualPanel.class, "OracleJETVisualPanel.locationLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(locationLabel)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(javaScriptFolderField, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseJSButton))
                    .addComponent(classNameTextField)
                    .addComponent(locationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(classNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(locationLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(javaScriptFolderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseJSButton))
                .addContainerGap(44, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void browseJSButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseJSButtonActionPerformed
        // Show the browse dialog
        SourceGroup group = getTargetGroup();
        FileObject fo = BrowseFolders.showDialog(new SourceGroup[]{group},
                project,
                javaScriptFolderField.getText().replace(File.separatorChar, '/')); // NOI18N

        if (fo != null && fo.isFolder()) {
            String relPath = FileUtil.getRelativePath(group.getRootFolder(), fo);
            javaScriptFolderField.setText(relPath.replace('/', File.separatorChar)); // NOI18N
        }
    }//GEN-LAST:event_browseJSButtonActionPerformed

    private void javaScriptFolderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaScriptFolderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_javaScriptFolderFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseJSButton;
    private javax.swing.JTextField classNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField javaScriptFolderField;
    private javax.swing.JComboBox locationComboBox;
    private javax.swing.JLabel locationLabel;
    // End of variables declaration//GEN-END:variables
}
