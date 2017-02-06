package thoang.thoang_sizebook;

import java.util.ArrayList;

/**
 * Created by Willi_000 on 2017-02-05.
 */

public class Record {
    private ArrayList<Person> records;

    public Record() {
        this.records = new ArrayList<Person>();
    }

    public void addRecord(Person individual) {
        this.records.add(individual);
    }

    public void removeRecord(Person individual) {
        this.records.remove(individual);
    }

    public void display(Person individual) {
        String recordName;
        int index = -1;

        if(this.records.isEmpty()) {
            System.out.println("Empty");
            return;
        }

        do{
            index += 1;
            recordName = this.records.get(index).getName();
        } while(recordName != individual.getName());

        System.out.println(index + recordName);

    }
}
