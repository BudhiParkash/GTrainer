package com.example.gtrainer.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;

import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder;
import com.squareup.picasso.OkHttp3Downloader;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class PicassoRegionDecoder implements ImageRegionDecoder {
private OkHttpClient client;
private BitmapRegionDecoder decoder;
private final Object decoderLock = new Object();

public PicassoRegionDecoder (OkHttpClient client) {
        this.client = client;
        }

@Override
public Point init(Context context, Uri uri) throws Exception {
        OkHttp3Downloader downloader = new OkHttp3Downloader(client);
        Request request = new Request.Builder().url(uri.toString()).build();
        InputStream inputStream = downloader.load(request).body().byteStream();
        this.decoder = BitmapRegionDecoder.newInstance(inputStream, false);

        return new Point(this.decoder.getWidth(), this.decoder.getHeight());
        }

@Override
public Bitmap decodeRegion(Rect rect, int sampleSize) {
synchronized(this.decoderLock) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = this.decoder.decodeRegion(rect, options);
        if(bitmap == null) {
        throw new RuntimeException("Region decoder returned null bitmap - image format may not be supported");
        } else {
        return bitmap;
        }
        }
        }

@Override
public boolean isReady() {
        return this.decoder != null && !this.decoder.isRecycled();
        }

@Override
public void recycle() {
        this.decoder.recycle();
        }
        }



