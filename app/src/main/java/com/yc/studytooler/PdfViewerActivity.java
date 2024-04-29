package com.yc.studytooler;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

/**
 * @ClassName PdfViewerActivity
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/24 15:45
 * @VERSION 1.0
 */
public class PdfViewerActivity extends AppCompatActivity {
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfView = findViewById(R.id.pdfView);
        String pdfPath = getIntent().getStringExtra("pdf_path");

        pdfView.fromFile(new File(pdfPath))
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pdfView != null) {
            pdfView.recycle(); // 清理PDFView加载的资源
        }
    }
}
