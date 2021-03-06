package com.chat.client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 当关闭按钮时，给用户是否退出确认反馈
 */
public class WindowClosingMessage extends WindowAdapter {

    /**
     * 当用户单击关闭按钮时，触发WindowEvent事件
     * @param e
     */
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
