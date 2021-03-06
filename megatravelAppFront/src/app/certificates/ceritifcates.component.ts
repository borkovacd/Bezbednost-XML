
import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {SecurityService} from '../service/security.service';
import {UserService} from '../service/user.service';
import {CertificateBackModel} from '../model/certificateBack.model';
import {nextTick} from "q";

@Component ({

  templateUrl: './certificates.component.html',


})

export class CeritifcatesComponent implements OnInit {

  certif: CertificateBackModel[];
  myCert: CertificateBackModel;
  selfCert: CertificateBackModel;
  user: any;
  email: any;
  visibleMessage: boolean;
  public form: FormGroup;
  public message: AbstractControl;
  serialN: any;
  vis: boolean = false;

  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService, private  userService: UserService,) {
    this.form = this.fb.group({
      'message': ['', Validators.compose([Validators.required])],
    });
    this.message = this.form.controls['message'];


  }
  ngOnInit() {

    this.userService.getLoggedUser().subscribe(data => {

      if (data == true) {
        this.vis = true;
      } else {
        this.vis = false;
      }


    });


    // alert(this.user.email);

    this.data.getCert(localStorage.getItem('loggedUser')).subscribe( data => this.certif = data);

    this.data.getMyCert(localStorage.getItem('loggedUser')).subscribe(data => this.myCert = data)

   // window.location.reload();

    this.visibleMessage=false;

  }
  revoke(serialNumber: any){

    this.visibleMessage=true;
    this.serialN = serialNumber;
  }
  potvrdiPovlacenje(serialNumber: any){

    this.data.revokeCert(serialNumber,this.message.value).subscribe( data =>{
      if( data == true){
        alert('Sertifikat uspesno povucen');
        this.router.navigateByUrl('home');
      }

    })
  }

  getStatus(isRevoked: boolean) {
    if  (isRevoked == true) {
      return 'Povucen';
    } else {
      return 'Aktivan';
    }
  }

  listAll() {
    this.router.navigateByUrl('allCertificates');
  }

  back() {
    this.router.navigateByUrl('/home');
  }
}
