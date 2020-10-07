package com.wt.blockchainivest.swing;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.wt.blockchainivest.api.InvestApplicationI;
import com.wt.blockchainivest.repository.dao.EarningDao;
import com.wt.blockchainivest.repository.dto.EarningDto;
import com.wt.blockchainivest.domain.util.NumberUtil;
import com.wt.blockchainivest.vo.EarningVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 资产统计页面
 *
 * @author wangtao
 */
@Component
public class EarningWindow extends BaseWindow {

	private static final long serialVersionUID = 4451854259633603697L;

	@Autowired
	private EarningDao earningDao;
	@Autowired
	private InvestApplicationI investApplicationImpl;

	private JFrame frame;
	private JTable table;
	private List<EarningVo> earningList = null;
	private JButton button = new JButton("结算");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new EarningWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
    public void initAndShow(Object... args) {
		initialize();
		refresh();
	}

	public void refresh() {
		this.frame.setVisible(true);
		earningList = investApplicationImpl.query();
		table.updateUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 510, 300);
		resetFrame(frame);

		table = new JTable();
		TableModel dataModel = getTableModel();
		table = new JTable(dataModel);
		// 添加排序
		table.setRowSorter(new TableRowSorter<TableModel>(dataModel));
		JScrollPane jsp = new JScrollPane(table);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(jsp,
								GroupLayout.PREFERRED_SIZE, 483, GroupLayout.PREFERRED_SIZE))
						.addComponent(button))
				.addContainerGap(15, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(button)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jsp, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(180, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);

		initDate();
		addListener();
	}

	private void initDate() {
		table.updateUI();
	}

	private void addListener() {
		button.addActionListener(t -> {
			boolean result = earningDao.calEarning();
			JOptionPane.showMessageDialog(null, result ? "操作成功！" : "操作失败！");
			refresh();
		});
	}

	@SuppressWarnings("unchecked")
	public AbstractTableModel getTableModel() {
		return new AbstractTableModel() {
			String[] names = { "结算日期", "总投入", "当期投入", "总市值", "增长率(去当期)" };

			private static final long serialVersionUID = 4354562018087682852L;

			@Override
			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}

			@Override
			public int getColumnCount() {
				return names.length;
			}
			@Override
			public int getRowCount() {
				return getData().size();
			}
			@Override
			public String getColumnName(int column) {
				return names[column];
			}
			@Override
			public Object getValueAt(int row, int col) {
				switch (col) {
				case (0): {
					return getData().get(row).getSettlement_date();
				}
				case (1): {
					return NumberUtil.formateNum(getData().get(row).getTotal_invest());
				}
				case (2): {
					return NumberUtil.formateNum(getData().get(row).getCurrent_invest());
				}
				case (3): {
					return NumberUtil.formateNum(getData().get(row).getTotal_value());
				}
				case (4): {
					return NumberUtil.formateNum(getData().get(row).getIncrease_rate() * 100) + "%";
				}
				default:
					return "";
				}
			}
		};
	}

	public List<EarningVo> getData() {
		if (earningList == null) {
			earningList = investApplicationImpl.query();
		}

		return earningList;
	}
}
