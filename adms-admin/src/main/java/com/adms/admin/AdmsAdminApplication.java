package com.adms.admin;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wangyz
 * @date 2021年04月01日 8:52
 */

@Slf4j
@SpringBootApplication
public class AdmsAdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment env = new SpringApplication(AdmsAdminApplication.class).run(args).getEnvironment();
        String envPort = env.getProperty("server.port");
        String envContext = env.getProperty("server.contextPath");
        String port = envPort == null ? "8080" : envPort;
        String context = envContext == null ? "" : envContext;
        String path = port + "" + context + "/doc.html";
        String externalAPI = InetAddress.getLocalHost().getHostAddress();
        log.info(
                "Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local-API: \t\thttp://127.0.0.1:{}\n\t"
                        + "External-API: \thttp://{}:{}\n\t"
                        + "web-URL: \t\thttp://127.0.0.1:{}/index.html\n\t----------------------------------------------------------",
                path, externalAPI, path, port);
    }
}
