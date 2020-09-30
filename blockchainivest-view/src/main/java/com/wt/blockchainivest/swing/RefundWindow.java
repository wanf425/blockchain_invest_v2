package com.wt.blockchainivest.swing;

import com.mysql.cj.util.StringUtils;
import com.wt.blockchainivest.domain.util.CommonUtil;
import com.wt.blockchainivest.domain.util.Constatns.ConstatnsKey;
import com.wt.blockchainivest.domain.util.NumberUtil;
import com.wt.blockchainivest.repository.dao.CoinDetailDao;
import com.wt.blockchainivest.repository.dao.CoinSummaryDao;
import com.wt.blockchainivest.repository.dao.ConstantsDao;
import com.wt.blockchainivest.repository.dto.CoinSummaryDto;
import com.wt.blockchainivest.repository.dto.ConstantsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * 补差额页面
 *
 * @author wangtao
 */
@Component
public class RefundWindow extends BaseWindow {

    private static final long serialVersionUID = -91315274629841966L;
    private final JLabel remarkLA = new JLabel("备注：");
    private final JTextField remarkTF = new JTextField();
    @Autowired
    private ConstantsDao constantsDao;
    @Autowired
    private CoinSummaryDao coinSummaryDao;
    @Autowired
    private CoinDetailDao coinDetailDao;
    private JFrame frame;
    // 预期数量文本框
    private JTextField expectCoinNumTF;
    private JComboBox<ConstantsDto> coinNameCB = new JComboBox<ConstantsDto>();
    // 保存按钮
    private JButton saveBtn = new JButton("保存");
    // 当前数量
    private JLabel currentCoinNumValue = new JLabel("");
    // 预期数量
    private JLabel refundNumValue = new JLabel("");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new RefundWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initAndShow() {
        initialize();
        refresh();
    }

