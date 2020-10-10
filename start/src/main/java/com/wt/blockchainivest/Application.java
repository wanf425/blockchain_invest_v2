package com.wt.blockchainivest;

import com.wt.blockchainivest.swing.BackupWindow;
import com.wt.blockchainivest.swing.BaseWindow;
import com.wt.blockchainivest.swing.BuySellStreamWindow;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Spring Boot Starter
 * <p>
 * COLA framework initialization is configured in {@link com.wt.blockchainivest.config.ColaConfig}
 *
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"com.wt.blockchainivest", "com" +
        ".alibaba.cola"})
@MapperScan("com.wt.blockchainivest.repository")
public class Application {

    public static void main(String[] args) {
        run(args);
    }

    private static void run(String[] args) {
        run(args, BuySellStreamWindow.class);
    }

    private static void run(String[] args, Class clazz) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);

        for (String s : args) {
            System.out.println(s);
        }
        ApplicationContext applicationContext = builder.headless(false).run(args);

        BaseWindow window =
                (BaseWindow) applicationContext.getBean(clazz);
        window.initAndShow(args);
    }

}
