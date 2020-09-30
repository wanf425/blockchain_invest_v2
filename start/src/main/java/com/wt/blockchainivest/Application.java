package com.wt.blockchainivest;

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

        // BuySellStreamWindow.init();

        // SpringApplication.run(Application.class, args);

        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        ApplicationContext applicationContext = builder.headless(false).run(args);

        BuySellStreamWindow window =
                applicationContext.getBean(BuySellStreamWindow.class);
        window.initAndShow();
    }
}
