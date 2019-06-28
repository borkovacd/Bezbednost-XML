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

  vis: boolean = false;

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

  validateLogData() {

    let error = false;
    let errorMessage = '';

    /* PROVERA MEJLA */
    const patternMail = /\b[\w\.-]+@[\w\.-]+\.\w{2,4}\b/;
    if (!patternMail.test(this.username.value)) {
      error = true;
      errorMessage = 'Email adresa sadrzi nedozvoljene karaktere!';
      return errorMessage;
    }

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

  logIn() {
    let message = this.validateLogData();

    if (message == "Ok") {
      const object = new LoginModel(this.escapeCharacters(this.username.value), this.password.value,);
      this.data.login(object).subscribe(user => {
          if (user.accessToken == null) {
            alert('Ne postoji korisnik sa unetim kredencijalima!');
            return;
          }


          localStorage.setItem('loggedUser', JSON.stringify(user.accessToken));
          console.log(localStorage);

          this.router.navigateByUrl('/home');

        },
        error => alert(error.error)
      );


    } else {
      alert(message);
    }

  }

  exit() {
    this.router.navigateByUrl('');
  }
}
