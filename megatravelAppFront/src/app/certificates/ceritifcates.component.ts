
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {SecurityService} from '../service/security.service';

@Component ({

  templateUrl: './certificates.component.html',


})

export class CeritifcatesComponent implements OnInit {


  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService) {


  }
  ngOnInit() {



  }
}
