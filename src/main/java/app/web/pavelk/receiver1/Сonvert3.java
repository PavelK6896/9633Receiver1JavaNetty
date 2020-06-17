package app.web.pavelk.receiver1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Сonvert3 {
    public static void main(String[] args) throws IOException {

        String name = "file/t1.json";
        BufferedInputStream inBuf = new BufferedInputStream(new FileInputStream("file/test1.txt"));
        BufferedOutputStream outBuf = new BufferedOutputStream(new FileOutputStream(name));

        String template1 = "///";
        String template2 = "***";
        String template3 = "+++";
        String template4 = "---";
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

                        outBuf.write(list.get(list.size() - template2.length()));
                    }
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
