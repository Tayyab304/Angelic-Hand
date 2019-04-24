package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class FireBaseNumber_Authentication {

    String number;
    private listener_gernal mCallBack;
    Context context;
    Activity activity;
    FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    public FireBaseNumber_Authentication(Context ctx, listener_gernal callback) {

        context = ctx;
        activity = (Activity) ctx;
        mCallBack = callback;

        firebaseAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(activity,"2",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                mCallBack.onFailure(e);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                //Toast.makeText(activity,"3"+s,Toast.LENGTH_LONG).show();

                //codesent=s;
                super.onCodeSent(s, forceResendingToken);

                mCallBack.onSuccess(s);
//
            }
        };

    }

    public void send_code(String num){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                "+92"+num,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks);


    }

    public void verify_code(String code,String entered_code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code, entered_code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity,"aho",Toast.LENGTH_LONG).show();
                            mCallBack.onSuccess("true");


                        } else {
                            mCallBack.onSuccess("false");
                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                mCallBack.onFailure(task.getException());
                                Toast.makeText(activity,"to gya thakur",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
