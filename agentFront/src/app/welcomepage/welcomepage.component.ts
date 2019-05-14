import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccomodationService} from '../service/accomodation.service';
import {AgentService} from '../service/agent.service';

@Component({
  selector: 'welcomepage',
  templateUrl: './welcomepage.component.html',

})

export class WelcomepageComponent implements OnInit{

  accomodation = false;
  public form: FormGroup;
  public messageToCentral: AbstractControl;
  public returnMessage: string;
  constructor(protected router: Router, private fb: FormBuilder, private data: AgentService) {
    this.form = this.fb.group({
      'messageToCentral': ['', Validators.compose([Validators.required])],

    })
    this.messageToCentral = this.form.controls['messageToCentral'];

  }
  ngOnInit(){

  }
  accomodationButton(){
    this.accomodation = true;


  }
  message(){

  }
  reservation() {

  }

  sendToCentral(): any {
    this.data.sendMessageToCentral(this.messageToCentral.value.toString()).subscribe(data => {
      alert(data);
    });
  }
}
