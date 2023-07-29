package com.geekhalo.lego.plugin.ui;


import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.function.Consumer;

public class DocumentUpdateListener implements DocumentListener {
    private final Consumer<String> updater;

    public DocumentUpdateListener(Consumer<String> updater) {
        this.updater = updater;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        update(e.getDocument());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update(e.getDocument());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update(e.getDocument());
    }

    private void update(Document document){
        int length = document.getLength();
        try {
            String value = document.getText(0, length);
            this.updater.accept(value);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
