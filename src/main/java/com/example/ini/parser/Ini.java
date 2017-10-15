package com.example.ini.parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Ini {

    private static final Pattern PATTERN_SECTION = Pattern.compile("\\s*\\[([^]]*)\\]\\s*");
    private static final Pattern PATTERN_KEY_VALUE = Pattern.compile("\\s*([^=]*)=(.*)");

    private Map<String, Map<String, Object>> entries = new HashMap<>();

//    public com.example.ini.parser.Ini(String path) throws IOException {
//        load("c://tmp/application.ini");
//    }

    public void load(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            String section = null;
            while ((line = br.readLine()) != null) {

                Matcher matcher = PATTERN_SECTION.matcher(line);
                if (matcher.matches()) {
                    section = matcher.group(1).trim();
                } else if (section != null) {
                    matcher = PATTERN_KEY_VALUE.matcher(line);
                    if (matcher.matches()) {
                        String key = matcher.group(1).trim();
                        String value = matcher.group(2).trim();
                        Map<String, String> kv = entries.get(section);
                        if (kv == null) {
                            entries.put(section, kv = new HashMap<>());
                        }
                        kv.put(key, value);
                    }
                }
            }
        }
    }

    public String getString(String section, String key, String defaultvalue) {
        Map<String, String> kv = entries.get(section);
        if (kv == null) {
            return defaultvalue;
        }
        return kv.get(key);
    }

    public int getInt(String section, String key, int defaultvalue) {
        Map<String, String> kv = entries.get(section);
        if (kv == null) {
            return defaultvalue;
        }
        return Integer.parseInt(kv.get(key));
    }

    public float getFloat(String section, String key, float defaultvalue) {
        Map<String, String> kv = entries.get(section);
        if (kv == null) {
            return defaultvalue;
        }
        return Float.parseFloat(kv.get(key));
    }

    public double getDouble(String section, String key, double defaultvalue) {
        Map<String, String> kv = entries.get(section);
        if (kv == null) {
            return defaultvalue;
        }
        return Double.parseDouble(kv.get(key));
    }

    public void parse(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                int divider;

                // checking if line is a comment or empty
                if (line.startsWith("#")
                        || line.startsWith(";")
                        || line.trim().isEmpty()) {
                    continue;
                }

                // checking if line is a section
                // lazy initialize new section
                if(line.startsWith("[") && line.endsWith("]")) {
                    String section = line.substring(1, line.length() - 2);
                    entries.computeIfAbsent(section, k -> new HashMap<>());
                    continue;
                }

                //key=value
                if (line.contains("=")) {
                    divider = line.indexOf("=");
                    String key = line.substring(0, divider);
                    String value = line.substring(divider + 1, line.length() - 1);
                    // TODO: process key and value
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}