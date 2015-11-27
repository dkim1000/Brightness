package brighteness.dkim.com.brighteness;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by TheGreatKingDK on 15-11-26.
 */
public class BrightnessHeadService extends Service {

    static private boolean isLongHold = false;

    static private WindowManager windowManager;
    static private ImageView brightnessHead;

    static private WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.TYPE_PHONE,
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                            PixelFormat.TRANSLUCENT
                    );

    View.OnTouchListener brightnessHeadOnTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (isLongHold)
                    {
                        isLongHold = false;
                        Log.d("action watch", "STOP CALC");
                        return true;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (isLongHold)
                    {
                        Log.d("action watch", "DRAGGING");
                        return true;
                    }
            }
            return false;
        }
    };

    View.OnLongClickListener brightnessHeadOnLongClick = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Log.d("action watch",  "long click");
            isLongHold = true;
            return true;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        brightnessHead = new ImageView(this);
        brightnessHead.setImageResource(R.drawable.abc_btn_check_material);



        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 700;

        windowManager.addView(brightnessHead, params);

        brightnessHead.setOnLongClickListener(brightnessHeadOnLongClick);
        brightnessHead.setOnTouchListener(brightnessHeadOnTouch);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (brightnessHead != null)
        {
            windowManager.removeView(brightnessHead);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
