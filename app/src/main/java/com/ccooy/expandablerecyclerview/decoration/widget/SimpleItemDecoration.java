package com.ccooy.expandablerecyclerview.decoration.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.ccooy.expandablerecyclerview.R;

public class SimpleItemDecoration extends RecyclerView.ItemDecoration {


    private Bitmap bitmap;
    private Paint.FontMetrics fontMetrics;
    private int wight;
    private int itemDecorationHeight;
    private Paint paint;
    private ObtainTextCallback callback;
    private float itemDecorationPadding;
    private TextPaint textPaint;
    private Rect text_rect=new Rect();
    public SimpleItemDecoration(Context context, ObtainTextCallback callback) {

        wight=context.getResources().getDisplayMetrics().widthPixels;
        paint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
        itemDecorationHeight=DensityUtil.dip2px(context, 30);
        itemDecorationPadding=DensityUtil.dip2px(context, 10);
        this.callback = callback;

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(DensityUtil.dip2px(context, 25));
        fontMetrics = new Paint.FontMetrics();
        textPaint.getFontMetrics(fontMetrics);

        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_right);
        ScaleBitmap();
    }

    //bitmap的大小和itemDecorationHeight进行比较对图片进行缩放操作(对性能有追求可以在加载到内存的时候进行压缩)
    private void ScaleBitmap() {
        Matrix matrix=new Matrix();
        float scale=bitmap.getWidth()>itemDecorationHeight? (float) itemDecorationHeight / (float) bitmap.getHeight() : (float) bitmap.getHeight() / (float) itemDecorationHeight;
        matrix.postScale(scale,scale);
        bitmap= Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
    }



    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int count=parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view=parent.getChildAt(i);
            int top=view.getTop()-itemDecorationHeight;
            int bottom=top+itemDecorationHeight;


            int position = parent.getChildAdapterPosition(view);
            String content = callback.getText(position);
            textPaint.getTextBounds(content,0, content.length(),text_rect);

            if(isFirstInGroup(position)) {
                c.drawRect(0,top,wight,bottom,paint);
                c.drawText(content, itemDecorationPadding+bitmap.getWidth(), bottom-fontMetrics.descent, textPaint);
                c.drawBitmap(bitmap,itemDecorationPadding,bottom-bitmap.getHeight(),paint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        View child0=parent.getChildAt(0);
        int position = parent.getChildAdapterPosition(child0);
        String content = callback.getText(position);
        if(child0.getBottom()<=itemDecorationHeight&&isFirstInGroup(position+1)){
            c.drawRect(0, 0, wight, child0.getBottom(), paint);
            c.drawText(content, itemDecorationPadding+bitmap.getWidth(), child0.getBottom()-fontMetrics.descent, textPaint);
            c.drawBitmap(bitmap,itemDecorationPadding,child0.getBottom()-bitmap.getHeight(),paint);
        }
        else {
            c.drawRect(0, 0, wight, itemDecorationHeight, paint);
            c.drawText(content, itemDecorationPadding+bitmap.getWidth(), itemDecorationHeight-fontMetrics.descent, textPaint);
            c.drawBitmap(bitmap,itemDecorationPadding,itemDecorationHeight-bitmap.getHeight(),paint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position= parent.getChildAdapterPosition(view);
        //如果不是在同一组就腾出分割线需要的高度
        if(isFirstInGroup(position)){
            outRect.top=itemDecorationHeight;
        }

    }

    //回调接口,通过该回调获取item的内容的第一个文字
    public interface ObtainTextCallback {
        String getText(int position);
    }

    //判断当前item和下一个item的第一个文字是否相同,如果相同说明是同一组,不需要画分割线
    private boolean isFirstInGroup(int pos) {
        //如果是adapter的第一个position直接return,因为第一个item必须有分割线
        if (pos == 0) {
            return true;
        } else {
            //否者判断前一个item的字符串 与 当前item字符串 是否相同
            String prevGroupId = callback.getText(pos - 1);
            String groupId = callback.getText(pos);
            if (prevGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }
    }
}
