package com.geekhalo.lego.plugin.ui;

import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class CreateAggregationMethodDialog extends JDialog {
    private final Project project;
    private String aggClassName;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField aggClassText;
    private JButton selectAggButton;
    private JTextField aggMethodName;
    private JCheckBox 独立包CheckBox;
    private JTextField commandCLass;
    private JTextField contextClass;
    private JTextField eventClass;
    private JComboBox comboBox1;

    private void init(){
        aggMethodName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateByAggMethodName(aggMethodName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateByAggMethodName(aggMethodName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateByAggMethodName(aggMethodName.getText());
            }
        });

        this.selectAggButton.addActionListener(e -> {
            TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
            // 设置过滤条件，只选择 public 类型的类
            ClassFilter classFilter = psiClass -> psiClass.getModifierList().hasExplicitModifier(PsiModifier.PUBLIC);
            // 弹出选择类的窗口
            TreeClassChooser chooser = factory.createWithInnerClassesScopeChooser("选择聚合根", GlobalSearchScope.allScope(project), classFilter, null);
            chooser.showDialog();
            // 获取用户选择的类
            PsiClass selectedClass = chooser.getSelected();
            // 如果用户选择了类，则在控制台输出类名
            if (selectedClass != null) {
                aggClassText.setText(selectedClass.getQualifiedName());
            }
        });

        this.aggClassText.setText(aggClassName);
    }

    private void updateByAggMethodName(String methodName){

        if (StringUtils.isNotEmpty(methodName)){
            MethodNamePair methodNamePair = parseMethod(methodName, this.aggClassName);

            String commandName = methodNamePair.getAction() + methodNamePair.getTarget() + "Command";
            this.commandCLass.setText(commandName);

            String contextName = methodNamePair.getAction() + methodNamePair.getTarget() +"Context";
            this.contextClass.setText(contextName);


            String eventName = methodNamePair.getTarget() + methodNamePair.getPastAction() +"Event";
            this.eventClass.setText(eventName);
        }else {
            this.commandCLass.setText("");
            this.contextClass.setText("");
            this.eventClass.setText("");
        }
    }

    private MethodNamePair parseMethod(String methodName, String aggClassName) {
        Integer firstUp = -1;
        for (int i = 0; i< methodName.length(); i++){
            char c = methodName.charAt(i);
            if (Character.isUpperCase(c) && i != 0){
                firstUp = i;
            }
        }
        if (firstUp == -1){
            String action = WordUtils.capitalize(methodName);
            String target = aggClassName;
            return new MethodNamePair(action, target);
        }else {
            String action = WordUtils.capitalize(methodName.substring(0, firstUp));
            String target = WordUtils.capitalize(methodName.substring(firstUp));
            return new MethodNamePair(action, target);
        }
    }


    private final class MethodNamePair{
        private String action;
        private String target;

        MethodNamePair(String action, String target){
            this.action = action;
            this.target = target;
        }
        public String getPastAction(){
            if (action.endsWith("e")){
                return WordUtils.capitalize(action + "d");
            }else {
                return WordUtils.capitalize(action + "ed");
            }
        }

        public String getAction() {
            return action;
        }

        public String getTarget() {
            return target;
        }
    }

    public CreateAggregationMethodDialog(Project project, String aggClassName) {
        this.project = project;
        this.aggClassName = aggClassName;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        init();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
