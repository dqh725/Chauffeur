package com.chauffr.registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chauffr.API;
import com.chauffr.R;
import com.chauffr.registration.adapter.ExpiryDatePicker;
import com.chauffr.registration.adapter.FourDigitCardFormatWatcher;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Signup3 extends AppCompatActivity {

//    private class GetCustomIdTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... params) {
//            com.stripe.Stripe.apiKey = stripeAPIKey;
//            Map<String, Object> customerParams = new HashMap<String, Object>();
//            customerParams.put("source", params[0]);
//            customerParams.put("description", "Example customer");
//
//            try {
//                Customer customer = Customer.create(customerParams);
//                Log.i("mycustom", customer.getId());
//                return customer.getId();
//
//            } catch (AuthenticationException e) {
//                e.printStackTrace();
//            } catch (InvalidRequestException e) {
//                e.printStackTrace();
//            } catch (APIConnectionException e) {
//                e.printStackTrace();
//            } catch (CardException e) {
//                e.printStackTrace();
//            } catch (APIException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

    private class RegisterClientTask extends AsyncTask<JSONObject, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(JSONObject... params) {
            Log.i("newClient", params[0].toString());
            JSONObject response = API.registerClientRequest(params[0]);
            Log.i("response", response.toString());
            return response;
        }
        @Override
        protected void onPostExecute(JSONObject response) {
            showProgress(false);
            try {
                if(response.get("ErrorCode")!=0) {
                    showErrorDialog((String) response.get("FriendlyMessage"));
                }
                else{
                    showErrorDialog("Successfully registerd a client!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private static final String PUBLISHABLE_KEY = "pk_test_yGhyZ8l19ZkYcBunOhi21bA1";

    TextView cardNumberText, termsView;
    EditText expiryDateText, cardCVCText;
    private View mProgressView;
    private View mLoginFormView;
    Button create;
    JSONObject newClient= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup3_activity);
        try {
            newClient = new JSONObject(getIntent().getStringExtra("newClient"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cardNumberText = (TextView) findViewById( R.id.credit_card);
        cardNumberText.addTextChangedListener(new FourDigitCardFormatWatcher());

        expiryDateText = (EditText) findViewById(R.id.card_expiry_date);
        cardCVCText = (EditText) findViewById(R.id.card_CVC);
        expiryDateText.setOnClickListener(new ExpiryDatePicker(Signup3.this, R.id.card_expiry_date));

        termsView = (TextView) findViewById(R.id.terms_and_condition);
        termsView.setClickable(true);
        termsView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://chauffeur.co'> Terms & Conditions </a>";
        termsView.setText(Html.fromHtml(text));


        create = (Button) findViewById(R.id.create_new_account_btn);
        mProgressView = findViewById(R.id.validate_progress);
        mLoginFormView = findViewById(R.id.login_form);
    }

    public void registerClient(View view) {
        String cardNumber = cardNumberText.getText().toString();
        Log.i("mycard", cardNumber);
        int cardExpMonth=0, cardExpYear=0;
        if(expiryDateText.getText().toString()!=null) {
            String[] expiryDate = expiryDateText.getText().toString().split("/");
            cardExpMonth = Integer.parseInt(expiryDate[0]);
            cardExpYear =Integer.parseInt(expiryDate[1]);
        }
        String cardCVC = cardCVCText.getText().toString();
        Log.i("myCVC",cardCVC);

        Card card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        Log.i("myCard", card.toString());

        boolean validation = card.validateCard();
        if (validation) {
            showProgress(true);
            try {
                Stripe stripe = new Stripe(PUBLISHABLE_KEY);
                stripe.createToken(
                        card,
                        PUBLISHABLE_KEY,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                try {
                                    if(newClient!=null){
                                        newClient.put("PayKey", token.getId());
                                        JSONObject responseJSON = new RegisterClientTask().execute(newClient).get();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    // asyncTask
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                //finishProgress();
                            }

                            public void onError(Exception error) {
                                handleError(error.getLocalizedMessage(), 5);
                                finishProgress();
                            }
                        });
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

        } else if (!card.validateNumber()) {
            handleError("The card number that you entered is invalid",1);
        } else if (!card.validateExpiryDate()) {
            handleError("The expiration date that you entered is invalid",2);
        } else if (!card.validateCVC()) {
            handleError("The CVC code that you entered is invalid",3);
        } else {
            handleError("The card details that you entered are invalid",4);
        }
    }




    /* Shows the progress UI and hides the card form.*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void finishProgress() {
//        showProgress(false);
//        new AlertDialog.Builder(Signup3.this)
//                .setTitle("The card has been verified!")
//                .setMessage("Start to connect to your chauffeur now!")
//                .setPositiveButton(android.R.string.ok,  new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // create account
//                        dialog.cancel();
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                        dialog.cancel();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .show();
    }

    private void handleError(String error, int option) {
        Log.i("myerror", error);
        View focusView = null;
        switch(option) {
            case 1:{
                cardNumberText.setError(getString(R.string.error_card_details));
                focusView = cardNumberText;
                break;
            }
            case 2:{
                expiryDateText.setError(getString(R.string.error_date));
                focusView = expiryDateText;
                break;
            }
            case 3:{
                cardCVCText.setError(getString(R.string.error_CVC));
                focusView = cardCVCText;
                break;
            }
            case 4:{
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(Signup3.this, getString(R.string.error_card_details), duration);
                break;
            }
            default:{
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(Signup3.this,"Something wrong", duration);
                break;
            }
        }
    }

    private void showErrorDialog(String message){
        new AlertDialog.Builder(Signup3.this)
                .setTitle("Error!")
                .setMessage(message)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
