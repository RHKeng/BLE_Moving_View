package com.example.keng.ble_moving_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private int POINT_Size;
    private int pointX = 380;
    private int pointY = 100;
    private int MoveType = 1;    //定义区分移动的类型
    private boolean Index = true;  //判断是否发生移动
    private int xSpeed = 7;
    private int ySpeed = 7;
    private int tablewidth;
    private int tableHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MapView mapView = new MapView(this);
        setContentView(mapView);
        POINT_Size=10;

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        tablewidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;


        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what==0x123)
                {
                    mapView.invalidate();
                }
            }
        };

        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if(Index==true)
                {
                    if(count==18)
                    {
                        count = 0;
                        if(MoveType==4)
                        {
                            Index=false;
                        }
                        MoveType=4;
                        //Index = false;
                    }
                    else
                    {
                        count++;
                        switch (MoveType)
                        {
                            case 1:  //向左运动
                            {
                                pointX = pointX-xSpeed;
                                break;
                            }
                            case 2:  //向右运动
                            {
                                pointX = pointX+xSpeed;
                                break;
                            }
                            case 3:  //向上运动
                            {
                                pointY = pointY-ySpeed;
                                break;
                            }
                            case 4:  //向下运动
                            {
                                pointY = pointY+ySpeed;
                                break;
                            }
                        }
                    }
                }
                else{

                }
                handler.sendEmptyMessage(0x123);
            }
        },0,200);

    }

    class MapView extends View
    {
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.map);
        Matrix matrix = new Matrix();

        public MapView(Context context)
        {
            super(context);
            setFocusable(true);
        }

        public void onDraw(Canvas canvas)
        {
            matrix.setScale(0.6f,1.0f);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(bitmap, matrix, null);
            paint.setColor(Color.rgb(255, 0, 0));
            canvas.drawCircle(pointX,pointY,POINT_Size,paint);
        }


    }
}
