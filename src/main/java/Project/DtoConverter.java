package Project;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by user on 01.03.2018.
 */
public class DtoConverter extends JsonSerializer<CompositeTaskDTO>{

    @Override
    public void serialize(CompositeTaskDTO compositeTaskDTO, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(compositeTaskDTO.toString());
    }
}
