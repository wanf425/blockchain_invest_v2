package com.wt.blockchainivest.swing;

import com.wt.blockchainivest.api.InvestApplicationI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 资金投入页面
 *
 * @author wangtao
 */
@Component
public class PutMoneyWindow extends BaseWindow {

    private static final long serialVersionUID = -9155959405640988640L;

    @Autowired
    private InvestApplicationI investApplicationImpl;
    @Autowired
    private BuySellStreamWindow buySellStreamWindow;

    private JFrame frame;
    private JTextField putMoneyTF;
    private JLabel putMoneyLA = new JLabel("投入金额（RMB）：");


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PutMoneyWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initAndShow(Object... args) {
        initialize();
        this.frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 262, 91);
        resetFrame(frame);

        putMoneyTF = new JTextField();
        putMoneyTF.setColumns(10);

        JButton saveBtn = new JButton("保存");
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addGap(4).addComponent(putMoneyLA).addGap(4).addComponent(
                        putMoneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(groupLayout.createSequentialGroup().addGap(180).addComponent(saveBtn)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addGap(5)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup().addGap(5).addComponent(putMoneyLA))
                                .addComponent(putMoneyTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGap(5).addComponent(saveBtn)));
        frame.getContentPane().setLayout(groupLayout);

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Double putMoney = 0.0;

                try {
                    putMoney = Double.valueOf(putMoneyTF.getText());
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "请输入正确的金额");
                }

                try {
                    investApplicationImpl.putMonet(putMoney);
                    JOptionPane.showMessageDialog(null, "保存成功！");

                    if (buySellStreamWindow != null) {
                        buySellStreamWindow.doQuery();
                    }
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc.getMessage());
                }
            }
        });
    }

}
