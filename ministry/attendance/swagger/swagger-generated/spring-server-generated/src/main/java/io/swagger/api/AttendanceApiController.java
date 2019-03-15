package io.swagger.api;

import io.swagger.model.AttendeeSignIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-08T15:50:03.200Z")

@Controller
public class AttendanceApiController implements AttendanceApi {

    private static final Logger log = LoggerFactory.getLogger(AttendanceApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AttendanceApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AttendeeSignIn> memberRegister(@ApiParam(value = "passing member and meeting details for attendee sign in" ,required=true )  @Valid @RequestBody AttendeeSignIn attendeeSignIn) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<AttendeeSignIn>(objectMapper.readValue("{  \"member\" : {    \"name\" : \"name\",    \"id\" : 0  },  \"meeting\" : {    \"date\" : \"date\",    \"name\" : \"name\",    \"id\" : 6,    \"time\" : \"time\"  }}", AttendeeSignIn.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<AttendeeSignIn>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<AttendeeSignIn>(HttpStatus.NOT_IMPLEMENTED);
    }

}
