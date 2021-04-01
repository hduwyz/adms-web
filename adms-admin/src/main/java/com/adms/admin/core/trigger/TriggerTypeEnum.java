package com.adms.admin.core.trigger;

public enum TriggerTypeEnum {
    MANUAL("jobconf_trigger_type_manual"),
    CRON("jobconf_trigger_type_cron"),
    RETRY("jobconf_trigger_type_retry"),
    PARENT("jobconf_trigger_type_parent"),
    API("jobconf_trigger_type_api");

    private TriggerTypeEnum(String title){
        this.title = title;
    }
    private String title;
    public String getTitle() {
        return title;
    }
}
