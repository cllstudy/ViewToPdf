package com.rongsheng.viewtopdf;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : lei
 * @desc :
 * @date : 2018/11/14 0014  上午 11:07.
 * 个人博客站: http://www.bestlei.top
 */

public class Utils {
    /**
     * 对RecyclerView进行截图
     *  https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }
    /**
     * 将 Bitmap 保存到SD卡
     * @param context
     * @param mybitmap
     * @param name
     * @return
     */
    public static boolean saveBitmapToSdCard(Context context, Bitmap mybitmap, String name){
        boolean result = false;
        //创建位图保存目录
        String path = Environment.getExternalStorageDirectory() + "/1000ttt/";
        File sd = new File(path);
        if (!sd.exists()){
            sd.mkdir();
        }

        File file = new File(path+name+".jpg");
        FileOutputStream fileOutputStream = null;
//        if (!file.exists()){
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    fileOutputStream = new FileOutputStream(file);
                    mybitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    //update gallery
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    context.sendBroadcast(intent);
                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                    result = true;
                }
                else{
                    Toast.makeText(context, "不能读取到SD卡", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
        return result;
    }

    /**
     * 图片转pdf
     * @param pic_path   原始图片路径
     * @param pdf_path   转换后pdf路径
     * @return  0转换成功  -1失败
     */
    public static int pic2pdf(String pic_path,String pdf_path) {
        try {
            Image image = Image.getInstance(pic_path);
//            image.scaleToFit(PageSize.A4.getWidth() - 10, PageSize.A4.getHeight() - 10);
            image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            image.setAlignment(Image.MIDDLE);
            //设置图片的显示位置（居中）
            image.setAbsolutePosition((PageSize.A4.getWidth() - image.getScaledWidth()) / 2, (PageSize.A4.getHeight() - image.getScaledHeight()) / 2);
            float height = image.getHeight();
            float width = image.getWidth();
            //向文档中加入图片
            for (int i = 0; i < 2; i++) {
                  image.setAbsolutePosition(0.0f,0.0f);
            }
//            Document doc = new Document(new Rectangle(width, height));
            Document doc = new Document(PageSize.A4,20,20,20,20);
            doc.setMargins(10, 20, 80, 10);
            File output = new File(pdf_path);
            output.getParentFile().mkdirs();
            PdfWriter.getInstance(doc, new FileOutputStream(pdf_path));
            doc.open();
            doc.add(image);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }


}
