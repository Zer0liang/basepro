package com.chechetimes.wzl;

import org.junit.Test;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * author:WangZhaoliang
 * Date:2020/5/5 16:35
 */
public class IoTest {

    @Test
    public void fileCopy() throws FileNotFoundException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = new FileInputStream("D:\\IOTEST\\2020Q1故事总结-0427.txt");
            inputStreamReader = new InputStreamReader(fileInputStream);
            File file = new File("D:\\IOTEST\\2020Q1故事总结-0427-copy.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");

            //读取数据
            char[] content = new char[1024];
            while (inputStreamReader.read(content) != -1) {
                outputStreamWriter.write(content);
                outputStreamWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
                inputStreamReader.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testNioCopy() {
        try {
            File file = new File("D:\\IOTEST\\2020Q1故事总结-0427-nioCopy.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileChannel in = new FileInputStream("D:\\IOTEST\\2020Q1故事总结-0427.txt").getChannel();
            FileChannel out = new FileOutputStream(file).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
