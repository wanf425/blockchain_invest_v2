package com.wt.blockchainivest.swing;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;

import javax.swing.*;

public abstract class BaseWindow extends JFrame {
    Logger log = LoggerFactory.getLogger(this.getClass());
    protected Boolean isAddedListener = false;

    protected void resetFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    protected void addListener() {
        if(isAddedListener) {
            return;
        }

        addWindowlistener();

        isAddedListener = true;
    }

    protected abstract void addWindowlistener(Object... args);
    public abstract void initAndShow(Object... args);



}
