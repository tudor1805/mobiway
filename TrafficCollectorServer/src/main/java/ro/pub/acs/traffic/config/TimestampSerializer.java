package ro.pub.acs.traffic.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TimestampSerializer extends JsonSerializer<Date> {
	
    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws 
        IOException, JsonProcessingException {      
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(value);

        jsonGenerator.writeString(formattedDate);
    }

}