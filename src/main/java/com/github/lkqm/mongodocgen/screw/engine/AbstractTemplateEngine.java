package com.github.lkqm.mongodocgen.screw.engine;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public abstract class AbstractTemplateEngine implements TemplateEngine {

    private EngineConfig engineConfig;

    protected AbstractTemplateEngine(EngineConfig engineConfig) {
        checkNotNull(engineConfig, "EngineConfig can not be empty!");
        this.engineConfig = engineConfig;
    }

    /**
     * 获取文件，文件名格式为，数据库名_版本号.文件类型
     */
    protected File getFile(String docName) {
        File file;
        //如果没有填写输出路径，默认当前项目路径下的doc目录
        if (StringUtils.isBlank(getEngineConfig().getFileOutputDir())) {
            String dir = System.getProperty("user.dir");
            file = new File(dir + "/doc");
        } else {
            file = new File(getEngineConfig().getFileOutputDir());
        }
        //不存在创建
        if (!file.exists()) {
            //创建文件夹
            boolean mkdir = file.mkdirs();
        }
        //文件后缀
        String suffix = getEngineConfig().getFileType().getFileSuffix();
        file = new File(file, docName + suffix);
        //设置文件产生位置
        getEngineConfig().setFileOutputDir(file.getParent());
        return file;
    }

}
