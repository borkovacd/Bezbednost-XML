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

  validateLogData() {

    let error = false;
    let errorMessage = '';

    /* PROVERA MEJLA */
    const patternMail = /\b[\w\.-]+@[\w\.-]+\.\w{2,4}\b/;
    if (!patternMail.test(this.email.value)) {
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
      const object = new LoginModel(this.escapeCharacters(this.email.value), this.password.value,);
      this.accountService.login(object).subscribe(user => {
        if (user == null) {
          alert('Ne postoji korisnik sa unetim kredencijalima!');
          return
        } else {
        }

        localStorage.setItem('loggedUser', JSON.stringify(user));
        console.log(localStorage);
        if (this.email.value === 'MTRoot@gmail.com')
          this.router.navigateByUrl('home');
        else {
          this.router.navigateByUrl('software');
        }

      });

    } else {
      alert(message);
    }
  }

  exit() {
    this.router.navigateByUrl('');
  }
}
