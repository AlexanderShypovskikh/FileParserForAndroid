package com.example.ini.parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Properties {

    private Map<String, String> keyValuePairs = new HashMap<>();

    public void parse(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                int divider;

                // check if the line is a comment
                if (line.startsWith("#")
                        || line.startsWith("!")
                        || line.trim().isEmpty()) {
                    continue;
                }
                //key:value
                if (!line.contains(":") && !line.contains("=")) {
                    throw new RuntimeException("Invalid key:value line format.");
                } else {
                    divider = line.indexOf(":");
                    if (divider < 0) divider = line.indexOf("=");
                }

                String key = line.substring(0, divider);

                // check if a line is multiline
                StringBuilder buffer = new StringBuilder();

                if (line.endsWith("\\")) {
                    buffer.append(line.substring(divider + 1, line.length() - 1));
                    while ((line = br.readLine()) != null) {
                        if (!line.endsWith("\\")) {
                            buffer.append(line);
                            break;
                        } else {
                            buffer.append(line.substring(0, line.length()));
                        }
                    }
                } else {
                    buffer.append(line.substring(divider + 1, line.length()));
                }
                // delete tab
                String value = buffer.toString().replace("\t", "");
                //
                keyValuePairs.put(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


//    public void parse(String filePath) {
//        parse(new File(filePath));
//    }

    public Object get(String key) {
        return keyValuePairs.get(key);
    }

    public String getString(String key) {
        return keyValuePairs.get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(keyValuePairs.get(key));
    }

    public static void main(String[] args) {
        String filePath = args[0];
        Properties properties = new Properties();
        try {
            properties.parse(new FileInputStream(filePath));
            System.out.println("Parse results");
            properties.keyValuePairs.forEach(
                    (key, value) -> System.out.println("key: " + key + " ; value: " + value));
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file path provided");
        }
    }
}
