package com.geekhalo.lego.plugin.ui;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import javax.swing.*;
import java.awt.event.*;

public class CreateAggDialog extends JDialog {
    private Project project;
    private PsiFile psiFile;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel buttonsPanel;
    private JPanel buttons;
    private JPanel Form;
    private JPanel titlePanel;
    private JPanel aggPanel;
    private JPanel repositoryPanel;
    private JTextField textField1;
    private JButton 请选择Button;
    private JTextField textField2;
    private JTextField textField3;
    private JButton 请选择Button1;
    private JTextField textField4;
    private JButton 请选择Button2;
    private JTextField textField5;
    private JTextField textField6;
    private JPanel applicationService;
    private JTextField textField7;
    private JButton 请选择Button3;
    private JTextField textField8;
    private JTextField textField9;
    private JPanel domainEvent;
    private JTextField textField10;
    private JButton 请选择Button4;
    private JTextField textField11;

    public CreateAggDialog(Project project, PsiFile psiFile) {
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CreateAggDialog dialog = new CreateAggDialog(null, null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
