import { Component, OnInit } from '@angular/core';
import {AbstractControl, Form, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AdminService} from '../service/admin.service';
import {AgentModel} from '../model/Agent.model';

@Component({
  selector: 'app-add-agent',
  templateUrl: './add-agent.component.html',
  styleUrls: ['./add-agent.component.css']
})
export class AddAgentComponent implements OnInit {

  form: FormGroup;
  firstName: AbstractControl;
  lastName: AbstractControl;
  username: AbstractControl;
  address: AbstractControl;
  mbr: AbstractControl;
  password: AbstractControl;
  rePassword: AbstractControl;

  constructor(protected router: Router, private fb: FormBuilder, private adminService: AdminService) {

    this.form = this.fb.group({
      'firstName': ['', Validators.compose([Validators.required, Validators.pattern('[A-Za-z]+$')])],
      'lastName': ['', Validators.compose([Validators.required, Validators.pattern('[A-Za-z]+$')])],
      'username': ['', Validators.compose([Validators.required, Validators.pattern('[A-Za-z0-9]+$')])],
      'address': ['', Validators.compose([Validators.required])],
      'mbr': ['', Validators.compose([Validators.required, Validators.pattern('[A-Za-z0-9]+$')])],
      'password': ['', Validators.compose([Validators.required])],
      'rePassword': ['', Validators.compose([Validators.required])],

    });

    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];
    this.firstName = this.form.controls['firstName'];
    this.lastName = this.form.controls['lastName'];
    this.address = this.form.controls['address'];
    this.mbr = this.form.controls['mbr'];
    this.rePassword = this.form.controls['rePassword'];

  }

  ngOnInit() {
  }

  validateRegData() {

    let error = false;
    let errorMessage = '';
    const errorMail = false;

    /* PROVERA LOZINKE */
    if (this.password.value.length < 8) {
      error = true;
      errorMessage = 'Lozinka mora biti barem 8 karaktera dugačka!';
      return errorMessage;
      /*alert('Password needs to be at least 8 characters long');*/
    } else if (/\d/.test(this.password.value) == false) {
      error = true;
      errorMessage = 'Lozinka mora sadržati barem jedan broj!';
      return errorMessage;
      /*alert('Password needs to contain at least one number!');*/
    } else if (!this.password.value.match('.*[A-Z].*')) {
      error = true;
      errorMessage = 'Lozinka mora posedovati barem jedno veliko slovo!';
      return errorMessage;
      /*alert('Password needs to contain at lease one uppercase letter!');*/
    }

    /* PROVERA POKLAPANJA LOZINKI */
    if (this.password.value !== this.rePassword.value) {
      error = true;
      errorMessage = 'Lozinke se ne poklapaju!';
      return errorMessage;
    }
    return 'Ok';

  }

  escapeCharacters(value: string): string {
    return value
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/\"/g, '&quot;')
      .replace(/\//g, '&#x2F;')
      .replace('src', 'drc')
      .replace(/\'/g, '&apos');

  }

  addNewAgent()
  {
    const message = this.validateRegData();
    const agent = new AgentModel();

    if (message == 'Ok')
    {
      agent.address = this.address.value;
      agent.mbr = this.mbr.value;
      agent.firstName = this.escapeCharacters(this.firstName.value);
      agent.username = this.escapeCharacters(this.username.value);
      agent.lastName = this.escapeCharacters(this.lastName.value);
      agent.password = this.password.value;

      this.adminService.addAgent(agent).subscribe(data => {
        this.router.navigateByUrl('/listAgents');
      });
    } else {
      alert(message);
    }

  }

  exit() {
  this.router.navigateByUrl('');
  }
}
