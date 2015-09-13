package com.mad.openisdm.madnew.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.mad.openisdm.madnew.listener.OnAddressReceiveResultListener;

/**
 * Created by aming on 2015/9/11.
 */
public class AddressResultReceiver extends ResultReceiver {
    protected OnAddressReceiveResultListener mOnAddressReceiveResultListener;

    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == FetchAddressIntentService.SUCCESS_RESULT) {
            String addressOutput = resultData.
                    getString(FetchAddressIntentService.FETCH_ADDRESS_RESULT_DATA_KEY);
            mOnAddressReceiveResultListener.onAddressReceiveCallback(addressOutput);
        }
    }

    public void setOnAddressResultReceiveResultListener(OnAddressReceiveResultListener listener) {
        mOnAddressReceiveResultListener = listener;
    }
}
