
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CertificateModel} from '../model/certificate.model';
import {SecurityService} from '../service/security.service';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';

@Component ({

  templateUrl: './createCertificate.component.html',


})

export class CreateCertificateComponent implements OnInit{
  public form: FormGroup;
  public name: AbstractControl;
  public state: AbstractControl;
  public email: AbstractControl;
  public password: AbstractControl;
  public startDate: AbstractControl;
  public endDate: AbstractControl;
  public subjectDate: AbstractControl;

  subjects: SubjectSoftwareModel[];


  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService) {
    this.form = this.fb.group({


      'name': ['', Validators.compose([Validators.required])],
      'state': ['', Validators.compose([Validators.required])],
      'email': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required])],
      'startDate': ['', Validators.compose([Validators.required])],
      'endDate': ['', Validators.compose([Validators.required])],
      'subjectDate': ['', Validators.compose([Validators.required])],

    })


  }
  ngOnInit() {

    this.data.getSubjectSoftware().subscribe(data => this.subjects = data);


  }

  confirm(){
    const model = new CertificateModel(this.name.value,
      this.state.value,
      this.email.value,
      this.password.value,
      this.startDate.value,
      this.endDate.value,
      this.subjectDate.value);

  }
}
