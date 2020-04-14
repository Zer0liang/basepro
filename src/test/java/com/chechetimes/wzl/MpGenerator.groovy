package com.chechetimes.wzl

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.generator.AutoGenerator
import com.baomidou.mybatisplus.generator.config.DataSourceConfig
import com.baomidou.mybatisplus.generator.config.GlobalConfig
import com.baomidou.mybatisplus.generator.config.PackageConfig
import com.baomidou.mybatisplus.generator.config.StrategyConfig
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy

class MpGenerator {
    static String[] tables = [
            'table_type'
    ]

    static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator()

        // 全局配置
        GlobalConfig config = new GlobalConfig()
        config.setOutputDir("src\\main\\java")
                .setFileOverride(true)
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseResultMap(false)
                .setBaseColumnList(true)
                .setEnableCache(false)
                .setAuthor("mybatisplus")
                .setServiceName("%sService")

        mpg.setGlobalConfig(config)

        // 数据库配置
        DataSourceConfig dsc = new DataSourceConfig()
        dsc.setDbType(DbType.MYSQL)
        dsc.setDriverName("com.mysql.jdbc.Driver")
        dsc.setUsername("finance_2019")
        dsc.setPassword("Gh7wx4ue?whC")
        dsc.setUrl("jdbc:mysql://117.78.39.116:18635/finance_2019?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=CTT")
        mpg.setDataSource(dsc)

        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setInclude(tables)
                .setTablePrefix('das_')
        mpg.setStrategy(strategy)

        // 包配置
        PackageConfig pc = new PackageConfig()
        pc.setParent("com.chechetimes.wzl")
        pc.setEntity("entity")
        pc.setController("controller")
        pc.setService("service")
        pc.setMapper("mapper")
        pc.setServiceImpl("service.impl")
        mpg.setPackageInfo(pc)

        mpg.execute()
    }
}
