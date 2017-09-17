package com.example.spectrus.stockide.AppFunction;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.example.spectrus.stockide.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used for writing and saving data
 * of the Android application.
 */

public class AppStorage {

    // Variables
    SimpleDateFormat formatter;
    Date today;

    public int MEMORY_SPACE = 25;
    Context context;
    public String filename;
    public String currentDay;
    public String [] dataRead = new String [MEMORY_SPACE];

    // dataRead Notes
    // 0: Player's Name
    // 1: Start Date
    // 2: Last Login Date
    // 3: Connect Link (Gmail)
    // 4: Contact Tickets (000001.)
    // 5: Tokens (Other)
    // 6: Title Screen Options (1: true to skip, 0: false to keep)
    // 7: Graphics (ex. Medium)
    // 8: Multiple Choice Scores
    // 9: Glossary Records (Entry 0-99 (#1-100)) (0000000000000....) (1 = unlocked)
    // 10: Time Slot Holder (For Last Login Switch)
    // 11: Dialogs Options (1: true to show, 0: false to skip)
    // 12: Medals Obtained (1: Obtained, 0: Not obtained)
    // 13: In-Game Currency
    // 14: Glossary Records (Entry 100-199 (#101-200)) (0000000000000....) (1 = unlocked)
    // 15: Glossary Records (Entry 200-299 (#201-300)) (0000000000000....) (1 = unlocked)
    // 16: Game Modes Options (00000(Flashcards)_00000(Multiple Choice)_00000_00000_00000)

    // Constructor
    // Ex. ChemPort = "user-cp-s.cp"
    public AppStorage(Context eContext, String name){
        context = eContext;
        filename = name;

        // Get updated date
        formatter = new SimpleDateFormat("yyyy_MM_dd");
        today = new Date();
        currentDay = formatter.format(today);
    }

    // Setting Up New Data File
    public void initializedData(){
        dataRead[0] = "";
        dataRead[1] = currentDay;
        dataRead[2] = currentDay;
        dataRead[3] = "";
        dataRead[4] = "";
        dataRead[5] = "";
        dataRead[6] = "01000000";
        dataRead[7] = "Medium";
        dataRead[8] = "0";
        dataRead[9] = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        dataRead[10] = currentDay;
        dataRead[11] = "1111";
        dataRead[12] = "0000000000";
        dataRead[13] = "0";
        dataRead[14] = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        dataRead[15] = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        dataRead[16] = "00000_00000_00000_00000_00000_00000_00000_00000_00000_00000";
        dataRead[17] = "";
        dataRead[18] = "";
        dataRead[19] = "";
        dataRead[20] = "";
        dataRead[21] = "";
        dataRead[22] = "";
        dataRead[23] = "";
        dataRead[24] = "";
    }

    // Checks if file exists
    // Note: If not, create a popup for data initialization
    public void doFileExist() {
        File file = new File(context.getFilesDir(), filename);
        if(!file.exists()) {
            // Variables
            initializedData();
            dataWrite();
            /*String tester = currentDay + "\r\nNew Caller X\n";

            try {
                FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
                out.write(tester);
                out.close();
            } catch (IOException e) {
                //Logger.logError(TAG, e);
            }*/
        }/*else {
            // Variables
            String tester = currentDay + "\r\nExister X\n";

            try {
                FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
                out.write(tester);
                out.close();
            } catch (IOException e) {
                //Logger.logError(TAG, e);
            }
        }*/
    }

    // Read data from the file
    public void dataRead() {
        // Variables
        String line = "";
        BufferedReader in = null;
        int counter = 0;
        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), filename)));
            while ((line = in.readLine()) != null) {
                // Reading data
                if(counter < MEMORY_SPACE) {
                    dataRead[counter] = line;
                    counter++;
                }
            }
        } catch (FileNotFoundException e) {
            //Logger.logError(TAG, e);
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }

    // Write data to the file
    public void dataWrite() {
        // Variables
        String writer = "";
        for(int i = 0; i < MEMORY_SPACE; i++){
            writer += dataRead[i] + "\r\n";
        }

        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
            out.write(writer);
            out.close();
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }

    // Read data into the dataRead Array for purposes
    public void dataScan() {
        // Variables
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        BufferedReader in = null;

        int counter = 0;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), filename)));
            while ((line = in.readLine()) != null) {
                // Reading data
                if(counter < MEMORY_SPACE) {
                    dataRead[counter] = line;
                    counter++;
                }
                //Toast.makeText(this.context, line, Toast.LENGTH_SHORT).show();
            }

            // Testing Site
            String testOutput= "";
            for(int i = 0 ; i < MEMORY_SPACE; i++) {
                if((i > 0) && (i % 5 == 0)){
                    testOutput += dataRead[i] + "\n";
                } else {
                    testOutput += dataRead[i] + " ";
                }
            }
            Toast.makeText(context, testOutput, Toast.LENGTH_SHORT).show();
            // ----------------
        } catch (FileNotFoundException e) {
            //Logger.logError(TAG, e);
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }

    // Extract and arrange date (US)
    // yyyy_MM_dd -> Month Day, Year
    public String extractUSDate(int index){
        String arrangedDate = "";
        String day = dataRead[index].substring(8);
        String year = dataRead[index].substring(0,4);
        int monthTok = Integer.parseInt(dataRead[index].substring(5,7));
        Resources res = context.getResources();
        String[] monthTokens = res.getStringArray(R.array.month_tokens);
        String month = monthTokens[monthTok];
        arrangedDate = month + " " + day + ", " + year;
        return arrangedDate;
    }

    // Updates the Last Session Date
    // Note: Needs to call dataWrite after this function is called
    public void updateLastSessionDate(){
        dataRead[2] = dataRead[10];
        dataRead[10] = currentDay;
        dataWrite();
    }

    // Write Testing
    public void writeTest() {
        // Variables
        String tester = "TEST CALL 25\r\nTEST CALL 250\n";

        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), filename));
            out.write(tester);
            out.close();
        } catch (IOException e) {
            //Logger.logError(TAG, e);
        }
    }

    // Read Testing
    public void readTest() {
        // Variables
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), filename)));
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
                //Toast.makeText(this.context, line, Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            //Logger.logError(TAG, e);
            Toast.makeText(this.context, "File not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Logger.logError(TAG, e);
            Toast.makeText(this.context, "File error.", Toast.LENGTH_SHORT).show();
        }
    }

}
