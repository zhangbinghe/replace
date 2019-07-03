package com.nv.replace;

import java.io.*;

public class Main {

    public static final String PATH = "D:\\hap";
    public static final String[] SUFFIXS = {/*".xml", ".java", ".html", ".groovy",".properties",".sql",".js",".view",".drl",*/".md"};
    public static final String[] REPLACE = {"jrap", "hap"};
    public static final boolean RECURSION = true; // 是否递归

    public static void main(String[] age) throws Exception {
        Main main = new Main();
        main.disposeFile(new File(PATH));
//        main.disposeFileName(new File(PATH));
//        main.disposeFolderName(new File(PATH));
    }

    /**
     * 替换文件夹名
     *
     * @param file
     * @throws Exception
     */
    public void disposeFolderName(File file) throws Exception {
        // 获取文件夹下面所有的文件（包含文件夹）
        String[] fileList = file.list();
        for (String fileName :
                fileList) {
//            System.out.println(fileName);
            File file1 = new File(file.getPath() + "\\" + fileName);
            if (file1.isDirectory()) {
                if (RECURSION) {
                    this.disposeFolderName(file1);
                }
                file1.renameTo(new File(file.getPath() + "\\" + fileName.replaceAll(REPLACE[0], REPLACE[1])));
            }
        }
    }


    /**
     * 替换文件名
     *
     * @param file
     * @throws Exception
     */
    public void disposeFileName(File file) throws Exception {
        // 获取文件夹下面所有的文件（包含文件夹）
        String[] fileList = file.list();
        for (String fileName :
                fileList) {
//            System.out.println(fileName);
            File file1 = new File(file.getPath() + "\\" + fileName);
            if (file1.isDirectory() && RECURSION) {
                this.disposeFileName(file1);
            } else {
                boolean accord = false;
                for (String suffix :
                        SUFFIXS) {
                    accord = fileName.indexOf(suffix) == -1 ? false : true;
                    if (accord)
                        break;
                }
                if (accord) {
                    file1.renameTo(new File(file.getPath() + "\\" + fileName.replaceAll(REPLACE[0], REPLACE[1])));
                }
            }
        }
    }

    /**
     * 替换文件内容
     *
     * @param file
     */
    public void disposeFile(File file) throws Exception {
        // 获取文件夹下面所有的文件（包含文件夹）
        String[] fileList = file.list();
        for (String fileName :
                fileList) {
//            System.out.println(fileName);
            File file1 = new File(file.getPath() + "\\" + fileName);
            if (file1.isDirectory() && RECURSION) {
                this.disposeFile(file1);
            } else {
                boolean accord = false;
                for (String suffix :
                        SUFFIXS) {
                    accord = fileName.indexOf(suffix) == -1 ? false : true;
                    if (accord)
                        break;
                }
                if (accord) {
                    BufferedReader reader = new BufferedReader(new FileReader(file1));
                    String tempString = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    int line = 1;
                    int i = 0;
                    //一次读一行，读入null时文件结束
                    while ((tempString = reader.readLine()) != null) {
                        tempString = tempString.replaceAll(REPLACE[0], REPLACE[1]);
                        if (i == 0) {
                            stringBuilder.append(tempString);
                        } else {
                            stringBuilder.append("\n" + tempString);
                        }
                        i++;
                        line++;
                    }
                    FileOutputStream fos = new FileOutputStream(file.getPath() + "\\" + fileName);
                    fos.write(stringBuilder.toString().getBytes());
                    fos.close();
                    reader.close();
                }
            }
        }
    }
}
