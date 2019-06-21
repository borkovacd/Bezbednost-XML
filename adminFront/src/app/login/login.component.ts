import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginModel} from '../model/login.model';
import {AdminService} from '../service/admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;
  public lm: LoginModel;

  constructor(protected router: Router,
              private fb: FormBuilder, private data: AdminService
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

      this.lm = new LoginModel(this.username.value, this.password.value);

     // alert(this.lm.email);
     // alert(this.lm.password);

      this.data.login(this.lm).subscribe(user => {

      localStorage.setItem('loggedUser', JSON.stringify(user.accessToken));
      console.log(localStorage);
      this.router.navigateByUrl('/home');
  });
  }

  exit() {
    this.router.navigateByUrl('');
  }
}
