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
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class CreateAggregationMethodDialog extends JDialog {
    private final Project project;
    private final PsiClass aggClass;
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
                updateByAggClassName(selectedClass.getQualifiedName());
                updateByAggMethodName(aggMethodName.getText());
            }
        });

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

        this.commandType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = String.valueOf(commandType.getSelectedItem());
                if ("updateById".equalsIgnoreCase(command)
                        || "updateByKey".equalsIgnoreCase(command)
                        || "sync".equalsIgnoreCase(command)){
                    keyTypeSelectPanel.show(true);
                }else {
                    keyTypeSelectPanel.show(false);
                }

            }
        });
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

    public CreateAggregationMethodDialog(Project project, PsiClass aggClass) {
        this.project = project;
        this.aggClass = aggClass;
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
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();


        PsiMethod newMethod = elementFactory.createMethod(methodName, PsiType.VOID);
        String contextType = basePkg + "." + this.contextClass.getText();
        String commandType = String.valueOf(this.commandType.getSelectedItem());
        if ("create".equalsIgnoreCase(commandType)){
            // 创建新方法
//            newMethod = elementFactory.createMethod(methodName, PsiType.VOID);
//            newMethod.getModifierList().setModifierProperty(PsiModifier.PUBLIC, true);
//
//            // 设置方法体
//            PsiCodeBlock codeBlock = elementFactory.createCodeBlockFromText(methodBody, null);
//            newMethod.getBody().replace(codeBlock);
//            targetMethod.getReturnTypeElement().replace(elementFactory.createTypeElement(PsiType.getTypeByName(returnType, project, null)));

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
            JavaFileCreator.createJavaFileInPackage(this.project, basePkg, this.commandCLass.getText(), content);
        }

        // Context
        {
            ContextTemplate.CreateContextContext context = new ContextTemplate.CreateContextContext(basePkg, this.contextClass.getText());
            String commandTypeFull = createBasePackage() + "." + this.commandCLass.getText();
            context.setCommandTypeFull(commandTypeFull);
            String content = ContextTemplate.create(context);
            JavaFileCreator.createJavaFileInPackage(this.project, basePkg, this.contextClass.getText(), content);
        }

        // Event
        {
            DomainEventTemplate.CreateDomainEventContext context = new DomainEventTemplate.CreateDomainEventContext(basePkg, this.eventClass.getText());
            context.setAggTypeFull(this.aggFullClassName);
            String absEvent = aggPackage + "." + "Abstract" + this.aggClassName + "Event";
            context.setParentTypeFull(absEvent);
            String content = DomainEventTemplate.createEvent(context);
            JavaFileCreator.createJavaFileInPackage(this.project, basePkg, this.eventClass.getText(), content);
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
