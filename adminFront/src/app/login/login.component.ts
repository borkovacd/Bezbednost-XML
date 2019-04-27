import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;

  constructor(protected router: Router,
              private fb: FormBuilder,
  ) {
    this.form = this.fb.group({
      'username': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],

    });
    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];


  }
  ngOnInit() {

  }

  logIn() {

    // gadjati funkciju na backu za logovanje

  }

  exit() {
    this.router.navigateByUrl('');
  }
}
