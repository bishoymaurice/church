/**
 * Ministry Attendance API
 * This API is created as part of ministry attendance program
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { Meeting } from './meeting';
import { Member } from './member';


export interface AttendeeSignIn { 
    member?: Member;
    meeting?: Meeting;
}
