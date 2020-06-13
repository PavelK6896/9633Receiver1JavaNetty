package app.web.pavelk.receiver1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Сonvert1 {
    public static void main(String[] args) throws IOException {

        String name = "file/q1.json";
        BufferedInputStream inBuf = new BufferedInputStream(new FileInputStream("file/q1.html"));
        BufferedOutputStream outBuf = new BufferedOutputStream(new FileOutputStream(name));
        outBuf.write("[".getBytes());

        String template1 = "<h3><p>";
        String template2 = "</p></h3>";
        int c;
        ArrayList<Integer> list = new ArrayList<>();
        while ((c = inBuf.read()) != -1) {
            list.add(c);

            if (template1.length() <= list.size()) {
                if (compare(template1.getBytes(), list.subList(list.size() - template1.length(), list.size()))) {
                    outBuf.write("[\"".getBytes());
                    byte skip = 1;
                    while ((c = inBuf.read()) != -1) {
                        list.add(c);
                        if (skip < template2.length()) {
                            skip++;
                            continue;
                        }
                        if (compare(template2.getBytes(), list.subList(list.size() - template2.length(), list.size()))) {
                            outBuf.write("\"],\n".getBytes());
                            break;
                        }
                        if (list.get(list.size() - template2.length()) == 34) {
                            outBuf.write("\\".getBytes());
                        }
                        outBuf.write(list.get(list.size() - template2.length()));
                    }
                }
            }
        }
        outBuf.write("]".getBytes());
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
