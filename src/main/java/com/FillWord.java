package com;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright by 中旌影视 (c) 2018 Inc.
 * @描述
 * @Author Administrator
 * @创建时间 2018/9/13/013 17:50
 */
public class FillWord {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Map<String,Object> content = new HashMap<>();
        content.put("NAME","中旌影视");
        content.put("OPT1","选项");
        content.put("VALUE1","值66666");
        File file = new File("f:/demo/aa.doc");

                Writer out=null;
        try{
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));


            TemplateLoader templateLoader = null;
            templateLoader = new FileTemplateLoader(new File("f:/demo"));
            String tempname = "template.xml";

            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(templateLoader);
            Template t = cfg.getTemplate(tempname, "UTF-8");

            t.process(content, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {

        }



    }
}
