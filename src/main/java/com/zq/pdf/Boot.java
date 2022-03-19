package com.zq.pdf;

import com.zq.pdf.service.SwingService;

public class Boot {
    public static void main(String[] args) {
        SwingService swingService = new SwingService("pdf书签生成器");
        swingService.start();
    }

}
