package org.netbeans.modules.st4oj.rest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.EditorCookie;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.StatusDisplayer;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Tools",
        id = "org.netbeans.modules.st4oj.rest.GenerateRESTClient"
)
@ActionRegistration(
        displayName = "#CTL_GenerateRESTClient"
)
@ActionReference(path = "Editors/text/javascript/Popup", position = 10)
@Messages("CTL_GenerateRESTClient=Generate REST Client")
public final class GenerateRESTClient implements ActionListener {
    
    String template = "$.getJSON(\"http://www.openligadb.de/api/getmatchdata/EM-2016/2016\",\n" +
"function (data) {\n" +
"    console.log(data);\n" +
"});";

    private final EditorCookie ec;

    public GenerateRESTClient(EditorCookie context) {
        this.ec = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        NotifyDescriptor.InputLine line = new NotifyDescriptor.InputLine("","");
        DialogDisplayer.getDefault().notify(line);
        String urlToRest = line.getInputText();
        StatusDisplayer.getDefault().setStatusText(urlToRest);
        if (ec != null) {
        JEditorPane[] panes = ec.getOpenedPanes();
        if (panes.length > 0) {
//            int cursor = panes[0].getCaret().getDot();
//            String selection = panes[0].getSelectedText();
            // USE selection
            panes[0].replaceSelection(template);
        }
    }
        
    }
    
}
