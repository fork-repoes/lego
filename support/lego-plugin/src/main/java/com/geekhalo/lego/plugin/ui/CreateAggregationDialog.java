package com.geekhalo.lego.plugin.ui;


import com.geekhalo.lego.plugin.creator.JavaFileCreator;
import com.geekhalo.lego.plugin.template.AggregationTemplate;
import com.geekhalo.lego.plugin.template.CreateClassContext;
import com.geekhalo.lego.plugin.template.DomainEventTemplate;
import com.geekhalo.lego.plugin.template.RepositoryTemplate;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class CreateAggregationDialog extends JDialog {
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
    private JTextField aggPackage;
    private JButton selectPkgForAgg;
    private JTextField aggClassName;
    private JTextField aggParentClass;
    private JButton aggParentSelectButton;
    private JTextField repositoryPkg;
    private JButton repositoryPackageSelectButton;
    private JTextField commandRepository;
    private JTextField queryRepository;
    private JPanel applicationService;
    private JTextField applicationPkg;
    private JButton applicationPackageSelectButton;
    private JTextField commandApplication;
    private JTextField queryApplication;
    private JPanel domainEvent;
    private JTextField domainEventPkg;
    private JButton domainEventPackageSelectButton;
    private JTextField domainEventClass;
    private JTextField aggIdTextField;
    private JButton aggIdTypeSelectButton;

    private void init(Project project, String pkg, PsiFile psiFile){
        this.project = project;
//        project.getRoot
//        ChooseModulesDialog chooseModulesDialog = new ChooseModulesDialog(project, project.)
        updateByPackage(pkg);
        this.aggPackage.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateByPackage(aggPackage.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateByPackage(aggPackage.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateByPackage(aggPackage.getText());
            }
        });
        // 选择 Agg 所在包
        selectPkgForAgg.addActionListener(e -> {
            PackageChooserDialog chooserDialog = new PackageChooserDialog("请选择包", project);
            if(chooserDialog.showAndGet()) {
                PsiPackage selectedPackage = chooserDialog.getSelectedPackage();
                if (selectedPackage != null) {
                    aggPackage.setText(selectedPackage.getQualifiedName());
                }
            }
        });

        this.aggParentSelectButton.addActionListener(e -> {
            TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
            // 设置过滤条件，只选择 public 类型的类
//                Condition<PsiClass> condition = psiClass -> PsiModifier.PUBLIC == psiClass.getModifierList()();
            ClassFilter classFilter = psiClass -> psiClass.getModifierList().hasExplicitModifier(PsiModifier.PUBLIC);
            // 弹出选择类的窗口
            TreeClassChooser chooser = factory.createWithInnerClassesScopeChooser("选择聚合根父类", GlobalSearchScope.allScope(project), classFilter, null);
            chooser.showDialog();
            // 获取用户选择的类
            PsiClass selectedClass = chooser.getSelected();
            // 如果用户选择了类，则在控制台输出类名
            if (selectedClass != null) {
                aggParentClass.setText(selectedClass.getQualifiedName());
            }
        });

        this.aggClassName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshByAggNameChanged(aggClassName.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshByAggNameChanged(aggClassName.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshByAggNameChanged(aggClassName.getText());
            }
        });
    }

    private void updateByPackage(String pkg) {
        if (StringUtils.isNotEmpty(pkg)) {
            aggPackage.setText(pkg);
            repositoryPkg.setText(pkg);
            domainEventPkg.setText(pkg);

            String appPkg = pkg.replace("domain", "app");
            applicationPkg.setText(appPkg);
        }

    }

    private void refreshByAggNameChanged(String aggClass){
        String commandRepository = aggClass + "CommandRepository";
        this.commandRepository.setText(commandRepository);

        String queryRepository = aggClass + "QueryRepository";
        this.queryRepository.setText(queryRepository);

        String commandApplication = aggClass + "CommandApplication";
        this.commandApplication.setText(commandApplication);

        String queryApplication = aggClass + "QueryApplication";
        this.queryApplication.setText(queryApplication);

        String domainEvent = "Abstract" + aggClass + "Event";
        this.domainEventClass.setText(domainEvent);
    }

    public CreateAggregationDialog(Project project, String pkg, PsiFile psiFile) {

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

        init(project, pkg, psiFile);
    }

    private void onOK() {
        // add your code here
        createFile();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createFile(){
        {
            AggregationTemplate.CreateAggregationContext context = new AggregationTemplate.CreateAggregationContext(this.aggPackage.getText(), this.aggClassName.getText());
            bindCommon(context);
            context.setParentClassFull(aggParentClass.getText());
            String content = AggregationTemplate.create(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.aggPackage.getText(), this.aggClassName.getText(), content);
        }

        {
            RepositoryTemplate.CreateCommandRepositoryContext context = new RepositoryTemplate.CreateCommandRepositoryContext(this.repositoryPkg.getText(), this.commandRepository.getText());
            bindCommon(context);
            String commandContent = RepositoryTemplate.createCommand(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.repositoryPkg.getText(), this.commandRepository.getText(), commandContent);
        }
        {
            RepositoryTemplate.CreateQueryRepositoryContext context = new RepositoryTemplate.CreateQueryRepositoryContext(this.repositoryPkg.getText(), this.queryRepository.getText());
            bindCommon(context);
            String queryContent = RepositoryTemplate.createQuery(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.repositoryPkg.getText(), this.queryRepository.getText(), queryContent);
        }
        {
            DomainEventTemplate.CreateAbstractDomainEventContext context = new DomainEventTemplate.CreateAbstractDomainEventContext(this.domainEventPkg.getText(), this.domainEventClass.getText());
            bindCommon(context);
            String domainContent = DomainEventTemplate.createAbstractEvent(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainEventPkg.getText(), this.domainEventClass.getText(), domainContent);
        }

    }

    private void bindCommon(CreateClassContext context){
        context.setIdTypeFull(this.aggIdTextField.getText());
        context.setAggTypeFull(this.aggPackage.getText() + "." + this.aggClassName.getText());
    }

//    public static void main(String[] args) {
//        CreateAggregationDialog dialog = new CreateAggregationDialog(null, "", null);
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }
}
