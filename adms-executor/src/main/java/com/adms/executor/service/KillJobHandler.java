package com.adms.executor.service;

import cn.hutool.core.io.FileUtil;
import com.adms.core.biz.model.ReturnT;
import com.adms.core.biz.model.TriggerParam;
import com.adms.core.handler.IJobHandler;
import com.adms.core.handler.annotation.JobHandler;
import com.adms.core.util.ProcessUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;

/**
 * 任务终止
 * @author wangyz
 * @date 2021年03月26日 8:44
 */
@JobHandler(value = "killJobHandler")
@Component
public class KillJobHandler extends IJobHandler {
    @Override
    public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
        String processId = tgParam.getProcessId();
        boolean result = ProcessUtil.killProcessByPid(processId);
        //  删除临时文件
        if (!CollectionUtils.isEmpty(jobTmpFiles)) {
            String pathname = jobTmpFiles.get(processId);
            if (pathname != null) {
                FileUtil.del(new File(pathname));
                jobTmpFiles.remove(processId);
            }
        }
        return result ? IJobHandler.SUCCESS : IJobHandler.FAIL;
    }
}
