package com.zq.pdf.service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SwingService extends JFrame {
    static final int WIDTH = 620;
    private JTextField inputField;
    private JLabel statusText;

    public SwingService(String title) {
        super(title);
    }

    public void start() {
        // 创建 JFrame 实例
        setLocationRelativeTo(null);
        setSize(WIDTH, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);

        placeComponents(panel);

        setVisible(true);
        setResizable(false);
    }

    private void placeComponents(JPanel panel) {

        panel.setLayout(null);
        // 创建 JLabel
        JLabel userLabel = new JLabel("pdf文件");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        inputField = new JTextField(20);
        inputField.setBounds(60, 20, 400, 25);
        panel.add(inputField);

        JButton button = new JButton("选择");
        button.setBounds(480, 20, 80, 25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                test1();
            }
        });
        panel.add(button);

        JButton genButton = new JButton("生成");
        genButton.addActionListener(new ButtonClickListener());

        statusText = new JLabel();
        statusText.setBounds(600, 40, 500, 25);

        panel.add(statusText);
        genButton.setBounds(WIDTH / 2 - 40, 70, 80, 25);
        panel.add(genButton);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = inputField.getText();
            System.out.println("操作：" + input + "...");
            setStatusText("操作中。。。");
            if (input == null || input.equals("")) {
                setStatusText("请输入或选择文件地址");
                return;
            }
            BootService bootService = new BootService();
            String res = bootService.start(input);
            setStatusText(res);
        }
    }

    //选择打开文件
    private void test1() {
        //创建一个文件选择器
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf文件", "pdf");
        chooser.setFileFilter(filter);

        //显示对话框
        int ret = chooser.showOpenDialog(this);
        //获取用户选择的结果
        if (ret == JFileChooser.APPROVE_OPTION) {
            //结果为：已经存在的一个文件
            File file = chooser.getSelectedFile();
            inputField.setText(file.getAbsolutePath());
        }
    }

    public void setStatusText(String msg) {
        statusText.setText(msg);
        System.out.println(msg);
    }
}