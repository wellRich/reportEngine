package cn.guoyka.simplermybatis.dao

import cn.guoyka.simplermybatis.annotation.Column
import cn.guoyka.simplermybatis.annotation.Table
import groovy.xml.StreamingMarkupBuilder

import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * 生成xml/java文件的脚本，使用org.junit.Test运行 ，帮助我们偷懒的groovy脚本
 * 这里是生成了一个mapper.xml，只生成了resultMap，可以帮助我们的少写一点注解，少犯错
 * @author guoyka
 * @version 1.0 , 2018/4/30
 */
class MethodScript{

    static pr(def obj) {
        println obj
    }

    @org.junit.Test
    void buildMapperXml() {
        StreamingMarkupBuilder builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = "UTF-8"

        /*
        准备一些输入
         */

        // 项目包名
        String packName = "cn.guoyka.simplermybatis.entity"

        // 实体类的包绝对路径
        File entityFilePath = new File("E:\\Kitty\\workspace\\simpler-mybatis\\src\\main\\java\\cn\\guoyka\\simplermybatis\\entity")


        // 保存生成的Mapper.xml的路径
        String resultPath = "E:\\Kitty\\workspace\\simpler-mybatis\\src\\main\\resources\\mybatis\\mapper\\Mapper.xml"

        String root = "E:\\Kitty\\workspace\\simpler-mybatis\\src\\main\\java\\"

        List<String> entityNames = []
        peekFile(entityFilePath, root, entityNames)
        final def d = ('<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">');
        def mapper = {
            mkp.xmlDeclaration()
            mkp.yieldUnescaped(d)
            mapper(namespace: packName) {
                for (String className : entityNames) {
                    try {
                        if (className != null || className != "") {
                            Class clazz = Class.forName(className)
                            if (clazz.isAnnotationPresent(Table.class)) {
                                Field[] fields = clazz.getDeclaredFields()
                                String primaryKey = clazz.getAnnotation(Table.class).primaryKey()
                                String type = (clazz.getPackage().name + "." + clazz.simpleName)
                                resultMap(id: "allOf" + className.substring(className.lastIndexOf(".") + 1), type: type) {
                                    for (int i = 0; i < fields.length; i++) {
                                        if (Modifier.isStatic(fields[i].getModifiers())) {
                                            pr(fields[i].getName() + "是静态属性，过滤过滤.")
                                        } else {
                                            if (fields[i].getName() == primaryKey) {
                                                id(property: (fields[i].name), javaType: fields[i].getType().simpleName, column: getCol(fields[i]))
                                            } else {
                                                result(property: (fields[i].name), javaType: fields[i].getType().simpleName, column: getCol(fields[i]))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        println("${className} ---> " + ex.getMessage())
                    }
                }

            }
        }
        def writer = new FileWriter(resultPath)
        writer << builder.bind(mapper)
    }

    /**
     * 获取目录下的java类名及完整包名，如 cn.guoyka.simplermybatis.entity.ZCCRequest
     * @param file 实体类的包绝对路径
     * @param root 项目中java文件夹的路径
     * @param fileNames 若干实体类名称
     * @return
     */
    def peekFile(File file, String root, List<String> fileNames) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File e : file.listFiles()) {
                    if (e.isDirectory()) {
                        peekFile(e, root, fileNames)
                    } else {
                        if (e.getName().endsWith(".java")) {
                            fileNames.add(e.getPath()
                                    .replace(root, "")
                                    .replace("\\", ".").replace(".java", ""))
                        }
                    }
                }
                return fileNames
            } else {
                if (file.getName().endsWith(".java")) {
                    fileNames.add(file.getPath()
                            .replace("E:\\Kitty\\workspace\\simpler-mybatis\\src\\main\\java\\", "")
                            .replace("\\", ".").replace(".java", ""))
                }
                return fileNames
            }
        } else {
            return new RuntimeException("目录或者文件不存在！");
        }
    }

    /**
     * 获取字段上的Column注解
     * @param field
     * @return
     */
    static def getCol(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name()
        } else {
            throw new RuntimeException(field.name + "未有添加注解cn.guoyka.simplermybatis.annotation.Column，请添加！")
        }
    }
}
