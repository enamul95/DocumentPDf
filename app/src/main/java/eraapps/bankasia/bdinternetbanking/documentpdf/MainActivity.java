package eraapps.bankasia.bdinternetbanking.documentpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String file_name_path = "";
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    String card_name = "00000 rafiq";

    TextView tv_digital_receipt_thank_title;
    TextView tv_digital_receipt_message;
    TextView tv_digital_receipt_date;
    TextView tv_digital_receipt_time;
    TextView tv_digital_receipt_merchant_name;
    TextView tv_digital_receipt_merchant_id;
    TextView tv_digital_receipt_sent;
    TextView tv_digital_receipt_paid_with;
    TextView tv_digital_receipt_paid_card;
    TextView tv_digital_receipt_payment_type;
    TextView tv_digital_receipt_scan_to_pay;
    TextView tv_digital_receipt_location;
    TextView tv_digital_receipt_city;
    TextView tv_digital_receipt_transaction_code;
    TextView tv_digital_receipt_transaction_no;

    View vw_date_bellow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        if (!ManifestPermistion.hasPermissions(MainActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }

        File file = new File(Environment.getExternalStorageDirectory(), "visacard_receit");
        if (!file.exists()) {
            file.mkdir();
        }

        tv_digital_receipt_thank_title = findViewById(R.id.tv_digital_receipt_thank_title);
        tv_digital_receipt_message = findViewById(R.id.tv_digital_receipt_message);
        tv_digital_receipt_date = findViewById(R.id.tv_digital_receipt_date);
        tv_digital_receipt_time = findViewById(R.id.tv_digital_receipt_time);
        tv_digital_receipt_merchant_name = findViewById(R.id.tv_digital_receipt_merchant_name);
        tv_digital_receipt_merchant_id = findViewById(R.id.tv_digital_receipt_merchant_id);
        tv_digital_receipt_sent = findViewById(R.id.tv_digital_receipt_sent);
        tv_digital_receipt_paid_with = findViewById(R.id.tv_digital_receipt_paid_with);
        tv_digital_receipt_paid_card = findViewById(R.id.tv_digital_receipt_paid_card);
        tv_digital_receipt_payment_type = findViewById(R.id.tv_digital_receipt_payment_type);
        tv_digital_receipt_scan_to_pay = findViewById(R.id.tv_digital_receipt_scan_to_pay);
        tv_digital_receipt_location = findViewById(R.id.tv_digital_receipt_location);
        tv_digital_receipt_city = findViewById(R.id.tv_digital_receipt_city);
        tv_digital_receipt_city = findViewById(R.id.tv_digital_receipt_city);
        tv_digital_receipt_transaction_code = findViewById(R.id.tv_digital_receipt_transaction_code);
        tv_digital_receipt_transaction_no = findViewById(R.id.tv_digital_receipt_transaction_no);

        vw_date_bellow = findViewById(R.id.vw_date_bellow);
        createpdf();


    }


    public void createpdf() {
        Rect bounds = new Rect();
        int pageWidth = 300;
        int pageheight = 470;
        int pathHeight = 2;

        final String fileName = getCurDateTime();
        file_name_path = "/visacard_receit/" + fileName + ".pdf";
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Path path = new Path();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageheight, 1).create();
        PdfDocument.Page documentPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = documentPage.getCanvas();
        int y = 25; // x = 10,
        //int x = (canvas.getWidth() / 2);
        int x = 10;

        paint.getTextBounds(tv_digital_receipt_thank_title.getText().toString(), 0, tv_digital_receipt_thank_title.getText().toString().length(), bounds);
        x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(tv_digital_receipt_thank_title.getText().toString(), x, y, paint);

        paint.getTextBounds(tv_digital_receipt_message.getText().toString(), 0, tv_digital_receipt_message.getText().toString().length(), bounds);
        x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        y += paint.descent() - paint.ascent();
        canvas.drawText(tv_digital_receipt_message.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space

        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_date.getText().toString() + " " + tv_digital_receipt_time.getText().toString(), x, y, paint);


        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText("Merchant Name(MN)", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_merchant_name.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_merchant_id.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_sent.getText().toString(), x, y, paint);


        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_paid_with.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_paid_card.getText().toString(), x, y, paint);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_payment_type.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_scan_to_pay.getText().toString(), x, y, paint);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_location.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_city.getText().toString(), x, y, paint);


        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_transaction_code.getText().toString(), x, y, paint);

        y += paint.descent() - paint.ascent();
        x = 10;
        canvas.drawText(tv_digital_receipt_transaction_no.getText().toString(), x, y, paint);



        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);

        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);


        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.visa_icon);
        Bitmap b =  (Bitmap.createScaledBitmap(bitmap, 50, 30, false));
        canvas.drawBitmap(b, x, y, paint);

        Bitmap bitmap2 = BitmapFactory.decodeResource(res, R.drawable.bank_asia_icon_color);
        Bitmap b2 =  (Bitmap.createScaledBitmap(bitmap2, 40, 40, false));
        canvas.drawBitmap(b2, 244, y, paint);



        myPdfDocument.finishPage(documentPage);

        File file = new File(Environment.getExternalStorageDirectory() + file_name_path);
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
        viewPdfFile();
    }

    public static Spanned fromHtml(String html, int flags) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, flags);
        } else {
            return Html.fromHtml(html);
        }
    }

    public void viewPdfFile() {

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + file_name_path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public static String getCurDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public String getHtmlPage() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<style>");
        html.append("table { border-collapse: collapse;  width: 100%;}");
        html.append(".tr-bottom { padding: 8px; border-bottom: 1px solid #ddd;}");
        html.append("</style>");

        html.append("<body>");
        html.append("<p style=\"text-align:center;\"><b>Thank You</b></p>");
        html.append("<p style=\"text-align:center;\"><b>Digital Receipt</b></p>");
        html.append("<table style=\"width:100%\">");

        html.append("<tr class=\"tr-bottom\"> <td> 19 0ct 2020</td>  <td style=\"text-align:right;\">12:00 PM</td></tr>");
        html.append(" <tr class=\"tr-bottom\"> <td><b>MN</b></td> <td><p><b>Cornar store</b></p><p>2828282828</p></td> <td style=\"color:red;\">sent</td> </tr>");
        html.append("<tr><td>Total Amount</td> <td style=\"text-align: right;\"><b>INR</b></td> <td style=\"text-align:left;\"><b>1,000</b></td></tr>");
        html.append(" <tr class=\"tr-bottom\"> <td><img src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"200\" height=\"100\"></td> <td style=\"text-align: left;\"> <p>Paid With</p> <p><b>" + card_name + "<b/></p> </td> </tr>");
        html.append("<tr class=\"tr-bottom\"> <td> <p>Payment Type</p><p><b>Scan to Pay</b></p></td> </tr> ");
        html.append("<tr class=\"tr-bottom\"> <td><p>Location</p><p><b>Dhaka</b></p></td> </tr>");
        html.append("<tr class=\"tr-bottom\"><td>\t<p>Transaction Code</p>\t<p><b>777777DR</b></p></td> </tr> ");
        html.append("</table> ");
        html.append("</body>");

        html.append("</html>");
        return String.valueOf(html);
    }
}