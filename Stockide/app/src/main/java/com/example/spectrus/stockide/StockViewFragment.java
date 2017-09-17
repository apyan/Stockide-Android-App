package com.example.spectrus.stockide;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spectrus.stockide.AppFunction.AppConnect;
import com.example.spectrus.stockide.AppFunction.AppGraphics;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StockViewFragment extends Fragment implements View.OnClickListener {

    // Variables for Stock View Fragment
    AppGraphics stockFragGraphics;
    AppConnect stackFragConnect;

    private static final String TAG = "Markit";
    public String lookupUrl = "http://dev.markitondemand.com/MODApis/Api/v2/Lookup?input=";
    public String quoteUrl = "http://dev.markitondemand.com/MODApis/Api/v2/Quote?symbol=";

    //public ArrayList <FoundQuote> quoteListing;
    ArrayList<String> quotesList;
    public ListView list_000;
    public EditText edit_000;
    public Context context;
    public String insertPut;
    public PopupWindow popup_000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment
        View v = inflater.inflate(R.layout.fragment_stock_view, container, false);
        stockFragGraphics = new AppGraphics(getActivity());
        stackFragConnect = new AppConnect(getActivity());

        context = getActivity();

        list_000 = (ListView) v.findViewById(R.id.list_000);
        edit_000 = (EditText) v.findViewById(R.id.edit_00);

        // Button onClick Inputs

        // Seek Stock Quote and Display
        Button button_00 = (Button) v.findViewById(R.id.button_00);
        button_00.setOnClickListener(this);
        //button_00.setTypeface(font_0);
        button_00.setTextColor(Color.WHITE);

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Seek Stock Quote and Display
            case R.id.button_00:
                // Obtained user's input
                insertPut = edit_000.getText().toString();
                //quoteListing = new ArrayList <FoundQuote> ();

                quotesList = new ArrayList<>();

                // Checks for Internet Connection
                if(!stackFragConnect.connectionAvailable()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.quote_004), Toast.LENGTH_SHORT).show();
                    return;
                }
                // Invalid Input
                if(insertPut.trim().equals("")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.quote_003), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Execute Asynchronous Task
                QuoteSeeking seeker = new QuoteSeeking();
                seeker.execute(insertPut);
                quotesList = seeker.quotesAvailable();

                // Hides Keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                list_000.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // Checks for Internet Connection
                        if(!stackFragConnect.connectionAvailable()){
                            Toast.makeText(context, getResources().getString(R.string.quote_004), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String extractSymbol = quotesList.get(position);
                        int indexer = extractSymbol.indexOf('\n');
                        extractSymbol = extractSymbol.substring(0, indexer);

                        // Execute Asynchronous Task
                        StockSeeking inform = new StockSeeking();
                        inform.execute(extractSymbol);
                        //stockData = inform.quotesAvailable();
                    }
                });
                break;

            // Closes Stock Viewing Popup
            //case R.id.button_pop_00:
            //    popup_000.dismiss();
            //    break;

            default:
                break;
        }
    }

    // Asynchronous Task of Quote Listing
    private class StockSeeking extends AsyncTask<String, String, String> {

        // Variables needed for the AsyncTask
        private String error = "";
        ProgressDialog progressDialog;
        ArrayList <String> infoAvail = new ArrayList();

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    getResources().getString(R.string.dialog_002),
                    getResources().getString(R.string.dialog_003));
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(quoteUrl + params[0]);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;
                // Returns the type of current event
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        // Get 'StockQuote list
                        if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_000))) {
                            insideItem = true;
                            // Get 'Status' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_001))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Name' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_002))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Symbol' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_003))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'LastPrice' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_004))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Change' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_005))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'ChangePercent' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_006))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Timestamp' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_007))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'MSDate' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_008))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'MarketCap' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_009))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Volume' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_010))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'ChangeYTD' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_011))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'ChangePercentYTD' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_012))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'High' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_013))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Low' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_014))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                            // Get 'Open' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_015))) {
                            if(insideItem) infoAvail.add(xpp.nextText());
                        }
                    } else if(eventType == XmlPullParser.END_TAG &&
                            xpp.getName().equalsIgnoreCase(getResources().getString(R.string.seek_000))) {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String message) {
            // Dismiss Progress Dialog
            progressDialog.dismiss();

            // Variables for the Popup
            TextView text_00, text_01, text_02, text_03,
                    text_04, text_05, text_06, text_07, text_08,
                    text_09, text_10, text_11;

            // Popup stuff
            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_quote_000,
                    (ViewGroup) getActivity().findViewById(R.id.popup_quote_000));
            popup_000 = new PopupWindow(layout, metrics.widthPixels,
                    metrics.heightPixels, true);
            popup_000.showAtLocation(layout, Gravity.CENTER, 0, 0);

            text_00 = (TextView) popup_000.getContentView().findViewById(R.id.name_000);
            text_00.setText(infoAvail.get(1));
            text_01 = (TextView) popup_000.getContentView().findViewById(R.id.symbol_000);
            text_01.setText(infoAvail.get(2));
            text_02 = (TextView) popup_000.getContentView().findViewById(R.id.lprice_000);
            text_02.setText(infoAvail.get(3) + " " + getResources().getString(R.string.curr_000));
            text_03 = (TextView) popup_000.getContentView().findViewById(R.id.change_000);
            if(infoAvail.get(5).length() < 5) {
                if(infoAvail.get(5).contains("-")) {
                    text_03.setText(infoAvail.get(4) + " (" + infoAvail.get(5) + "%)");
                    text_03.setTextColor(Color.parseColor("#f71818"));
                } else {
                    text_03.setText("+" + infoAvail.get(4) + " (+" + infoAvail.get(5) + "%)");
                    text_03.setTextColor(Color.parseColor("#1af451"));
                }
            } else {
                if(infoAvail.get(5).contains("-")) {
                    text_03.setText(infoAvail.get(4) + " (" + infoAvail.get(5).substring(0,
                            infoAvail.get(5).indexOf('.') + 3) + "%)");
                    text_03.setTextColor(Color.parseColor("#f71818"));
                } else {
                    text_03.setText("+" + infoAvail.get(4) + " (+" + infoAvail.get(5).substring(0,
                            infoAvail.get(5).indexOf('.') + 3) + "%)");
                    text_03.setTextColor(Color.parseColor("#1af451"));
                }
            }
            text_04 = (TextView) popup_000.getContentView().findViewById(R.id.time_001);
            text_04.setText(infoAvail.get(6));
            text_05 = (TextView) popup_000.getContentView().findViewById(R.id.marketcap_001);
            text_05.setText(infoAvail.get(8));
            text_06 = (TextView) popup_000.getContentView().findViewById(R.id.volume_001);
            text_06.setText(infoAvail.get(9));
            text_07 = (TextView) popup_000.getContentView().findViewById(R.id.ytd_000);
            text_07.setText(infoAvail.get(10));
            text_08 = (TextView) popup_000.getContentView().findViewById(R.id.ytdchange_000);
            if(infoAvail.get(11).length() < 5) {
                if(infoAvail.get(11).contains("-")) {
                    text_08.setText(infoAvail.get(11) + "%");
                    text_08.setTextColor(Color.parseColor("#f71818"));
                } else {
                    text_08.setText("+" + infoAvail.get(11) + "%");
                    text_08.setTextColor(Color.parseColor("#1af451"));
                }
            } else {
                if(infoAvail.get(11).contains("-")) {
                    text_08.setText(infoAvail.get(11).substring(0, infoAvail.get(11).indexOf('.') + 3) + "%");
                    text_08.setTextColor(Color.parseColor("#f71818"));
                } else {
                    text_08.setText("+" + infoAvail.get(11).substring(0, infoAvail.get(11).indexOf('.') + 3) + "%");
                    text_08.setTextColor(Color.parseColor("#1af451"));
                }
            }
            text_09 = (TextView) popup_000.getContentView().findViewById(R.id.high_001);
            text_09.setText(infoAvail.get(12));
            text_10 = (TextView) popup_000.getContentView().findViewById(R.id.low_001);
            text_10.setText(infoAvail.get(13));
            text_11 = (TextView) popup_000.getContentView().findViewById(R.id.open_001);
            text_11.setText(infoAvail.get(14));

            // Closes Stock Viewing Popup
            Button button_01 = (Button) popup_000.getContentView().findViewById(R.id.button_pop_00);
            button_01.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popup_000.dismiss();
                }
            });
            //button_01.setOnClickListener(this)
            //button_01.setTypeface(font_0);
            button_01.setTextColor(Color.WHITE);
        }

        // Get the URL input stream
        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        // Return the quotes listing
        public ArrayList <String> quotesAvailable() {
            return infoAvail;
        }
    }

    // Asynchronous Task of Quote Listing
    private class QuoteSeeking extends AsyncTask<String, String, String> {

        // Variables needed for the AsyncTask
        ArrayList <String> convSymbol = new ArrayList();
        ArrayList <String> convName = new ArrayList();
        ArrayList <String> convExchange = new ArrayList();

        private String error = "";
        ProgressDialog progressDialog;
        ArrayList <String> quotesAvail = new ArrayList();

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    getResources().getString(R.string.dialog_000),
                    getResources().getString(R.string.dialog_001));
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(lookupUrl + params[0].trim());

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;

                // Returns the type of current event
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        // Get 'LookupResult' list
                        if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.lookup_001))) {
                            insideItem = true;
                            // Get 'Symbol' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.lookup_002))) {
                            if(insideItem) convSymbol.add(xpp.nextText());
                            // Get 'Name' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.lookup_003))) {
                            if(insideItem) convName.add(xpp.nextText());
                            // Get 'Exchange' list
                        } else if(xpp.getName().equalsIgnoreCase(getResources().getString(R.string.lookup_004))) {
                            if(insideItem) convExchange.add(xpp.nextText());
                        }
                    } else if(eventType == XmlPullParser.END_TAG &&
                            xpp.getName().equalsIgnoreCase(getResources().getString(R.string.lookup_001))) {
                        insideItem = false;
                    }

                    //quoteListing.add(new FoundQuote(convSymbol, convName, convExchange));
                    eventType = xpp.next(); //move to next element
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                error = e.getMessage();
            }
            return error;
        }

        @Override
        protected void onPostExecute(String message) {
            // Dismiss Progress Dialog
            progressDialog.dismiss();

            // Mend the found quote statuses together
            for(int index = 0; index < convSymbol.size(); index++){
                quotesAvail.add(convSymbol.get(index) + "\n" + convName.get(index) + "\nExchange: " + convExchange.get(index));
            }

            ArrayAdapter adapter = new ArrayAdapter(context,
                    android.R.layout.simple_list_item_1, quotesAvail);
            list_000.setAdapter(adapter);
        }

        // Get the URL input stream
        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        // Return the quotes listing
        public ArrayList <String> quotesAvailable() {
            return quotesAvail;
        }
    }

}
