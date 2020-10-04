package com.wt.blockchainivest.swing;


import com.wt.blockchainivest.api.BlockchainInvestApplicationI;
import com.wt.blockchainivest.domain.util.CommonUtil;
import com.wt.blockchainivest.domain.util.Constatns;
import com.wt.blockchainivest.domain.util.Constatns.ConstatnsKey;
import com.wt.blockchainivest.domain.util.NumberUtil;
import com.wt.blockchainivest.repository.dao.BackupDao;
import com.wt.blockchainivest.repository.dao.CoinDetailDao;
import com.wt.blockchainivest.repository.dto.CoinDetailDto;
import com.wt.blockchainivest.vo.ConstantsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.util.List;

/**
 * 数据备份页面
 *
 * @author wangtao
 */
@Component
public class BackupWindow extends BaseWindow {

    private final JEditorPane detailLogPane = new JEditorPane();
    @Autowired
    private BlockchainInvestApplicationI blockchainInvestApplicationImpl;
    @Autowired
    private CoinDetailDao coinDetailDao;
    @Autowired
    private BackupDao backupDao;
    private JFrame frame;
    private JButton backupBtn = new JButton("备份");
    private JButton rollbackBtn = new JButton("回滚");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new BackupWindow();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initAndShow(Object... args) {
        initialize();
        initDate();
        this.frame.setVisible(true);
    }

    public void refresh() {
        initDate();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 308);
        resetFrame(frame);
        JScrollPane jsp = new JScrollPane(detailLogPane);

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(jsp))
                                .addGroup(groupLayout.createSequentialGroup().addComponent(backupBtn)
                                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(rollbackBtn)))
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(backupBtn)
                                .addComponent(rollbackBtn))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jsp, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE)));
        frame.getContentPane().setLayout(groupLayout);

        initDate();
        addlistener();
    }

    private void addlistener() {
        // 备份
        backupBtn.addActionListener(t -> {
            boolean result = backupDao.doBackUp();
            JOptionPane.showMessageDialog(null, result ? "备份成功！" : "备份失败！");
            refresh();
        });

        // 回滚
        rollbackBtn.addActionListener(t -> {
            boolean result = backupDao.doRollBack();
            JOptionPane.showMessageDialog(null, result ? "回滚成功！" : "回滚失败！");
            refresh();
        });
    }

    public void initDate() {
        ConstantsVo maxid =
                blockchainInvestApplicationImpl.queryByType(ConstatnsKey.MAX_DETAIL_ID).get(0);

        List<CoinDetailDto> detailList = coinDetailDao.queryById(Integer.valueOf(maxid.getValue()));

        StringBuffer sb = new StringBuffer("");
        for (CoinDetailDto detail : detailList) {
            sb.append("[" + CommonUtil.formateDate(detail.getCreate_Date()) + "]  ");
            sb.append("币种：" + detail.getCoin_name());

            if (Constatns.OpType.buy.equals(detail.getOp_type())) {
                sb.append(" 买入，数量：" + NumberUtil.formateNum(detail.getCoin_num(), "#0.0000"));
            } else {
                sb.append(" 卖出，数量：" + NumberUtil.formateNum(detail.getCoin_num(), "#0.0000"));
            }

            sb.append(" 总花费：" + NumberUtil.formateNum(detail.getTotal_cost(), "#0.0000")).append(" ")
                    .append(detail.getMonetary_unit());
            sb.append("\n");
        }

        detailLogPane.setText(sb.toString());
    }
}
