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
      'firstName': ['', Validators.compose([Validators.required])],
      'lastName': ['', Validators.compose([Validators.required])],
      'username': ['', Validators.compose([Validators.required])],
      'address': ['', Validators.compose([Validators.required])],
      'mbr': ['', Validators.compose([Validators.required])],
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

  addNewAgent() {

    const agent = new AgentModel();

    agent.address = this.address.value;
    agent.username = this.username.value;
    agent.password = this.password.value;
    agent.mbr = this.mbr.value;
    agent.firstName = this.firstName.value;
    agent.lastName = this.lastName.value;

    this.adminService.addAgent(agent).subscribe( data =>
        this.router.navigateByUrl('/listAgents'));



  }
}
