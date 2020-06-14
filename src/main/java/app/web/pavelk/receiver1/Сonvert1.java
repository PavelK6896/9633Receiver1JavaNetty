package app.web.pavelk.receiver1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Сonvert1 {
    public static void main(String[] args) throws IOException {

        String name = "file/javas.json";
        BufferedInputStream inBuf = new BufferedInputStream(new FileInputStream("file/javas.html"));
        BufferedOutputStream outBuf = new BufferedOutputStream(new FileOutputStream(name));
        outBuf.write("[[".getBytes());

        String template1 = "<h3>";
        String template2 = "</h3>";
        String template3 = "class=\"java\">";
        String template4 = "</code>";
        int c;
        ArrayList<Integer> list = new ArrayList<>();
        boolean start = true;
        boolean active = false;
        while ((c = inBuf.read()) != -1) {
            list.add(c);

            if (template1.length() <= list.size()) {
                if (compare(template1.getBytes(), list.subList(list.size() - template1.length(), list.size()))) {
                    if (start) {
                        start = false;
                    } else {
                        outBuf.write("],".getBytes());
                        outBuf.write("\n[]],[".getBytes());
                    }
                    outBuf.write("\n\"".getBytes());

                    byte skip = 1;
                    while ((c = inBuf.read()) != -1) {
                        list.add(c);
                        if (skip < template2.length()) {
                            skip++;
                            continue;
                        }
                        if (compare(template2.getBytes(), list.subList(list.size() - template2.length(), list.size()))) {
                            outBuf.write("\",\n[\n".getBytes());
                            break;
                        }
                        if (list.get(list.size() - template2.length()) == 34) {
                            outBuf.write("\\".getBytes());

                        }
                        if (list.get(list.size() - template2.length()) == 10) {
                            list.remove(list.size() - template2.length());
                            continue;
                        }
                        if (list.get(list.size() - template2.length()) == 13) {
                            list.remove(list.size() - template2.length());
                            outBuf.write("<br/>".getBytes());
                            continue;
                        }
//                        if (list.get(list.size() - template2.length()) == 32 &&
//                                list.get(list.size() - template2.length() - 1) == 32) {
//                            list.remove(list.size() - template2.length());
//                            continue;
//                        }
                        outBuf.write(list.get(list.size() - template2.length()));
                    }
                }


                if ("class=\"active\"".length() <= list.size() && compare("class=\"active\"".getBytes(),
                        list.subList(list.size() - "class=\"active\"".length(), list.size()))) {
                    outBuf.write("\"active ".getBytes());
                    active = true;
                }

                if (template3.length() <= list.size() && compare(template3.getBytes(), list.subList(list.size() - template3.length(), list.size()))) {
                   if (active){
                      active = false;
                   }else{
                       outBuf.write("\"".getBytes());
                   }


                    byte skip = 1;
                    while ((c = inBuf.read()) != -1) {
                        list.add(c);
                        if (skip < template4.length()) {
                            skip++;
                            continue;
                        }

                        if (compare(template4.getBytes(), list.subList(list.size() - template4.length(), list.size()))) {
                            outBuf.write("\",\n".getBytes());
                            break;
                        }
                        if (list.get(list.size() - template4.length()) == 34) {
                            outBuf.write("\\".getBytes());

                        }

                        if (list.get(list.size() - template4.length()) == 10) {
                            list.remove(list.size() - template4.length());
                            continue;
                        }
                        if (list.get(list.size() - template4.length()) == 13) {
                            list.remove(list.size() - template4.length());
                            outBuf.write("<br/>".getBytes());
                            continue;

                        }
//                        if (list.get(list.size() - template4.length()) == 32 &&
//                                list.get(list.size() - template4.length() - 1) == 32) {
//                            list.remove(list.size() - template4.length());
//                            continue;
//                        }
                        outBuf.write(list.get(list.size() - template4.length()));
                    }
                }
            }
        }
        outBuf.write("],[],]]".getBytes());
        inBuf.close();
        outBuf.close();
    }

    public static boolean compare(byte[] a, List<Integer> b) {
        for (int i = 0; i < b.size(); i++) {
            if (a[i] != b.get(i)) {
                return false;
            }
        }
        System.out.println("compare сравнить");
        return true;
    }
}
