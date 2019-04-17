
import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserModel} from '../model/user.model';
import {UserService} from '../service/user.service';


@Component ({
  templateUrl: './registration.component.html',
})

export class RegistrationComponent {

  public form: FormGroup;
  public username: AbstractControl;
  public password: AbstractControl;
  public rePassword: AbstractControl;
  public firstName: AbstractControl;
  public lastName: AbstractControl;
  public email: AbstractControl;
  public city: AbstractControl;

  constructor(protected router: Router,
              private fb: FormBuilder,
              private userService: UserService,
              private route: ActivatedRoute) {

    this.form = this.fb.group({
      'username': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],
      'rePassword': ['', Validators.compose([Validators.required])],
      'firstName': ['', Validators.compose([Validators.required])],
      'lastName': ['', Validators.compose([Validators.required])],
      'email': ['', Validators.compose([Validators.required])],
      'city': ['', Validators.compose([Validators.required])],
    });

    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];
    this.rePassword = this.form.controls['rePassword'];
    this.firstName = this.form.controls['firstName'];
    this.lastName = this.form.controls['lastName'];
    this.email = this.form.controls['email'];
    this.city = this.form.controls['city'];

  }

  validateRegData() {

    let error = false;
    let errorMessage = '';
    let errorMail = false;

    /* PROVERA LOZINKE */
    if (this.password.value.length < 8) {
      error = true;
      errorMessage = 'Password needs to be at least 8 characters long';
      return errorMessage;
      /*alert('Password needs to be at least 8 characters long');*/
    } else if (/\d/.test(this.password.value) == false) {
      error = true;
      errorMessage = 'Password needs to contain at least one number!';
      return errorMessage;
      /*alert('Password needs to contain at least one number!');*/
    } else if (!this.password.value.match('.*[A-Z].*')) {
      error = true;
      errorMessage = 'Password needs to contain at lease one uppercase letter!';
      return errorMessage;
      /*alert('Password needs to contain at lease one uppercase letter!');*/
    }

    /* PROVERA POKLAPANJA LOZINKI */
    if (this.password.value !== this.rePassword.value) {
      error = true;
      errorMessage = 'Lozinke se ne poklapaju!';
      return errorMessage;
    }

    /* PROVERA MEJLA */
    const patternMail = /\b[\w\.-]+@[\w\.-]+\.\w{2,4}\b/;
    if (!patternMail.test(this.email.value)) {
      error = true;
      errorMessage = 'Email adresa sadrzi nedozvoljene karaktere!';
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
      /*.replace(/\'/g, '&#39;')*/
      .replace(/\//g, '&#x2F;')
      .replace('src', 'drc')
      .replace(/\'/g, '&apos');

  }

  checkEmails(): any {

    this.userService.checkIfMailExists(this.email.value).subscribe(res => {
      if (res == true) {
        alert('Uneta je vec postojeca email adresa');
        return 'Error';
      } else {
        return 'Ok';
      }
    });
  }


  signUp() {

    const message = this.validateRegData();

    if (message == 'Ok') {

      /* PROVERA POSTOJANJA ISTOG MEJLA */
      this.checkEmails();

      const user = new UserModel(
        this.escapeCharacters(this.username.value),
        this.password.value,
        this.rePassword.value,
        this.escapeCharacters(this.firstName.value),
        this.escapeCharacters(this.lastName.value),
        this.escapeCharacters(this.email.value),
        this.escapeCharacters(this.city.value)
      );

      this.userService.registration(user).subscribe(data => {
        this.router.navigateByUrl('');
      });

    } else {
      alert(message);
    }


  }

  exit() {
    this.router.navigateByUrl('');
  }
}
