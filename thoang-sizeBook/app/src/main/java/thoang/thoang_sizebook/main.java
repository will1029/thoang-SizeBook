package thoang.thoang_sizebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class main extends Activity {

    /* All TextView objects */
    private TextView heading;
    private TextView prompt;
    private TextView fieldPrompt;
    private TextView sizePrompt;
    private TextView detailHead;

    /* All EditText objects */
    private EditText bodyText;
    private EditText field;
    private EditText size;

    private Button addButton;
    private Button displayButton;
    private Button deleteButton;
    private Button edit;
    private Button backButton;

    private ViewFlipper viewFlipper;
    private ListView recordList;
    private ListView details;

    private ArrayList<Person> records;
    private ArrayAdapter<Person> adapter;
    private ArrayList<detailedPerson> detailedPeople;
    private ArrayAdapter<detailedPerson> dAdapter;

    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);

        heading = (TextView) findViewById(R.id.heading);
        prompt = (TextView) findViewById(R.id.prompt);
        fieldPrompt = (TextView) findViewById(R.id.fieldPrompt);
        sizePrompt = (TextView) findViewById(R.id.sizePrompt);
        detailHead = (TextView) findViewById(R.id.detailHead);

        bodyText = (EditText) findViewById(R.id.body);
        field = (EditText) findViewById(R.id.field);
        size = (EditText) findViewById(R.id.size);


        records = new ArrayList<Person>();
        detailedPeople = new ArrayList<detailedPerson>();

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        recordList = (ListView) findViewById(R.id.recordList);
        details = (ListView) findViewById(R.id.details);

        addButton = (Button) findViewById(R.id.Add);
        displayButton = (Button) findViewById(R.id.Display);
        deleteButton = (Button) findViewById(R.id.Delete);
        edit = (Button) findViewById(R.id.Edit);
        backButton = (Button) findViewById(R.id.Back);

        addButton.setOnClickListener(buttonListener);
        displayButton.setOnClickListener(buttonListener);
        deleteButton.setOnClickListener(buttonListener);
        edit.setOnClickListener(editRecord);
        backButton.setOnClickListener(buttonListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //loadFromFile();
        adapter = new ArrayAdapter<Person>(this, R.layout.list, records);
        dAdapter = new ArrayAdapter<detailedPerson>(this, R.layout.list, detailedPeople);

        recordList.setAdapter(adapter);
        details.setAdapter(dAdapter);
    }


/*
    TODO
    -Save and load on file
    -Display all (and count)
    -Edit fields of records
        -> Comment
        -> Limits of fields
    -Interface controls (Rasio Buttons?)
    -Fix delete

    -Documentation
    -Video
    -UML
 */






    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View v) {
            setResult(RESULT_OK);

            String name = bodyText.getText().toString();
            name = trimSpaces(name);
            Person human = new Person(name);

            if(v == addButton) {
                records.add(human);
            } else if(v == displayButton) {
                viewFlipper.showNext();
                for(int i = 0; i < records.size(); i++) {
                    if(records.get(i).getName().equalsIgnoreCase(name)) {
                        detailedPerson dhuman = new detailedPerson(name);
                        dAdapter.clear();
                        dAdapter.notifyDataSetChanged();
                        detailedPeople.add(dhuman);
                        dAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (dAdapter.isEmpty()) {
                    //TODO Add prompt?
                }
            } else if(v == deleteButton) {
                if(!records.isEmpty()) {
                    records.remove(human);
                    adapter.remove(human);
                    System.out.println("Deleted:" + human);
                }
            } else if(v == backButton) {
                viewFlipper.setDisplayedChild(
                        viewFlipper.indexOfChild(findViewById(R.id.mainLayout)));
                dAdapter.clear();
                dAdapter.notifyDataSetChanged();
            }

            adapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener editRecord = new View.OnClickListener() {
        public void onClick(View v) {
            setResult(RESULT_OK);

            /* Get input */
            String name = bodyText.getText().toString();
            String dimension = field.getText().toString();
            float sizeInput = size.getInputType();

            name = trimSpaces(name);
            dimension = trimSpaces(dimension);
            Person human = new Person(name);


            adapter.notifyDataSetChanged();
        }
    };

    /**
     * Removes unneeded spaces
     *
     * @param inputString input string
     * @return string with extra spaces removed
     */
    private String trimSpaces(String inputString) {
        inputString = inputString.replaceAll("\\s+", " ");
        return inputString;
    }
}
