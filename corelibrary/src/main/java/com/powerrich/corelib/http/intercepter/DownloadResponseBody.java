package com.powerrich.corelib.http.intercepter;

import com.powerrich.corelib.msg.ProgressMsg;
import com.threshold.rxbus2.RxBus;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadResponseBody extends ResponseBody {

    public static final String DOWN_LOAD_KEY = "download";

    private ResponseBody responseBody;
    private DecimalFormat df = new DecimalFormat("0");


    private BufferedSource bufferedSource;
    private ProgressMsg msg =  new ProgressMsg(0,DOWN_LOAD_KEY);

    public DownloadResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;

    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                RxBus.getDefault().post(msg.setProgress(bytesReaded));
                return bytesRead;
            }
        };
    }


}