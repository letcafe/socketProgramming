package com.chat.client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowClosingMessage extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示",
                JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            System.exit(0);  //关闭
        }
    }
}
