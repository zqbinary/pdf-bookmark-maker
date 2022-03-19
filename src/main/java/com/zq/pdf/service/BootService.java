package com.zq.pdf.service;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BootService {
    FileWriter fw = null;

    public String start(String path) {
        File f = new File(path);
        if (!f.exists() || !f.canExecute()) {
            return "文件不存在或无权限";
        }
        try {
            PdfReader reader = new PdfReader(path);
            List<HashMap<String, Object>> list = SimpleBookmark.getBookmark(reader);
            if (list == null || list.size() == 0) {
                return "没有找到书签/标题";
            }
            fw = new FileWriter(path.replace(".pdf", ".txt"));
            for (HashMap<String, Object> stringObjectHashMap : list) {
                showBookmark(stringObjectHashMap, 0);
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "失败：读取pdf失败";
        }
        return "成功：已经输出在文件中";
    }

    //获取标题
    private void showBookmark(HashMap<String, Object> bookmark, int level) throws IOException {
        String line = String.join("", Collections.nCopies(level, " "))
                + bookmark.get("Title")
                + "\t"
                + (getPageNumbers(bookmark) == 0 ? 0 : "");
        fw.write(line + System.getProperty("line.separator"));
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> kids = (ArrayList<HashMap<String, Object>>) bookmark.get("Kids");
        if (kids == null) {
            return;
        }
        ++level;
        for (HashMap<String, Object> kid : kids) {
            showBookmark(kid, level);
        }
    }

    public static int getPageNumbers(HashMap<String, Object> bookmark) {
        if (bookmark == null) {
            return 0;
        }

        if ("GoTo".equals(bookmark.get("Action"))) {
            String page = (String) bookmark.get("Page");
            if (page != null) {
                page = page.trim();
                int idx = page.indexOf(' ');
                int pageNum;
                if (idx < 0) {
                    pageNum = Integer.parseInt(page);
                } else {
                    pageNum = Integer.parseInt(page.substring(0, idx));
                }
                return pageNum;
            }
        }
        return 0;
    }
}
