package com.powerrich.corelib.http.intercepter;

import com.powerrich.corelib.msg.ProgressMsg;
import com.threshold.rxbus2.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadRequestBody extends RequestBody {
    public static final String UPLOAD_KEY = "upload";

    private RequestBody mRequestBody;
    private CountingSink mCountingSink;
    private ProgressMsg msg =  new ProgressMsg(0,UPLOAD_KEY);

    public UploadRequestBody(RequestBody requestBody) {
        mRequestBody = requestBody;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mRequestBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;

        mCountingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(mCountingSink);

        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            msg.setSize(contentLength());
            float resaut = (float) bytesWritten / contentLength();
            RxBus.getDefault().post(msg.setProgress(bytesWritten));
        }
    }
}
