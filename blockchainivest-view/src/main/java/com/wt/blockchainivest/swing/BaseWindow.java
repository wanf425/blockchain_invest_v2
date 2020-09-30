package com.wt.blockchainivest.swing;

import org.springframework.stereotype.Component;

import javax.swing.JFrame;

public abstract class BaseWindow extends JFrame {

	protected void resetFrame(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
}
