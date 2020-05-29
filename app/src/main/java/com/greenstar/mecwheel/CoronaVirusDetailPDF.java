package com.greenstar.mecwheel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class CoronaVirusDetailPDF extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener, OnLoadCompleteListener {

    public String SAMPLE_FILE = "";
    PDFView pdfView=null;
    String pdfFileName="";
    Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sign_and_symptoms);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");

        if(title.equals("No-Panic Help Guide")){
            SAMPLE_FILE = "corona_book.pdf";
        }else if(title.equals("Home Quarantine - Guidelines (Urdu)")){
            SAMPLE_FILE = "guidelinesfor_home_quarentineand_disinfection.pdf";
        }else if(title.equals("Home Quarantine - Guide (English)")){
            SAMPLE_FILE = "household_isolationfor_coronavirus.pdf";
        }else if(title.equals("How to save yourself from Covid-19")){
            SAMPLE_FILE = "how_to_save.pdf";
        }else if(title.equals("Collaborating to Fight Corona Virus")){
            SAMPLE_FILE = "fight_corona.pdf";
        }else if(title.equals("PAC Covid-19 Guidance Royal College")){
            SAMPLE_FILE = "pac_covid19_guidance_royal_college.pdf";
        }else if(title.equals("Corona and Feeding Mother")){
            SAMPLE_FILE = "gsm_corona_bf.pdf";
        }else if(title.equals("Covid-19 and pregnancy")){
            SAMPLE_FILE = "covidsogp.pdf";
        }

        View view = null;

        view = findViewById(R.id.sign_and_symptoms_layout);
        pdfView= (PDFView)view.findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_nav_icon));
        toolbar.setNavigationOnClickListener(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:080011171"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
