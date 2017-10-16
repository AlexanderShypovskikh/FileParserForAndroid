package com.parse.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IniLoader {
    private Map<String, LinkedList<String>> keyValuePairs = new HashMap();
    private LinkedList<Section> list = new LinkedList();

    public IniLoader() {}

    public void parse(InputStream is) {
        LinkedList<String> values = null;
        Section section = null;
        boolean fl = false;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line = null;
            br.readLine();

            while ((line = br.readLine()) != null) {
                int divider;

                if (line.startsWith("#") || line.equals("") || line.startsWith(";")  ) {
                    continue;
                }

                //    System.out.println("line = "+line);
                String sectionName = null;

                if(line.trim().startsWith("[") && line.trim().endsWith("]") ) {

                    if (fl & keyValuePairs.isEmpty()) {
                        section.setParams(null);
                        list.add(section);
                        //  System.out.println("added " +section.getSection());
                        fl= false;
                    }

                    if(!keyValuePairs.isEmpty()) {
                        section.setParams(keyValuePairs);
                        list.add(section);
                    }

                    sectionName = line.substring(1, line.length()-1);
                    section = new Section(sectionName);
                    //  System.out.println("Created new section :"+line);
                    fl = true;
                    keyValuePairs = new HashMap();
                    continue;
                }

                if(line.indexOf("=") <= 0){
                    throw new RuntimeException("Invalid key:value line format.");
                }else{
                    divider = line.indexOf("=");
                    //  System.out.println(x);
                }

                //       System.out.println("section = "+section.getSection());
                String key = line.substring(0, divider);

                int index = line.indexOf("#");
                if (index == -1)
                    index = line.indexOf(";");

                if(index > 0) {
                    line = line.substring(divider+1, index);
                } else
                    line = line.substring(divider+1).trim();
                values = new LinkedList();
                for(String elem : line.split(","))
                    values.add(elem);

                keyValuePairs.put(key, values);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fl) {
            section.setParams(keyValuePairs);
            list.add(section);
        }
        System.out.println(list.toString());
    }


    public Map<String, LinkedList<String>> getSectionParams(String section){

        Section item = getSection(section);
        if(item != null)
            return item.getParams();

        return null;
    }


    public LinkedList getParamValue (String section, String key) {
        Section item = getSection(section);
        if(item != null)
            return item.getParams().get(key);

        return null;
    }

    public Section getSection(String section) {
        for(Section item : list) {
            if(item.getSection().toLowerCase().equals(section.toLowerCase()))
                return item;
        }

        return null;
    }

}
