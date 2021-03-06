package com.wt.blockchainivest.swing;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wt.blockchainivest.api.InvestApplicationI;
import com.wt.blockchainivest.domain.util.CommonUtil;
import com.wt.blockchainivest.domain.util.LogUtil;
import com.wt.blockchainivest.vo.CoinInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 信息录入页面
 *
 * @author wangtao
 */
@Component
public class CoinInfoWindow extends BaseWindow {
    private static final long serialVersionUID = -9191839187194468465L;
    private final JButton saveBtn = new JButton("保存");
    @Autowired
    private InvestApplicationI investApplicationImpl;
    @Autowired
    private BuySellStreamWindow buySellStreamWindow;
    private JFrame frame;
    private JLabel lbljson = new JLabel("使用JSON格式录入代币信息");
    private JButton formateBtn = new JButton("格式化");
    private JEditorPane cionInfoEP = new JEditorPane();
    private JScrollPane jsp = new JScrollPane(cionInfoEP);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new CoinInfoWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void addWindowlistener(Object... args) {
        formateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cionInfoEP.setText(CommonUtil.formatJson(cionInfoEP.getText()));
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Gson gson = new Gson();
                    List<CoinInfoVo> list = gson.fromJson(cionInfoEP.getText()
                            , new TypeToken<List<CoinInfoVo>>() {
                            }.getType());
                    investApplicationImpl.updateCoinInfos(list);
                    investApplicationImpl.updateAllSummary();

                    JOptionPane.showMessageDialog(null, "保存成功");
                    initDate();
                    buySellStreamWindow.doQuery();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "保存失败");
                    LogUtil.print("", exc);
                }
            }
        });
    }

    @Override
    public void initAndShow(Object... args) {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 689, 315);
        resetFrame(frame);

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
                .createSequentialGroup().addGap(22)
                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lbljson, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(groupLayout.createSequentialGroup().addComponent(formateBtn)
                                                .addPreferredGap(ComponentPlacement.RELATED).addComponent(saveBtn))
                                        .addComponent(jsp, GroupLayout.PREFERRED_SIZE, 647, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)))
                .addContainerGap(17, Short.MAX_VALUE)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(lbljson)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jsp, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                        .addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout
                                .createParallelGroup(Alignment.BASELINE).addComponent(saveBtn).addComponent(formateBtn))
                        .addGap(14)));
        frame.getContentPane().setLayout(groupLayout);

        initDate();
        addListener();
    }

    private void initDate() {
        // 货币信息
        List<CoinInfoVo> list = investApplicationImpl.queryCoinInfo(null);
        Gson gson = new Gson();
        String coinInfo = gson.toJson(list);
        cionInfoEP.setText(CommonUtil.formatJson(coinInfo));
    }
}
