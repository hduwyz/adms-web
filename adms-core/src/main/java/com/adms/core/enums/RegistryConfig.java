package com.adms.core.enums;

/**
 * @author wangyz
 * @date 2021年03月25日 16:14
 */
public class RegistryConfig {

    public static final int BEAT_TIMEOUT = 30;
    public static final int DEAD_TIMEOUT = BEAT_TIMEOUT * 3;

    public enum RegistType{ EXECUTOR, ADMIN }
}
