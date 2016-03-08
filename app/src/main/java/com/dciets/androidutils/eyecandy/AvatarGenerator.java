package com.dciets.androidutils.eyecandy;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;

/**
 * Avatar Generator
 * @author mahinse
 *
 */
public class AvatarGenerator {

    /**
     * Generate an avatar based on dimensions
     * @param width The width of the image in px
     * @param height The height of the image in px
     * @return Returns the generated image
     */
    public static Bitmap generate(int width, int height) {
        Canvas canvas = new Canvas();
        //We create a bitmap adapted to the screen
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_4444);

        canvas.setBitmap(bitmap);

        //We use a random paint color for the rectangles
        int color = Color.rgb((int) (Math.random() * 254), (int) (Math.random() * 254), (int) (Math.random() * 254));
        Paint paint = new Paint();
        paint.setColor(color);

        ArrayList<Rect> rects = new ArrayList<Rect>();


        do{
            //We clear the canvas
            canvas.drawColor(Color.TRANSPARENT);
            //We create and draw three rectangles
            for (int i = 0; i < 3; i++) {
                boolean fits = true;
                Rect currentRect = null;
                do {
                    fits = true;
                    double rand = Math.random();
                    //We love horizontal rects more than vertical ones.
                    if (rand < 0.4) {
                        //vertical
                        int left = (int) (Math.random() * (width / 2));
                        int top = (int) (Math.random() * height);
                        int right = width / 6 + left;
                        int bottom = (int) Math.max((Math.random() * height), Math.min(20 + top, height));
                        currentRect = new Rect(left, top, right, bottom);
                    } else {
                        //horizontal
                        int left = (int) (Math.random() * (width / 2));
                        int top = (int) (Math.random() * height);
                        int right = (int) Math.max((Math.random() * (width / 2)), Math.min(20 + left, width / 2));
                        int bottom = (height / 6) + top;
                        currentRect = new Rect(left, top, right, bottom);
                    }

                    //We don't want rectangles to copulate.
                    for (Rect existingRect : rects) {
                        if (existingRect.contains(currentRect)) {
                            fits = false;
                        }
                    }
                } while (!fits);

                //If it fits, we add it to the array and draw it.
                rects.add(currentRect);
                canvas.drawRect(currentRect, paint);
            }

            //Now that we have drawn half of the canvas, we do a symmetrical copy of the first half.
            for (Rect r : rects) {
                r.left = width - r.left;
                r.right = width - r.right;
                canvas.drawRect(r, paint);
            }
        } while(percentTransparent(bitmap, Math.min(width, height)) > 0.7);


        return bitmap;
    }

    /**
     * Generate an avatar based on dimensions
     * @param context The Context
     * @param widthDp The width of the image in dp
     * @param heightDp The height of the image in dp
     * @return Returns the generated image
     */
    public static Bitmap generate(Context context, int widthDp, int heightDp) {
        Canvas canvas = new Canvas();
        //We create a bitmap adapted to the screen
        Bitmap bitmap = Bitmap.createBitmap(dpInPixels(context, widthDp), dpInPixels(context, heightDp),
                Config.ARGB_4444);

        canvas.setBitmap(bitmap);

        //We use a random paint color for the rectangles
        int color = Color.rgb((int) (Math.random() * 254), (int) (Math.random() * 254), (int) (Math.random() * 254));
        Paint paint = new Paint();
        paint.setColor(color);

        ArrayList<Rect> rects = new ArrayList<Rect>();


        do{
            //We clear the canvas
            canvas.drawColor(Color.TRANSPARENT);
            //We create and draw three rectangles
            for (int i = 0; i < 3; i++) {
                boolean fits = true;
                Rect currentRect = null;
                do {
                    fits = true;
                    double rand = Math.random();
                    //We love horizontal rects more than vertical ones.
                    if (rand < 0.4) {
                        //vertical
                        int left = dpInPixels(context, (int) (Math.random() * (widthDp / 2)));
                        int top = dpInPixels(context, (int) (Math.random() * heightDp));
                        int right = dpInPixels(context, widthDp / 6) + left;
                        int bottom = dpInPixels(context, (int) Math.max((Math.random() * heightDp), Math.min(20 + top, heightDp)));
                        currentRect = new Rect(left, top, right, bottom);
                    } else {
                        //horizontal
                        int left = dpInPixels(context, (int) (Math.random() * (widthDp / 2)));
                        int top = dpInPixels(context, (int) (Math.random() * heightDp));
                        int right = dpInPixels(context, (int) Math.max((Math.random() * (widthDp / 2)), Math.min(20 + left, widthDp / 2)));
                        int bottom = dpInPixels(context, heightDp / 6) + top;
                        currentRect = new Rect(left, top, right, bottom);
                    }

                    //We don't want rectangles to copulate.
                    for (Rect existingRect : rects) {
                        if (existingRect.contains(currentRect)) {
                            fits = false;
                        }
                    }
                } while (!fits);

                //If it fits, we add it to the array and draw it.
                rects.add(currentRect);
                canvas.drawRect(currentRect, paint);
            }

            //Now that we have drawn half of the canvas, we do a symmetrical copy of the first half.
            for (Rect r : rects) {
                r.left = dpInPixels(context, widthDp) - r.left;
                r.right = dpInPixels(context, widthDp) - r.right;
                canvas.drawRect(r, paint);
            }
        } while(percentTransparent(bitmap, Math.min(widthDp, heightDp)) > 0.7);


        return bitmap;
    }

    private static int dpInPixels(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * @param bm Bitmap to determine
     * @param scale Number of pixels to look for in the bitmap
     * @return Percentage of the bitmap that is transparent
     */
    private static float percentTransparent(Bitmap bm, int scale) {

        final int width = bm.getWidth();
        final int height = bm.getHeight();

        // size of sample rectangles
        final int xStep = width/scale;
        final int yStep = height/scale;

        // center of the first rectangle
        final int xInit = xStep/2;
        final int yInit = yStep/2;

        // center of the last rectangle
        int xEnd = width - xStep/2;
        int yEnd = height - yStep/2;

        if (xEnd >= width) {
            xEnd = width-1;
        }
        if (yEnd >= height) {
            yEnd = height-1;
        }

        int totalTransparent = 0;

        for(int x = xInit; x <= xEnd; x += xStep) {
            for(int y = yInit; y <= yEnd; y += yStep) {
                if (bm.getPixel(x, y) == Color.TRANSPARENT) {
                    totalTransparent++;
                }
            }
        }
        return ((float)totalTransparent)/(scale * scale);

    }
}