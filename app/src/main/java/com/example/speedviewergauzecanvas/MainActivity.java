package com.example.speedviewergauzecanvas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private int sliceAngel = 180/5;
    private int R_;
    private Paint textPaint;
    private int outerRadius, innerRadius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateDrawing();
    }

    private void initiateDrawing() {
        try {
            final ImageView imageView = findViewById(R.id.image);

            ViewTreeObserver vto = imageView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int width = imageView.getMeasuredWidth();
                    int height = imageView.getMeasuredHeight();

                    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    textPaint.setColor(getResources().getColor(R.color.black_deep));
                    textPaint.setTextSize(34);

                    outerRadius = (int) (width*0.5);
                    innerRadius = (int) (outerRadius - outerRadius*0.28);
                    R_ =  innerRadius + (outerRadius - innerRadius)/2;

                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);

                    drawColoredHollow(width, height, canvas);
                    drawCoverMoon(width, height, canvas);
                    drawCenterHalfSphere(width, height, canvas);

                    setLowText(width, height, canvas);
                    setModerateLowText(width, height, canvas);
                    setModerateText(width, height, canvas);
                    setModerateHighText(width, height, canvas);
                    setHighText(width, height, canvas);
                    setPointer(0, width, height, canvas);

                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPointer(int pin, int width, int height, Canvas canvas){
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(getResources().getColor(R.color.centerHaldfMoonColor));
        linePaint.setStrokeWidth(10);

        int angle = (int) (pin*sliceAngel + 0.5*sliceAngel);
        int R =  innerRadius;
        float x = (float) (R*Math.cos(Math.toRadians(angle)));
        float y = (float) (R*Math.sin(Math.toRadians(angle)));
        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.drawLine(width/2, height, x_, y_, linePaint);
    }

    private void setLowText(int width, int height, Canvas canvas){
        int angle = 10;
        float x = (float) (R_*Math.cos(Math.toRadians(angle)));
        float y = (float) (R_*Math.sin(Math.toRadians(angle)));

        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.save();
        canvas.rotate(-sliceAngel*2, x_, y_);
        canvas.drawText("Low", x_, y_, textPaint);
        canvas.restore();
    }

    private void setModerateLowText(int width, int height, Canvas canvas){
        int angle = sliceAngel + 5;
        float x = (float) (R_*Math.cos(Math.toRadians(angle)));
        float y = (float) (R_*Math.sin(Math.toRadians(angle)));

        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.save();
        canvas.rotate((float) (-sliceAngel*1.1), x_, y_);
        canvas.drawText("Moderate", x_, y_, textPaint);
        canvas.restore();

        angle = sliceAngel + 10;
        x = (float) ((R_-45)*Math.cos(Math.toRadians(angle)));
        y = (float) ((R_-45)*Math.sin(Math.toRadians(angle)));

        x_ = width/2 - x;
        y_ = height - y;
        canvas.save();
        canvas.rotate((float) (-sliceAngel*1.1), x_, y_);
        canvas.drawText("Low", x_, y_, textPaint);
        canvas.restore();
    }

    private void setModerateText(int width, int height, Canvas canvas){
        int angle = 2*sliceAngel + 5;
        float x = (float) (R_*Math.cos(Math.toRadians(angle)));
        float y = (float) (R_*Math.sin(Math.toRadians(angle)));

        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.save();
        canvas.rotate(0, x_, y_);
        canvas.drawText("Moderate", x_, y_, textPaint);
        canvas.restore();
    }

    private void setModerateHighText(int width, int height, Canvas canvas){
        int angle = 3*sliceAngel + 5;
        float x = (float) (R_*Math.cos(Math.toRadians(angle)));
        float y = (float) (R_*Math.sin(Math.toRadians(angle)));

        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.save();
        canvas.rotate(sliceAngel, x_, y_);
        canvas.drawText("Moderate", x_, y_, textPaint);
        canvas.restore();

        angle = 3*sliceAngel + 10;
        x = (float) ((R_-45)*Math.cos(Math.toRadians(angle)));
        y = (float) ((R_-45)*Math.sin(Math.toRadians(angle)));

        x_ = width/2 - x;
        y_ = height - y;
        canvas.save();
        canvas.rotate(sliceAngel, x_, y_);
        canvas.drawText("High", x_, y_, textPaint);
        canvas.restore();
    }

    private void setHighText(int width, int height, Canvas canvas){
        int angle = 4*sliceAngel + 15;
        float x = (float) (R_*Math.cos(Math.toRadians(angle)));
        float y = (float) (R_*Math.sin(Math.toRadians(angle)));

        float x_ = width/2 - x;
        float y_ = height - y;
        canvas.save();
        canvas.rotate((float) (2*sliceAngel*.9), x_, y_);
        canvas.drawText("High", x_, y_, textPaint);
        canvas.restore();
    }

    private void drawCoverMoon(int mWidth, int mHeight, Canvas canvas){
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(getResources().getColor(R.color.black));

        int mRadius = innerRadius;
        int left = mWidth/2- mRadius;
        int right = mWidth/2 + mRadius;
        int top = mHeight - mRadius;
        int bottom = mHeight+mRadius;

        final RectF rect = new RectF();
        rect.set(left, top, right, bottom);

        canvas.drawArc(rect, 180, 180, true, rectPaint);

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(getResources().getColor(R.color.white));
        for (int i = 1; i < 5; i++) {
            int angle = i*sliceAngel;
            int R =  innerRadius;
            float x = (float) (R*Math.cos(Math.toRadians(angle)));
            float y = (float) (R*Math.sin(Math.toRadians(angle)));
            float x_ = mWidth/2 - x;
            float y_ = mHeight - y;
            canvas.drawLine(mWidth/2, mHeight, x_, y_, linePaint);
        }
    }

    private void drawColoredHollow(int width, int height, Canvas canvas){
        int colorArr[] = {
                getResources().getColor(R.color.lowHollowColor),
                getResources().getColor(R.color.moderateLowHollowColor),
                getResources().getColor(R.color.moderateHollowColor),
                getResources().getColor(R.color.moderateHighHollowColor),
                getResources().getColor(R.color.highHollowColor)
        };

        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        int mRadius = outerRadius;
        int left = width/2- mRadius;
        int right = width/2 + mRadius;
        int top = height - mRadius;
        int bottom = height+mRadius;

        final RectF rect = new RectF();
        rect.set(left, top, right, bottom);

        for (int i = 0; i <5 ; i++) {
            rectPaint.setColor(colorArr[i]);
            canvas.drawArc(rect, 180+i*sliceAngel, sliceAngel, true, rectPaint);
        }
    }


    private void drawCenterHalfSphere(int mWidth, int mHeight, Canvas canvas){
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(getResources().getColor(R.color.centerHaldfMoonColor));

        int mRadius = 40;
        int left = mWidth/2- mRadius;
        int right = mWidth/2 + mRadius;
        int top = mHeight - mRadius;
        int bottom = mHeight+mRadius;

        final RectF rect = new RectF();
        rect.set(left, top, right, bottom);

        canvas.drawArc(rect, 180, 180, true, rectPaint);
    }
}