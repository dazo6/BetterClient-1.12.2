package com.dazo66.betterclient.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Dazo66
 */
public class LangFileUpdater {

    private LangEntryList[] list;

    public LangFileUpdater(File... files) {
        int length = files.length;
        list = new LangEntryList[length];
        for (int i = 0; i < length; i++) {
            list[i] = new LangEntryList(files[i]);
        }
        addShutDownHook();
    }

    public void put(String key, String value) {
        if (null == value) {
            value = "To be translated";
        }
        for (LangEntryList entryList : list) {
            entryList.put(key, value);
        }
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (LangEntryList entryList : list) {
                entryList.save();
            }
        }));
    }


}


class LangEntryList {

    private static final Pattern PATTERN0 = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
    private static final Pattern PATTERN1 = Pattern.compile("=");
    private File file;
    private TreeMap<String, String> map = new TreeMap<>();
    private List<Rule> rules = new ArrayList<>();

    public LangEntryList(File fileIn) {
        file = fileIn;
        map = serialization();
    }

    private static Queue<Character> toCharacterQueue(String string) {
        Queue<Character> queue = new LinkedList<>();
        for (Character char1 : string.toCharArray()) {
            queue.offer(char1);
        }
        return queue;
    }


    @SuppressWarnings("unchecked")
    private static String pollAWord(Queue<Character> queue) {
        Character character;
        LinkedList<Character> linkedList = (LinkedList) queue;
        StringBuilder builder = new StringBuilder();
        while (!linkedList.isEmpty()) {
            character = linkedList.poll();
            if (isAlphabet(character)) {
                builder.append(character);
            } else {
                linkedList.addFirst(character);
                break;
            }
        }
        return builder.toString();
    }

    private static boolean isAlphabet(char character) {
        return character >= 'A' && character <= 'z';
    }

    private static Character peekNotSpace(Queue<Character> queue) {
        char c;
        while (!queue.isEmpty()) {
            c = queue.peek();
            if (c == ' ') {
                queue.poll();
            } else {
                return c;
            }
        }
        return null;
    }

    private static String pollTimes(Queue<Character> queue, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(queue.poll());
        }
        return builder.toString();
    }

    private TreeMap<String, String> serialization() {
        List<String> list = new ArrayList<>();
        TreeMap<String, String> map = new TreeMap<>();
        try {
            list = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strings;
        for (String s : list) {
            if (null != s && !s.isEmpty()) {
                if (!s.startsWith("#")) {
                    strings = PATTERN1.split(s, 2);
                    if (strings.length == 2) {
                        if (strings[1].isEmpty()) {
                            strings[1] = "To be translated";
                        }
                        map.put(strings[0], strings[1]);
                    }
                    if (PATTERN0.matcher(strings[1]).find()) {
                        System.out.println(strings[1]);
                    }
                } else {
                    try {
                        rules.add(new Rule(s));
                    } catch (ParseException e) {
                        System.err.println(e.getMessage());
                    }

                }
            }
        }
        return map;

    }

    protected List<String> splitFile() {
        ArrayList<String> list = new ArrayList<>();
        if (file.isFile()) {
            try {
                LineNumberReader reader = new LineNumberReader(new FileReader(file));
                String s;
                while ((s = reader.readLine()) != null) {
                    list.add(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    boolean save() {
        TreeMap<String,String> reference = serialization();
        try {
            PrintWriter writer = new PrintWriter(file);
            String s;
            for (Rule rule : rules) {
                s = rule.toString();
                writer.println(s);
            }
            for (Map.Entry<String, String> entrySet : map.entrySet()) {
                String value = reference.get(entrySet.getKey());
                if(!entrySet.getValue().equals(value)) {
                    entrySet.setValue(value);
                }
                s = entrySet.getKey() + "=" + entrySet.getValue();
                writer.println(s);
            }
            IOUtils.closeQuietly(writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void put(String key, String value) {
        for (Rule rule : rules) {
            if (!rule.isAllow(key)) {
                return;
            }
        }
        map.put(key, value);
    }

    private static class Rule {

        private String rawRule;
        private boolean reverse = true;
        private String containString;

        Rule(String rule) {
            rawRule = rule;
            compile(rule);
        }

        void compile(String rule) {
            Character c;
            LinkedList<Character> queue = (LinkedList<Character>) toCharacterQueue(rule);
            if (rule.startsWith("#rule:")) {
                pollTimes(queue, 6);
                c = peekNotSpace(queue);
                if (c == '!') {
                    queue.poll();
                    c = peekNotSpace(queue);
                }else {
                    reverse = false;
                }
                if (isAlphabet(c)) {
                    String s = pollAWord(queue);
                    if ("contains".equals(s) || "contain".equals(s)) {
                        c = peekNotSpace(queue);
                        if (c == '(') {
                            queue.poll();
                            c = peekNotSpace(queue);
                            if (c == '"') {
                                queue.poll();
                                int i = queue.lastIndexOf('"');
                                if (i != -1) {
                                    containString = pollTimes(queue, i);
                                    return;
                                } else {
                                    throw new ParseException(rule);
                                }
                            } else if (c == '\'') {
                                queue.poll();
                                int i = queue.lastIndexOf('\'');
                                if (i != -1) {
                                    containString = pollTimes(queue, i);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            throw new ParseException(rule);
        }

        protected boolean isAllow(String s) {
            if (reverse) {
                return !s.contains(containString);
            } else {
                return s.contains(containString);
            }
        }

        @Override
        public String toString() {
            return String.format("#rule: %scontains(\"%s\");", reverse ? "!" : "", containString);
        }

    }
}