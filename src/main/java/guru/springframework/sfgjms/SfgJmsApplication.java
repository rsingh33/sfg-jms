package guru.springframework.sfgjms;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.impl.ActiveMQServerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class SfgJmsApplication {

    public static void main(String[] args) throws Exception {

        ActiveMQServer server = new ActiveMQServerImpl(new ConfigurationImpl()
        .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
        .addAcceptorConfiguration("invm","vm://0"));

        server.start();
        SpringApplication.run(SfgJmsApplication.class, args);
    }

}
