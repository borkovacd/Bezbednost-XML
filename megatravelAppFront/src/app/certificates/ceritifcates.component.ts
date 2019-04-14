
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {SecurityService} from '../service/security.service';
import {UserService} from '../service/user.service';
import {CertificateBackModel} from '../model/certificateBack.model';

@Component ({

  templateUrl: './certificates.component.html',


})

export class CeritifcatesComponent implements OnInit {

  certif: CertificateBackModel[];
  selfCert: CertificateBackModel;
  user: any;

  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService, private  userService: UserService) {


  }
  ngOnInit() {

    this.user = this.userService.getLoggedUser();

   // alert(this.user.email);

    this.data.getCert(this.user.email).subscribe( data => this.certif = data);





  }
}
