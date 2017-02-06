package thoang.thoang_sizebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class main extends Activity {

    /* All TextView objects */
    private TextView heading;
    private TextView prompt;
    private TextView fieldPrompt;
    private TextView sizePrompt;
    private TextView detailHead;

    /* All EditText objects */
    private EditText bodyText;
    private EditText size;

    /* ALl button object */
    private Button addButton;
    private Button displayButton;
    private Button deleteButton;
    private Button edit;
    private Button backButton;

    /* Other View objects */
    private Spinner field;
    private ViewFlipper viewFlipper;
    private ListView recordList;
    private ListView details;

    /* Adaptors, Lists that use the adaptors */
    private ArrayList<Person> records;
    private ArrayAdapter<Person> adapter;
    private ArrayList<DetailedPerson> detailedPeople;
    private ArrayAdapter<DetailedPerson> dAdapter;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    /* Other variables */
    private static final String FILENAME = "file.sav";

    /**
     * Called when the activity is first created.
     * Creates all the views (UI).
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);

        /* Create TextViews */
        heading = (TextView) findViewById(R.id.heading);
        prompt = (TextView) findViewById(R.id.prompt);
        fieldPrompt = (TextView) findViewById(R.id.fieldPrompt);
        sizePrompt = (TextView) findViewById(R.id.sizePrompt);
        detailHead = (TextView) findViewById(R.id.detailHead);

        /* Create the EditText views */
        bodyText = (EditText) findViewById(R.id.body);
        size = (EditText) findViewById(R.id.size);

        /* Create buttons */
        addButton = (Button) findViewById(R.id.Add);
        displayButton = (Button) findViewById(R.id.Display);
        deleteButton = (Button) findViewById(R.id.Delete);
        edit = (Button) findViewById(R.id.Edit);
        backButton = (Button) findViewById(R.id.Back);

        /* Create the other View objects */
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        field = (Spinner) findViewById(R.id.field);
        recordList = (ListView) findViewById(R.id.recordList);
        details = (ListView) findViewById(R.id.details);

        /* Create the lists to store the data */
        records = new ArrayList<Person>();
        detailedPeople = new ArrayList<DetailedPerson>();

        /* Set the listeners for the buttons */
        addButton.setOnClickListener(buttonListener);
        displayButton.setOnClickListener(buttonListener);
        deleteButton.setOnClickListener(buttonListener);
        edit.setOnClickListener(editRecord);
        backButton.setOnClickListener(buttonListener);
    }

    /**
     * Called when activity is started
     * Sets up adaptors, and loads in stored data
     */
    @Override
    protected void onStart() {
        super.onStart();

        /* Create the adaptors */
        adapter = new ArrayAdapter<Person>(this, R.layout.list, records);
        dAdapter = new ArrayAdapter<DetailedPerson>(this,
                R.layout.list, detailedPeople);
        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.dataFields, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        /* Set adaptors */
        recordList.setAdapter(adapter);
        details.setAdapter(dAdapter);
        field.setAdapter(spinnerAdapter);

        /* Load previously save records */
        loadFromFile();
    }

    /**
     * Waits until a button is pressed.
     * Specifically waits for Add, Delete, Edit and Back
     */
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View v) {
            setResult(RESULT_OK);

            /* Variables */
            String name = bodyText.getText().toString();
            name = trimSpaces(name);
            Person human = new Person(name);

            /* Adds a record */
            if(v == addButton) {
                records.add(human);
                saveToFile();
                /* Displays more information about a record */
            } else if(v == displayButton) {
                viewFlipper.showNext();
                human = findPerson(name);
                dAdapter.clear();
                dAdapter.notifyDataSetChanged();
                detailedPeople.add(copy(human));
                dAdapter.notifyDataSetChanged();

                /* Deletes a record */
            } else if(v == deleteButton) {
                if(!records.isEmpty()) {
                    records.remove(human);
                    adapter.remove(human);
                    saveToFile();
                    loadFromFile();
                }

                /* Returns to previous screen */
            } else if(v == backButton) {
                viewFlipper.setDisplayedChild(
                        viewFlipper.indexOfChild(findViewById(R.id.mainLayout)));
                loadFromFile();
            }

            adapter.notifyDataSetChanged();
        }
    };

    /**
     * Waits for edit button to be pressed
     *
     * Updates/ Edits a single field of a record.
     *
     * Gets the field to be updated from a spinner,
     *  as well as the amount or text from an EditView.
     */
    private View.OnClickListener editRecord = new View.OnClickListener() {
        public void onClick(View v) {
            setResult(RESULT_OK);

            /* Get input */
            String name = bodyText.getText().toString();
            String dimension = field.getSelectedItem().toString();
            String input = size.getText().toString();

            /* Get rid of extra spaces */
            name = trimSpaces(name);
            dimension = trimSpaces(dimension);

            /* Input should be a double. If not, then it is a comment or date */
            try {
                //Idea from http://stackoverflow.com/questions/23539713/how-to-get-double-from-edittext-the-right-way
                double sizeInput = Double.parseDouble(input);

                edit(name, dimension, sizeInput);

                /* Input is date or comment */
            } catch (Exception NotDouble) {
                String[] date = input.split("-");

                // Date has 3 fields, yyyy-mm-dd
                if(date.length == 3) {
                    editDate(input, name, dimension);
                } else {
                    editComment(input, name, dimension);
                }
            }

            adapter.notifyDataSetChanged();
            saveToFile();
        }
    };

    /**
     * Searches through all records to find a person specified by the name
     * @param name
     * @return A person that exsists inside the record
     */
    private Person findPerson(String name) {
        Person person = new Person(name);

        for(int i = 0; i < records.size(); i++) {
            if(records.get(i).getName().equalsIgnoreCase(name)) {
                person = records.get(i);
                records.remove(i);
                break;
            }
        }

        return(person);
    }

    /**
     * Edits to a person made according to field
     *
     * @param personName
     * @param dim the field that is being edited
     * @param input the numerical data to go into the field
     */
    private void edit(String personName, String dim, double input) {
        Person human = findPerson(personName);

        if(dim.equalsIgnoreCase("Neck")) {
            human.setNeck(input);
        } else if(dim.equalsIgnoreCase("Bust")) {
            human.setBust(input);
        } else if(dim.equalsIgnoreCase("Chest")) {
            human.setChest(input);
        } else if(dim.equalsIgnoreCase("Waist")) {
            human.setWaist(input);
        } else if(dim.equalsIgnoreCase("Hip")) {
            human.setHip(input);
        } else if(dim.equalsIgnoreCase("Inseam")) {
            human.setInseam(input);
        }

        records.add(human);
    }

    /**
     * Edits a person's date
     *
     * @param date input from user
     * @param personName
     * @param dim the field selected from the spinner
     */
    private void editDate(String date, String personName, String dim) {
        /* Wrong field selected */
        if(!dim.equalsIgnoreCase("Date")) {
            System.out.println("Not a date!");
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //Format
        Person human = findPerson(personName);

        try {
            Date newDate = format.parse(date);

            human.setDate(newDate);
            records.add(human);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits a person's comment
     *
     * @param comment The text from user input
     * @param personName
     * @param dim The field selected from the spinner
     */
    private void editComment(String comment, String personName, String dim) {
        if (!dim.equalsIgnoreCase("Comment")) {
            return;
        }

        Person human = findPerson(personName);

        human.setComment(comment);
        records.add(human);
    }

    /**
     * Creates a DetailedPerson object by copying values of a Person object
     *
     * @param person The object to copy from
     * @return a DetailedPerson
     */
    private DetailedPerson copy(Person person) {
        DetailedPerson detail = new DetailedPerson(person.getName());

        detail.setDate(person.getDate());
        detail.setNeck(person.getNeck());
        detail.setBust(person.getBust());
        detail.setChest(person.getChest());
        detail.setWaist(person.getWaist());
        detail.setHip(person.getHip());
        detail.setInseam(person.getInseam());
        detail.setComment(person.getComment());

        return detail;
    }

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

    /**
     * Loads all records that were stored in a file
     */
    private void loadFromFile() {
        records.clear();
        adapter.notifyDataSetChanged();
        Person savedHuman;
        String[] splitLine;

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();

            Person person = new Person("temp");

            while (line != null) {
                line = in.readLine();

                if(line == null) {
                    records.remove(person);
                    continue;
                }

                splitLine = line.split(":");

                if(splitLine[0].equalsIgnoreCase("name")) {
                    person.setName(splitLine[1]);
                } else if(splitLine[0].equalsIgnoreCase("date")) {
                    editDate(splitLine[1], person.getName(), splitLine[0]);
                } else if(splitLine[0].equalsIgnoreCase("comment")) {
                    editComment(splitLine[1], person.getName(), splitLine[0]);
                    records.add(person);
                    adapter.notifyDataSetChanged();
                    person = new Person("temp");
                } else {
                    try {
                        //Idea from http://stackoverflow.com/questions/23539713/how-to-get-double-from-edittext-the-right-way
                        double sizeInput = Double.parseDouble(splitLine[1]);

                        edit(person.getName(), splitLine[0], sizeInput);
                    } catch(Exception NotDouble) {}
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves records to a file
     */
    private void saveToFile() {
        String store;
        DetailedPerson dPerson;

        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_APPEND);

            /* Save all records */
            for(int i = 0; i < records.size(); i++) {
                dPerson = copy(records.get(i));
                store = dPerson.toString() + "\n";
                fos.write(store.getBytes());
            }

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
