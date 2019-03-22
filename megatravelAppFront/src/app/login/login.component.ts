import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LoginModel} from '../model/login.model';
import {UserService} from '../service/user.service';

@Component({
  templateUrl: './login.component.html',
})

export class LoginComponent implements OnInit{

  public object: LoginModel;
  public form: FormGroup;
  public email: AbstractControl;
  public password: AbstractControl;

  constructor(protected router: Router,
              private fb: FormBuilder,
              private accountService: UserService,
  ) {
    this.form = this.fb.group({
      'email': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],

    });
    this.email = this.form.controls['email'];
    this.password = this.form.controls['password'];


  }
  ngOnInit() {

  }

  logIn() {
    const object = new LoginModel(this.email.value,
      this.password.value,);
    this.accountService.login(object).subscribe(user => {
      if(user == null){
        alert('Bad email or password!');
        return
      }


      localStorage.setItem('loggedUser', JSON.stringify(user));
      console.log(localStorage);
      this.router.navigateByUrl('home');


    });

  }

  exit() {
    this.router.navigateByUrl('');
  }
}
