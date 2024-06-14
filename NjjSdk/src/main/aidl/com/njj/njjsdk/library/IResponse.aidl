// IBleResponse.aidl
package com.njj.njjsdk.library;

// Declare any non-default types here with import statements

interface IResponse {
    void onResponse(int code, inout Bundle data);
}