    private void refresh() {
        frame.setVisible(true);

        ConstantsDto c = (ConstantsDto) coinNameCB.getSelectedItem();
        List<CoinSummaryDto> csList = coinSummaryDao.query(c.getKey());

        CoinSummaryDto coinSummaryDto = null;
        if (csList != null && csList.size() > 0) {
            coinSummaryDto = csList.get(0);
        }
        currentCoinNumValue.setText(coinSummaryDto.getCoin_num().toString());
        expectCoinNumTF.setText("");
        refundNumValue.setText("");
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        remarkTF.setColumns(10);
        frame = new JFrame();
        frame.setBounds(100, 100, 213, 228);
        resetFrame(frame);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel coinNameLA = new JLabel("币种：");
        GridBagConstraints gbc_coinNameLA = new GridBagConstraints();
        gbc_coinNameLA.anchor = GridBagConstraints.EAST;
        gbc_coinNameLA.insets = new Insets(0, 0, 5, 5);
        gbc_coinNameLA.gridx = 1;
        gbc_coinNameLA.gridy = 1;
        frame.getContentPane().add(coinNameLA, gbc_coinNameLA);

        GridBagConstraints gbc_coinNameCB = new GridBagConstraints();
        gbc_coinNameCB.gridwidth = 2;
        gbc_coinNameCB.insets = new Insets(0, 0, 5, 0);
        gbc_coinNameCB.fill = GridBagConstraints.HORIZONTAL;
        gbc_coinNameCB.gridx = 2;
        gbc_coinNameCB.gridy = 1;
        frame.getContentPane().add(coinNameCB, gbc_coinNameCB);

        JLabel currentCoinNumLA = new JLabel("当前数量：");
        GridBagConstraints gbc_currentCoinNumLA = new GridBagConstraints();
        gbc_currentCoinNumLA.anchor = GridBagConstraints.EAST;
        gbc_currentCoinNumLA.insets = new Insets(0, 0, 5, 5);
        gbc_currentCoinNumLA.gridx = 1;
        gbc_currentCoinNumLA.gridy = 2;
        frame.getContentPane().add(currentCoinNumLA, gbc_currentCoinNumLA);

        GridBagConstraints gbc_currentCoinNumValue = new GridBagConstraints();
        gbc_currentCoinNumValue.gridwidth = 2;
        gbc_currentCoinNumValue.anchor = GridBagConstraints.WEST;
        gbc_currentCoinNumValue.insets = new Insets(0, 0, 5, 0);
        gbc_currentCoinNumValue.gridx = 2;
        gbc_currentCoinNumValue.gridy = 2;
        frame.getContentPane().add(currentCoinNumValue, gbc_currentCoinNumValue);

        JLabel expectCoinNumLA = new JLabel("预期数量：");
        GridBagConstraints gbc_expectCoinNumLA = new GridBagConstraints();
        gbc_expectCoinNumLA.anchor = GridBagConstraints.EAST;
        gbc_expectCoinNumLA.insets = new Insets(0, 0, 5, 5);
        gbc_expectCoinNumLA.gridx = 1;
        gbc_expectCoinNumLA.gridy = 3;
        frame.getContentPane().add(expectCoinNumLA, gbc_expectCoinNumLA);

        expectCoinNumTF = new JTextField();
        GridBagConstraints gbc_expectCoinNumTF = new GridBagConstraints();
        gbc_expectCoinNumTF.gridwidth = 2;
        gbc_expectCoinNumTF.anchor = GridBagConstraints.WEST;
        gbc_expectCoinNumTF.insets = new Insets(0, 0, 5, 0);
        gbc_expectCoinNumTF.gridx = 2;
        gbc_expectCoinNumTF.gridy = 3;
        frame.getContentPane().add(expectCoinNumTF, gbc_expectCoinNumTF);
        expectCoinNumTF.setColumns(10);

        JLabel refundNumLA = new JLabel("差额：");
        GridBagConstraints gbc_refundNumLA = new GridBagConstraints();
        gbc_refundNumLA.anchor = GridBagConstraints.EAST;
        gbc_refundNumLA.insets = new Insets(0, 0, 5, 5);
        gbc_refundNumLA.gridx = 1;
        gbc_refundNumLA.gridy = 4;
        frame.getContentPane().add(refundNumLA, gbc_refundNumLA);

        GridBagConstraints gbc_refundNumValue = new GridBagConstraints();
        gbc_refundNumValue.gridwidth = 2;
        gbc_refundNumValue.anchor = GridBagConstraints.WEST;
        gbc_refundNumValue.insets = new Insets(0, 0, 5, 0);
        gbc_refundNumValue.gridx = 2;
        gbc_refundNumValue.gridy = 4;
        frame.getContentPane().add(refundNumValue, gbc_refundNumValue);

        GridBagConstraints gbc_remarkLA = new GridBagConstraints();
        gbc_remarkLA.anchor = GridBagConstraints.EAST;
        gbc_remarkLA.insets = new Insets(0, 0, 5, 5);
        gbc_remarkLA.gridx = 1;
        gbc_remarkLA.gridy = 5;
        frame.getContentPane().add(remarkLA, gbc_remarkLA);

        GridBagConstraints gbc_remarkTF = new GridBagConstraints();
        gbc_remarkTF.anchor = GridBagConstraints.WEST;
        gbc_remarkTF.gridwidth = 2;
        gbc_remarkTF.insets = new Insets(0, 0, 5, 5);
        gbc_remarkTF.gridx = 2;
        gbc_remarkTF.gridy = 5;
        frame.getContentPane().add(remarkTF, gbc_remarkTF);

        GridBagConstraints gbc_saveBtn = new GridBagConstraints();
        gbc_saveBtn.gridx = 3;
        gbc_saveBtn.gridy = 6;
        frame.getContentPane().add(saveBtn, gbc_saveBtn);

        initDate();
        addListener();
    }

    private void initDate() {
        // 币种 下拉框
        List<ConstantsDto> coinNames =
                constantsDao.queryByType(ConstatnsKey.COIN_NAME);
        CommonUtil.initialComboBox(coinNames, coinNameCB, c -> c.getValue());
    }

    /**
     * 条件监听事件
     */
    private void addListener() {
        coinNameCB.addItemListener(t -> {
            ConstantsDto c = (ConstantsDto) t.getItem();
            List<CoinSummaryDto> csList = coinSummaryDao.query(c.getKey());

            if (csList != null && csList.size() > 0) {
                currentCoinNumValue.setText(csList.get(0).getCoin_num().toString());
            }

            Double refund = calRefund();
            refundNumValue.setText(refund == null ? "" : refund.toString());
        });

        expectCoinNumTF.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Double refund = calRefund();
                refundNumValue.setText(refund == null ? "" : refund.toString());
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });

        // 保存
        saveBtn.addActionListener(e -> {
            try {
                // 必填校验
                if (StringUtils.isNullOrEmpty(refundNumValue.getText())) {
                    throw new Exception("缺少必填项");
                }

                ConstantsDto c = (ConstantsDto) coinNameCB.getSelectedItem();
                coinDetailDao.doRefund(c.getKey(), Double.valueOf(refundNumValue.getText()), remarkTF.getText());
                JOptionPane.showMessageDialog(null, "保存成功！");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    /**
     * 计算差额
     *
     * @return
     */
    private Double calRefund() {
        String currentCoinNum = currentCoinNumValue.getText();
        String expectCoinNum = expectCoinNumTF.getText();
        return NumberUtil.sub(expectCoinNum, currentCoinNum, null);
    }

}
