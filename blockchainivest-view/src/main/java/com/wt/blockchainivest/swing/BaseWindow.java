package com.wt.blockchainivest.swing;

import com.alibaba.cola.logger.Logger;
import com.alibaba.cola.logger.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

public abstract class BaseWindow extends JFrame {
    Logger log = LoggerFactory.getLogger(this.getClass());

    protected void resetFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public abstract void initAndShow(Object... args);

}
