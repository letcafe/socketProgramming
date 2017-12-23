package com.chat.client;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 实现KeyListener接口，用于在文本框输入结束，直接敲击Enter，提交文本
 */
public class EnterPressSend implements KeyListener {

    JButton sendButton;

    public EnterPressSend(JButton sendButton) {
        this.sendButton = sendButton;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 敲击Enter，实现代码：模拟发送按钮单击事件，实现Enter发送
     * @param e 监听Keyevent事件参数对象
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
