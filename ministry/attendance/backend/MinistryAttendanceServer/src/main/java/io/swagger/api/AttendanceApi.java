/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.2).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.AttendeeSignIn;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-03-08T15:50:03.200Z")

@Api(value = "attendance", description = "the attendance API")
public interface AttendanceApi {

    @ApiOperation(value = "Registers member attendance", nickname = "memberRegister", notes = "Registers member attendance", response = AttendeeSignIn.class, tags={ "minister", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "attendee registered successfully", response = AttendeeSignIn.class),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/attendance",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<AttendeeSignIn> memberRegister(@ApiParam(value = "passing member and meeting details for attendee sign in" ,required=true )  @Valid @RequestBody AttendeeSignIn attendeeSignIn);

}