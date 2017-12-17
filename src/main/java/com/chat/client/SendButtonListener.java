package com.chat.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class SendButtonListener implements ActionListener {

    PrintWriter writer;
    JTextField outputStream;

    public SendButtonListener(PrintWriter writer, JTextField outputStream) {
        this.writer = writer;
        this.outputStream = outputStream;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            writer.println(outputStream.getText());
            writer.flush();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        outputStream.setText("");
        outputStream.requestFocus();
    }
}
