package com.wt.blockchainivest;

import com.wt.blockchain.asset.view.swing.BuySellStreamWindow;
import com.wt.blockchainivest.domain.gateway.CoinInfoGateway;
import com.wt.blockchainivest.repository.CoinInfoRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
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
        ".alibaba.cola","com.wt.blockchain.asset","com.wt.blockchainivest.repository.dao"})
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
