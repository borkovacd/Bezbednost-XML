import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccomodationService} from '../service/accomodation.service';
import {AgentService} from '../service/agent.service';

@Component({
  selector: 'welcomepage',
  templateUrl: './welcomepage.component.html',
  styleUrls: ['./welcomepage.component.scss']


})

export class WelcomepageComponent implements OnInit{

  accomodation = false;
  communicate = false;
  reservations = false;
  message = false;

  public form: FormGroup;
  public messageToCentral: AbstractControl;
  public returnMessage: string;
  constructor(protected router: Router, private fb: FormBuilder, private data: AgentService) {
    this.form = this.fb.group({
      'messageToCentral': ['', Validators.compose([Validators.required])],

    })
    this.messageToCentral = this.form.controls['messageToCentral'];

  }
  ngOnInit() {

      this.accomodation = true;
      this.communicate = false;
    this.reservations = false;
    this.message = false;
  }
  accomodationButton() {
    this.accomodation = true;
    this.communicate = false;
    this.reservations = false;
    this.message = false;



  }

  reservation() {
    this.accomodation = false;
    this.communicate = false;
    this.reservations = true;
    this.message = false;


  }
  messages(){
    this.accomodation = false;
    this.communicate = false;
    this.reservations = false;
    this.message = true;
  }

  logOut() {
    this.data.logout().subscribe(data => {

      localStorage.clear();
      this.router.navigateByUrl('');
    });

  }
  communicateC() {
    this.communicate = true;
    this.accomodation = false;
    this.reservations = false;

  }

  sendToCentral(): any {
    this.data.sendMessageToCentral(this.messageToCentral.value.toString()).subscribe(data => {
      alert(data);
    });
  }
}
