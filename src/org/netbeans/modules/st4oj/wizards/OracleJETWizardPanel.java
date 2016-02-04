package org.netbeans.modules.st4oj.wizards;

import java.io.IOException;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.ErrorManager;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class OracleJETWizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private OracleJETVisualPanel component;
    private Project project;
    private SourceGroup[] folders;

    public OracleJETWizardPanel(Project project, SourceGroup[] folders) {
        this.project = project;
        this.folders = folders;
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public OracleJETVisualPanel getComponent() {
        if (component == null) {
            component = new OracleJETVisualPanel(project, folders);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        // Try to preselect a folder
        FileObject preselectedTarget = Templates.getTargetFolder(wiz);
        // Try to preserve the already entered target name
        String targetName = Templates.getTargetName(wiz);
        // Init values
        component.initValues(Templates.getTemplate(wiz), preselectedTarget, targetName);

    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        if (WizardDescriptor.PREVIOUS_OPTION.equals(wiz.getValue())) {
            return;
        }
        if (!wiz.getValue().equals(WizardDescriptor.CANCEL_OPTION) && isValid()) {
            String name = component.getClassName();
            if (name.indexOf('/') > 0) { // NOI18N
                name = name.substring(name.lastIndexOf('/') + 1);
            }

            FileObject targetfo = getTargetFolderFromGUI();
            try {
                Templates.setTargetFolder(wiz, targetfo);
            } catch (IllegalArgumentException iae) {
                ErrorManager.getDefault().annotate(iae, ErrorManager.EXCEPTION, null,
                        NbBundle.getMessage(OracleJETWizardPanel.class, "MSG_Cannot_Create_Folder",
                        component.getTargetFolder()), null, null);
                throw iae;
            }
            Templates.setTargetName(wiz, name);

        }
    }

    private FileObject getTargetFolderFromGUI() {
        FileObject rootFolder = component.getTargetGroup().getRootFolder();
        String folderName = component.getTargetFolder();
        String newObject = component.getClassName();

        if (newObject.indexOf('/') > 0) { // NOI18N
            String path = newObject.substring(0, newObject.lastIndexOf('/')); // NOI18N
            folderName = folderName == null || "".equals(folderName) ? path : folderName + '/' + path; // NOI18N
        }

        FileObject targetFolder;
        if (folderName == null || folderName.length() == 0) {
            targetFolder = rootFolder;
        } else {
            targetFolder = rootFolder.getFileObject(folderName);
        }

        if (targetFolder == null) {
            // XXX add deletion of the file in uninitalize of the wizard
            try {
                targetFolder = FileUtil.createFolder(rootFolder, folderName);
            } catch (IOException ioe) {
                // XXX
                // Can't create the folder
            }
        }

        return targetFolder;
    }
}
