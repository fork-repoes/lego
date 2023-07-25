package com.geekhalo.lego.plugin.ui;

import com.geekhalo.lego.plugin.creator.JavaFileCreator;
import com.geekhalo.lego.plugin.template.CommandTemplate;
import com.geekhalo.lego.plugin.template.ContextTemplate;
import com.geekhalo.lego.plugin.template.CreateClassContext;
import com.geekhalo.lego.plugin.template.DomainEventTemplate;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.Arrays;

import static com.geekhalo.lego.plugin.ui.MethodNamePair.parseMethod;
import static com.geekhalo.lego.plugin.util.Utils.*;

public class CreateAggregationMethodDialog extends JDialog {
    private final Project project;
    private final PsiClass aggClass;
    private Module appModule;
    private Module domainModule;
    private Module infraModule;
    private String aggPackage;
    private String aggClassName;
    private String aggFullClassName;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField aggClassText;
    private JButton selectAggButton;
    private JTextField aggMethodName;
    private JCheckBox singlePackaged;
    private JTextField commandCLass;
    private JTextField contextClass;
    private JTextField eventClass;
    private JComboBox commandType;
    private JTextField keyTypeForCommand;
    private JPanel keyTypeSelectPanel;
    private JButton selectKeyTypeButton;

    private void init(){

        aggMethodName.getDocument()
                .addDocumentListener(new DocumentUpdateListener(this::updateByAggMethodName));

        // 选择聚合根
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
                updateByAggClassName(selectedClass.getQualifiedName());
                updateByAggMethodName(aggMethodName.getText());
            }
        });

        // 选择 ID 或 Key 类型
        this.selectKeyTypeButton.addActionListener(e->{
            TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
            // 设置过滤条件，只选择 public 类型的类
            ClassFilter classFilter = psiClass -> psiClass.getModifierList().hasExplicitModifier(PsiModifier.PUBLIC);
            // 弹出选择类的窗口
            TreeClassChooser chooser = factory.createWithInnerClassesScopeChooser("选择类型", GlobalSearchScope.allScope(project), classFilter, null);
            chooser.showDialog();
            // 获取用户选择的类
            PsiClass selectedClass = chooser.getSelected();
            // 如果用户选择了类，则在控制台输出类名
            if (selectedClass != null) {
                keyTypeForCommand.setText(selectedClass.getQualifiedName());
            }
        });

        this.aggClassText.setText(aggFullClassName);

    }

    /**
     * 根据方法名，更新 Command、Context、Event 类名
     * @param methodName
     */
    private void updateByAggMethodName(String methodName){
        if (StringUtils.isNotEmpty(methodName)){
            MethodNamePair methodNamePair = parseMethod(methodName, this.aggClassName);

            this.commandCLass.setText(createCommandTypeName(methodNamePair));

            this.contextClass.setText(createContextTypeName(methodNamePair));

            this.eventClass.setText(createDomainEventTypeName(methodNamePair));
        }else {
            this.commandCLass.setText("");
            this.contextClass.setText("");
            this.eventClass.setText("");
        }
    }


    /**
     * 聚合变化后，更新 package 和 class
     * @param aggFullClassName
     */
    private void updateByAggClassName(String aggFullClassName){
        if (StringUtils.isEmpty(aggFullClassName)){
            return;
        }
        this.aggFullClassName = aggFullClassName;
        int index = aggFullClassName.lastIndexOf('.');
        if (index > 0) {
            this.aggClassName = aggFullClassName.substring(index + 1);
            this.aggPackage = aggFullClassName.substring(0, index);
        }else {
            this.aggClassName = aggFullClassName;
        }
    }




    public CreateAggregationMethodDialog(Project project,
                                         Module appModule, Module domainModule, Module infraModule,
                                         PsiClass aggClass) {
        this.project = project;
        this.aggClass = aggClass;
        this.appModule = appModule;
        this.domainModule = domainModule;
        this.infraModule = infraModule;

        updateByAggClassName(aggClass.getQualifiedName());
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
        String basePkg = createBasePackage();

        addFiles(basePkg);
        addMethod(basePkg);

        dispose();
    }

    private void addMethod(String basePkg){
        String methodName = this.aggMethodName.getText();
        createAggMethod(basePkg, methodName);
        createAppMethod(basePkg, methodName);
    }

    private void createAppMethod(String basePkg, String methodName) {
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        String commandType = String.valueOf(this.commandType.getSelectedItem());
        String aggType = this.aggPackage + "." + this.aggClassName;
        String commandClass = basePkg + "." + this.commandCLass.getText();

        PsiClass commandApplication = findCommandApplication(domainPkgToApp(this.aggPackage), createCommandApplicationByAgg(this.aggClassName));

        PsiMethod newMethod = null;
        if ("create".equalsIgnoreCase(commandType)){
              String methodSignatureString =  aggType + " " + methodName + "(" + this.commandCLass.getText() + " command);";
              newMethod = elementFactory.createMethodFromText(methodSignatureString, commandApplication);
        }else {
            String methodSignatureString =  "void " + methodName + "(" + this.commandCLass.getText() + " command);";
            newMethod = elementFactory.createMethodFromText(methodSignatureString, commandApplication);
        }

        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        PsiMethod methodToAdd = newMethod;
        WriteCommandAction.runWriteCommandAction(project, ()->{
            // 将新方法添加到目标类
            commandApplication.add(methodToAdd);

            // 将 import 语句添加到 PsiJavaFile 的 import 列表中
            PsiJavaFile javaFile = (PsiJavaFile) commandApplication.getContainingFile();
            PsiImportList importList = javaFile.getImportList();

            {
                PsiClass psiClass = javaPsiFacade.findClass(commandClass, GlobalSearchScope.allScope(this.project));
                if (psiClass!=null && !hasImports(importList, psiClass)) {
                    importList.add(elementFactory.createImportStatement(psiClass));
                }
            }
            {
                PsiClass psiClass = javaPsiFacade.findClass(aggType, GlobalSearchScope.allScope(this.project));
                if (psiClass != null && !hasImports(importList, psiClass)) {
                    importList.add(elementFactory.createImportStatement(psiClass));
                }
            }
        });
    }

    private boolean hasImports(PsiImportList importList, PsiClass psiClass) {
        return Arrays.asList(importList.getImportStatements())
                .stream()
                .map(PsiImportStatement::getQualifiedName)
                .anyMatch(qualifiedName ->
                        psiClass.getQualifiedName().equals(qualifiedName)
                );
    }

    private PsiClass findCommandApplication(String commandApplicationPkg, String commandApplicationType) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(this.appModule);
        VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots();

        VirtualFile sourceFile = findSourceFile(sourceRoots);
        if (sourceFile == null){
            Messages.showMessageDialog("请先为 " + this.appModule.getName() + " 模块创建 Java 源码目录", "Warn", null);
            return null;
        }

        PsiDirectory sourceDirectory = PsiManager.getInstance(project)
                .findDirectory(sourceFile);

        // 根据包路径获取指定的 PsiDirectory
        PsiDirectory tmp = sourceDirectory;
        for (String dir : commandApplicationPkg.split("\\.")){
            PsiDirectory cDirectory = tmp.findSubdirectory(dir);
            if (cDirectory != null){
                tmp = cDirectory;
            }else {
                break;
            }
        }
        if (tmp != null){
            PsiFile file = tmp.findFile(commandApplicationType + ".java");
            if (file != null && file instanceof PsiJavaFile){
                PsiJavaFile javaFile = (PsiJavaFile)file;
                PsiClass[] classes = javaFile.getClasses();
                return classes[0];
            }
        }
        return null;
    }

    private void createAggMethod(String basePkg, String methodName) {
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiMethod newMethod = null;
        String contextType = basePkg + "." + this.contextClass.getText();
        String commandType = String.valueOf(this.commandType.getSelectedItem());
        String aggType = this.aggPackage + "." + this.aggClassName;
        if ("create".equalsIgnoreCase(commandType)){
            // 创建新方法
            PsiType returnType =  PsiType.getTypeByName(aggType, project, GlobalSearchScope.allScope(project));
            newMethod = elementFactory.createMethod(methodName, returnType);
            newMethod.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
            newMethod.getModifierList().setModifierProperty(PsiModifier.STATIC, true);

            PsiParameter newParam = elementFactory.createParameter("context", PsiType.getTypeByName(contextType, project, GlobalSearchScope.allScope(project)));
            newMethod.getParameterList().add(newParam);

            String methodBody = "{\n" +
                    "       // 添加代码 \n" +
                    "       return new " + this.aggClassName +"();" +
                    "}";
            // 设置方法体
            PsiCodeBlock codeBlock = elementFactory.createCodeBlockFromText(methodBody, null);
            newMethod.getBody().replace(codeBlock);

        }else {
            // 创建新方法
            newMethod = elementFactory.createMethod(methodName, PsiType.VOID);
            newMethod.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
            // 创建新参数
            PsiParameter newParam = elementFactory.createParameter("context", PsiType.getTypeByName(contextType, project, GlobalSearchScope.allScope(project)));
            newMethod.getParameterList().add(newParam);

            String body =
                    "{ \n" +
                    "       //添加代码 \n" +
                    "addEvent(new " + this.eventClass.getText()  +"(this));\n"
                    + "}";
            // 设置方法体
            PsiCodeBlock codeBlock = elementFactory.createCodeBlockFromText(body, null);
            newMethod.getBody().replace(codeBlock);
        }

        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        final PsiMethod methodToAdd = newMethod;
        WriteCommandAction.runWriteCommandAction(project, ()->{
            // 将新方法添加到目标类
            aggClass.add(methodToAdd);

            // 将 import 语句添加到 PsiJavaFile 的 import 列表中
            PsiJavaFile javaFile = (PsiJavaFile) aggClass.getContainingFile();
            PsiImportList importList = javaFile.getImportList();
            {
                PsiClass psiClass = javaPsiFacade.findClass(basePkg + "." + commandCLass.getText(), GlobalSearchScope.allScope(this.project));
                if (psiClass!=null) {
                    importList.add(elementFactory.createImportStatement(psiClass));
                }
            }
            {
                PsiClass psiClass = javaPsiFacade.findClass(basePkg + "." + contextClass.getText(), GlobalSearchScope.allScope(this.project));
                if (psiClass != null) {
                    importList.add(elementFactory.createImportStatement(psiClass));
                }
            }
            {
                PsiClass psiClass = javaPsiFacade.findClass(basePkg + "." + eventClass.getText(), GlobalSearchScope.allScope(this.project));
                if (psiClass != null) {
                    importList.add(elementFactory.createImportStatement(psiClass));
                }
            }
        });
    }

    private void addFiles(String basePkg){

        // Command
        {
            String content = getCreateCommandContent(basePkg);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule, basePkg, this.commandCLass.getText(), content);
        }

        // Context
        {
            ContextTemplate.CreateContextContext context = new ContextTemplate.CreateContextContext(basePkg, this.contextClass.getText());
            String commandTypeFull = createBasePackage() + "." + this.commandCLass.getText();
            context.setCommandTypeFull(commandTypeFull);
            String content = ContextTemplate.create(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule, basePkg, this.contextClass.getText(), content);
        }

        // Event
        {
            DomainEventTemplate.CreateDomainEventContext context = new DomainEventTemplate.CreateDomainEventContext(basePkg, this.eventClass.getText());
            context.setAggTypeFull(this.aggFullClassName);
            String absEvent = aggPackage + "." + createAbstractDomainEventByAgg(this.aggClassName);
            context.setParentTypeFull(absEvent);
            String content = DomainEventTemplate.createEvent(context);
            JavaFileCreator.createJavaFileInPackage(this.project, this.domainModule, basePkg, this.eventClass.getText(), content);
        }

    }

    private void bindCommon(CreateClassContext context) {
        context.setAggTypeFull(this.aggFullClassName);
        context.setIdTypeFull(this.keyTypeForCommand.getText());
    }

    private String getCreateCommandContent(String basePkg){
        String commandType = String.valueOf(this.commandType.getSelectedItem());
        if ("create".equalsIgnoreCase(commandType)) {
            CommandTemplate.CreateCreateCommandContext context = new CommandTemplate.CreateCreateCommandContext(basePkg, this.commandCLass.getText());
            bindCommon(context);
            return CommandTemplate.createCreateCommand(context);
        }

        if ("updateById".equalsIgnoreCase(commandType)) {
            CommandTemplate.CreateUpdateCommandContext context = new CommandTemplate.CreateUpdateCommandContext(basePkg, this.commandCLass.getText());
            bindCommon(context);
            return CommandTemplate.createUpdateCommand(context);
        }

        if ("updateByKey".equalsIgnoreCase(commandType)) {
            CommandTemplate.CreateUpdateCommandContext context = new CommandTemplate.CreateUpdateCommandContext(basePkg, this.commandCLass.getText());
            bindCommon(context);
            context.setKeyTypeFull(this.keyTypeForCommand.getText());
            return CommandTemplate.createUpdateByKeyCommand(context);
        }

        if ("sync".equalsIgnoreCase(commandType)) {
            CommandTemplate.CreateSyncCommandContext context = new CommandTemplate.CreateSyncCommandContext(basePkg, this.commandCLass.getText());
            bindCommon(context);
            context.setKeyTypeFull(this.keyTypeForCommand.getText());
            return CommandTemplate.createSyncCommand(context);
        }
        return null;
    }

    private String createBasePackage() {
        if (this.singlePackaged.isSelected()){
            return aggPackage + "." + this.aggMethodName.getText();
        }
        return aggPackage;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
