package id.aditya.andropulsa.kelas;

import id.aditya.andropulsa.LaporanActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

public class ClassLaporan extends AsyncTask<String, Void, Integer> {
	
	ProgressDialog progressDialog;
	Context contx;
	PdfPTable tabel = new PdfPTable(5);
	String awal, akhir, date;
	int jumlah, total;
	
	public ClassLaporan(LaporanActivity laporanActivity, PdfPTable pdfPTable, String start, String end, int jml, int total, String date_now){
		this.contx = laporanActivity;
		this.tabel = pdfPTable;
		this.awal = start;
		this.akhir = end;
		this.jumlah = jml;
		this.total = total;
		this.date = date_now;
	}
	
	@Override
    protected void onPreExecute() {
		progressDialog = ProgressDialog.show(contx, "Mohon Tunggu",  "Exporting PDF ...", true);
    }

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
			Document document = new Document();
			String posisi = Environment.getExternalStorageState();
			File exportDir;
			if(!Environment.MEDIA_MOUNTED.equals(posisi)){
				return null;
			} else {
				exportDir = Environment.getExternalStorageDirectory();
			}
			
			if(!exportDir.exists()){
				exportDir.mkdirs();
			}
			
			File file = new File(exportDir,"Laporan_" + date + ".pdf");
			PdfWriter.getInstance(document, new FileOutputStream(file));
			
			document.open();
			
			Font font1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
			Paragraph p1 = new Paragraph("Laporan Penjualan Pulsa");
			Paragraph p2 = new Paragraph("Periode : "+awal+" - "+akhir);
			Paragraph p3 = new Paragraph("Jumlah Penjualan : "+jumlah);
			Paragraph p4 = new Paragraph("Total Pendapatan : Rp."+total);
			p1.setAlignment(Element.ALIGN_CENTER);
			p1.setSpacingAfter(30);
			p1.setFont(font1);
			document.add(p1);
			
			p2.setIndentationLeft(60);
			//p2.setAlignment(Element.ALIGN_LEFT);
			p2.setSpacingAfter(20);
			p2.setFont(font2);
			
			p3.setIndentationLeft(60);
			p3.setSpacingAfter(20);
			p3.setFont(font2);
			
			p4.setIndentationRight(60);
			p4.setSpacingBefore(5);
			p4.setAlignment(Element.ALIGN_RIGHT);
			p4.setFont(font2);
			
			PdfPTable tabel_header = new PdfPTable(2);
			PdfPCell cell1 = new PdfPCell(p2);
			cell1.setBorder(Rectangle.NO_BORDER);
			PdfPCell cell2 = new PdfPCell(p3);
			cell2.setBorder(Rectangle.NO_BORDER);
			tabel_header.addCell(cell1);
			tabel_header.addCell(cell2);
			tabel_header.setSpacingAfter(10f);
			tabel_header.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			document.add(tabel_header);
			document.add(tabel);
			document.add(p4);
			document.close();
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
    protected void onPostExecute(final Integer success) {
		if(this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }

        if(success == 1) {
            Toast.makeText(contx, "PDF Berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else if(success == 0) {
            Toast.makeText(contx, "PDF Gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }

}
