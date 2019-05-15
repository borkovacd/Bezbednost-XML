
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CertificateModel} from '../model/certificate.model';
import {SecurityService} from '../service/security.service';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';
import {UserService} from '../service/user.service';

@Component ({
  templateUrl: './createCertificate.component.html',
})

export class CreateCertificateComponent implements OnInit {
  public form: FormGroup;
  public password: AbstractControl;
  public startDate: AbstractControl;
  public endDate: AbstractControl;
  public city: AbstractControl;

  subjects: SubjectSoftwareModel[];

  user: any;


  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService, private userService: UserService) {
    this.form = this.fb.group({

      'startDate': ['', Validators.compose([Validators.required])],
      'endDate': ['', Validators.compose([Validators.required])],
      'city': ['', Validators.compose([Validators.required])],

    });

    this.startDate = this.form.controls['startDate'];
    this.endDate = this.form.controls['endDate'];
    this.city = this.form.controls['city'];


  }
  ngOnInit() {

    this.data.getSubjectSoftware().subscribe(data => this.subjects = data);

    this.user = this.userService.getLoggedUser();

  }

  confirm(): any {
    const model = new CertificateModel(
      this.startDate.value,
      this.endDate.value,
      this.city.value);


    this.data.addCertificate(model, localStorage.getItem('loggedUser')).subscribe(dataF =>
      this.router.navigateByUrl('/certificates'));

  }
}
