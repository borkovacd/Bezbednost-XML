
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {LogInModel} from '../model/logIn.model';
import {AgentService} from '../service/agent.service';
import { Observable, Subscription } from 'rxjs';

@Component ({
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})

export class LogInComponent implements OnInit {
  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;
  subscription: Subscription
  constructor(protected  router: Router,
              private fb: FormBuilder,
              private agentService: AgentService){
    this.form = this.fb.group({
      'username': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],

    })
    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];

  }
  ngOnInit() {
    this.subscription = this.agentService.getAllAgents().subscribe(data => {

    })
  }

  validateLogData() {
    return 'Ok';
  }

  escapeCharacters(value: string): string{
    return value
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/\"/g, '&quot;')
      /*.replace(/\'/g, '&#39;')*/
      .replace(/\//g, '&#x2F;')
      .replace('src', 'drc')
      .replace(/\'/g, '&apos')

  }


  confirmClick() {

    let message = this.validateLogData();

    if (message == "Ok") {
      const model = new LogInModel(
        this.escapeCharacters(this.username.value),
        this.password.value,
      );
      this.agentService.logIn(model).subscribe(data => {
        if (data == null) {
          alert('Ne postoji agent sa unetim kredencijalima!');
        } else {
          localStorage.setItem('agentId', data.accessToken);
          this.router.navigateByUrl('/welcomepage');
        }
      })
    } else {
      alert(message);
    }
  }
}
