package app.web.pavelk.receiver1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Сonvert2 {
    public static void main(String[] args) throws IOException {

        String name = "file/mysql.txt";
        BufferedInputStream inBuf = new BufferedInputStream(new FileInputStream("file/mysql.html"));
        BufferedOutputStream outBuf = new BufferedOutputStream(new FileOutputStream(name));

        String template1 = "<h3>";
        String template2 = "</h3>";
        String template3 = "";
        String template4 = "</code>";
        int c;
        int v = 0;
        ArrayList<Integer> list = new ArrayList<>();
        boolean active = false;
        while ((c = inBuf.read()) != -1) {
            list.add(c);

            if (template1.length() <= list.size()) {
                if (compare(template1.getBytes(), list.subList(list.size() - template1.length(), list.size()))) {
                    v++;
                    outBuf.write(("\n(" + v + ")").getBytes());

                    byte skip = 1;
                    while ((c = inBuf.read()) != -1) {
                        list.add(c);
                        if (skip < template2.length()) {
                            skip++;
                            continue;
                        }
                        if (compare(template2.getBytes(), list.subList(list.size() - template2.length(), list.size()))) {
                            outBuf.write("\n".getBytes());
                            break;
                        }

                        if (list.get(list.size() - template2.length()) == 10) {
                            list.remove(list.size() - template2.length());
                            continue;
                        }
                        if (list.get(list.size() - template2.length()) == 13) {
                            list.remove(list.size() - template2.length());
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

                }

                if (template3.length() <= list.size() && compare(template3.getBytes(), list.subList(list.size() - template3.length(), list.size()))) {



                    byte skip = 1;
                    while ((c = inBuf.read()) != -1) {
                        list.add(c);
                        if (skip < template4.length()) {
                            skip++;
                            continue;
                        }

                        if (compare(template4.getBytes(), list.subList(list.size() - template4.length(), list.size()))) {
                            outBuf.write("\n".getBytes());
                            break;
                        }


                        if (list.get(list.size() - template4.length()) == 10) {
                            list.remove(list.size() - template4.length());
                            continue;
                        }
                        if (list.get(list.size() - template4.length()) == 13) {
                            list.remove(list.size() - template4.length());
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
