package net.bartosiak.receiver;

import net.bartosiak.exception.CommandReceiverException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class FetchAndConvertCityReceiver implements IFetchCityReceiver {
    private static final Logger logger = Logger.getLogger(FetchAndConvertCityReceiver.class.getName());

    private final static String geoApiendpoint;
    private final static String outputFileName;
    private final static String fieldsFormat;
    private final static String CSVseparator;
    private final static String CSVnullValues;
    private final static String [] fieldsArray;

    static {
        Properties prop = new Properties();
        try {
            logger.info("Loading default configuration from 'config.properties'.");
            InputStream inputStream = new FileInputStream("config.properties");
            prop.load(inputStream);
        } catch (java.io.IOException e) {
            logger.warning(e.getMessage());
        }
        geoApiendpoint = prop.getProperty("goeuro.config.api.position.suggest.url", "http://api.goeuro.com/api/v2/position/suggest/en/{CITY_NAME}");
        outputFileName = prop.getProperty("goeuro.config.out.filename", "export.csv");
        fieldsFormat = prop.getProperty("goeuro.config.out.csv.format", "_id;key;name;fullName;iata_airport_code;type;country;geo_position/latitude;geo_position/longitude;locationId;inEurope;countryCode;coreCountry;distance");

        CSVnullValues = prop.getProperty("goeuro.config.out.csv.null", "NULL");
        CSVseparator = prop.getProperty("goeuro.config.out.csv.separator", ";");

        fieldsArray = fieldsFormat.split(";");
        logger.info("Geo API Endpoint: " + geoApiendpoint);
    }

    /**
     * Fetching result of API call and passing it CSV printer
     *
     * @param cityName name of City to fetch data
     * @throws CommandReceiverException
     */
    public void fetchCity(String cityName) throws CommandReceiverException {
        String url = geoApiendpoint.replace("{CITY_NAME}", cityName);
        logger.info("calling: " + url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    try {
                        FileWriter fileWriter = new FileWriter(outputFileName);
                        CSVPrinter csvPrinter = new CSVPrinter(
                                fileWriter,
                                CSVFormat.DEFAULT
                                        .withHeader(fieldsArray)
                                        .withNullString(CSVnullValues)
                                        .withDelimiter(CSVseparator.charAt(0))
                                        .withQuote('"')
                                        .withEscape('\\')
                        );
                        try{
                            handleData(inputStream, csvPrinter);
                        }finally {
                            csvPrinter.flush();
                            csvPrinter.close();
                            fileWriter.close();
                        }
                    }finally {
                        inputStream.close();
                    }
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new CommandReceiverException(e);
        }
    }

    /**
     * Method reads all input data from Stream and creates JSON array of elements
     * @param inputStream response from API server
     * @param csvPrinter  CSV file printer
     * @throws IOException
     */
    private void handleData(InputStream inputStream, CSVPrinter csvPrinter) throws IOException {
        String data = IOUtils.toString(inputStream);
        JSONArray array = new JSONArray(data);
        for (Object anArray : array) {
            JSONObject obj = (JSONObject) anArray;
            this.handleRecord(obj, csvPrinter);
        }
    }

    /**
     * Method received JSON object which is basically Record to write, and trying to extract values from configuration
     * @param obj JSON record
     * @param csvPrinter CSVprinter
     * @throws IOException
     */
    private void handleRecord(JSONObject obj, CSVPrinter csvPrinter) throws IOException {
        List<Object> jsonList = new LinkedList<Object>();
        for (String field : fieldsArray) {
            Object value;
            try {
                value = this.getByPath(obj, field);
            } catch (JSONException e) {
                value = JSONObject.NULL;
                logger.warning("Field: '"+field+"' Exception:'" + e.getMessage() + "'");
            }
            if (value == JSONObject.NULL) {
                value = null;
            }
            jsonList.add(value);
        }
        csvPrinter.printRecord(jsonList);
    }

    /**
     * Method is able to search JSON object and extract data by given path
     * @param obj JSON object to parse
     * @param field JSON Path
     * @return String, Integer.. or JSON object
     */
    private Object getByPath(JSONObject obj, String field) {
        if (field.indexOf('/') > 0) {
            // we got path so lets extract it

            // lets extract path and field
            String [] fields = field.split("/");
            field = fields[Math.max(fields.length-1, 0)];
            for(String elem: fields){
                if (elem.equals(field)) {
                    break;  // this is last elem not path part
                }
                obj = obj.getJSONObject(elem);
            }
        }
        return obj.get(field);
    }
}
