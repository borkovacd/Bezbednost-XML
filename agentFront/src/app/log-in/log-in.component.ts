
import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {LogInModel} from '../model/logIn.model';
import {AgentService} from '../service/agent.service';

@Component ({
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})

export class LogInComponent {
  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;
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
  confirmClick() {
    const model = new LogInModel(
      this.username.value,
      this.password.value,
    );
    this.agentService.logIn(model).subscribe(data => {
      this.router.navigateByUrl('/welcomepage');

    })
  }
}
