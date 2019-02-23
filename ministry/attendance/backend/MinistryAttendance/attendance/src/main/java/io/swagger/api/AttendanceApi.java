/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.0).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-22T11:09:01.088Z")

@Api(value = "attendance", description = "the attendance API")
public interface AttendanceApi {

    @ApiOperation(value = "adds member attendance", nickname = "addAttandance", notes = "adds member attendance", tags={ "user", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "action done successfully"),
        @ApiResponse(code = 400, message = "bad input parameter") })
    @RequestMapping(value = "/attendance",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Void> addAttandance(@NotNull @ApiParam(value = "passing member ID to be marked as an attendee", required = true) @Valid @RequestParam(value = "memberId", required = true) String memberId);

}
