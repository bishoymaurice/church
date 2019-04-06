import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MinisterService } from 'src/swagger/api/minister.service';
import { AttendeeSignIn } from 'src/swagger/model/attendeeSignIn';
import { Member } from 'src/swagger/model/member';
import { Meeting } from 'src/swagger/model/meeting';

@Component({
  selector: 'app-member-register',
  templateUrl: './member-register.component.html',
  styleUrls: ['./member-register.component.css']
})
export class MemberRegisterComponent implements OnInit {

  HTTP_STATUS_PRECONDITION_FAILED: number = 412;
  HTTP_STATUS_NOT_FOUND: number = 404;

  attendeeSignIn: AttendeeSignIn = {};

  greenMessage: string = "";
  redMessage: string = "";
  meetingNotFoundMsg: string = "";
  memberNotFound: string = "";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private ministerService: MinisterService
  ) { }

  ngOnInit() {
    this.memberRegister();
  }

  private memberRegister(): void {

    this.greenMessage = "";
    this.redMessage = "";
    this.memberNotFound = "";
    this.meetingNotFoundMsg = "";

    let id = this.route.snapshot.paramMap.get('id');

    if (id != undefined && id != null && id.length > 0) {

      console.info("Received member ID is:", id);

      let member: Member = {};
      let meeting: Meeting = {};

      member.id = Number(id);

      this.attendeeSignIn.member = member;
      this.attendeeSignIn.meeting = meeting;

      this.ministerService.memberRegister(this.attendeeSignIn).subscribe(
        data => {
          console.info('Success data:', data);
          this.attendeeSignIn = data;
          this.greenMessage = this.attendeeSignIn.member.name + " registered successfully into: " + this.attendeeSignIn.meeting.name;
        },
        error => {
          console.info('Error message:', error);

          if (error.status == this.HTTP_STATUS_NOT_FOUND) {
            this.memberNotFound = "ID: " + this.attendeeSignIn.member.id + " not found!";

          }

          else if (error.status == this.HTTP_STATUS_PRECONDITION_FAILED) {
            this.meetingNotFoundMsg = "No meeting found at the time being!";
          }

          else {
            this.redMessage = "Failed to register ID: " + this.attendeeSignIn.member.id;
          }
        }
      );
    }
  }
}
