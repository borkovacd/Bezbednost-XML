
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
  public us: UserModel = new UserModel();

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
    });

    this.username = this.form.controls['username'];
    this.password = this.form.controls['password'];
    this.rePassword = this.form.controls['rePassword'];
    this.firstName = this.form.controls['firstName'];
    this.lastName = this.form.controls['lastName'];
    this.email = this.form.controls['email'];

  }

  signUp() {

      this.us.firstName = this.firstName.value;
      this.us.username = this.username.value;
      this.us.lastName = this.lastName.value;
      this.us.password = this.password.value;
      this.us.rePassword = this.rePassword.value;
      this.us.email = this.email.value;

      this.userService.registration(this.us).subscribe(data => {
        this.router.navigateByUrl('');
      });



  }

  exit() {
    this.router.navigateByUrl('');
  }
}
