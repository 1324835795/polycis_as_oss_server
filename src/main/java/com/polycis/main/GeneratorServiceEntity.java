package com.polycis.main;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author 崔文昊
 * @date 2019/4/18
 */
public class GeneratorServiceEntity {

    @Test
    public void generateCode() {

        String packageName = "com.polycis.main";
        boolean serviceNameStartWithI = true;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "iot_oss_admin");
    }


//"lo_network_server","lo_network_system","lo_network_config","lo_network_config_info"
    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://192.168.10.156:3306/polycis_as_main";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(dbUrl);
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true); ///全局大写命名
        strategyConfig.setEntityLombokModel(false); // 【实体】是否为lombok模型（默认 false）
        strategyConfig.setDbColumnUnderline(true); //数据库表字段采用下划线的
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategyConfig.setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        strategyConfig.setTablePrefix(new String[]{"iot_", ""});// 此处可以修改为您的表前缀
       // strategyConfig.entityTableFieldAnnotationEnable(true);//生成实体类属性的对应数据库字段注解

        config.setActiveRecord(false);
        //D:\LORA_DEVELOP\lorawan2\lorawan-service\src\main\java
        config.setOutputDir("D:\\LORA_DEVELOP\\polycis_iot_platform\\polycis_as_oss_server\\src\\main\\java\\com\\polycis\\main");
        //\LoRaWanService\lorawan-service\src\main\java
        config.setFileOverride(true);

        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }


        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                                .setMapper("dao")
                )
                .setCfg(
                        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                        new InjectionConfig() {
                            @Override
                            public void initMap() {
                                Map<String, Object> map = new HashMap<>();
                                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                                this.setMap(map);
                            }
                        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                            // 自定义输出文件目录
                            @Override
                            public String outputFile(TableInfo tableInfo) {
                                //D:/LORA_DEVELOP/lorawan2/lorawan-service/"+"/src/main/resources/mybatis/gateway/
                                return "D:\\LORA_DEVELOP\\polycis_iot_platform\\polycis_as_oss_server"+"/src/main/resources/mybatis/data/" + tableInfo.getEntityName() + "Mapper.xml";
                        }
                        }))

                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }
}
