package cdvis.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class InstrumentDict {
    public static Map<Integer, String> MidiInstrument;

    public static void loadInstrumentData() {
        if (MidiInstrument != null) return;

        MidiInstrument = new HashMap<>();

        String csvFilePath = "resources/GM_Instrument_list.csv";
        try (Reader reader = new FileReader(csvFilePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                int number = Integer.parseInt(csvRecord.get("Number"));
                String instrument = csvRecord.get("Instrument");

                MidiInstrument.put(number, instrument);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
