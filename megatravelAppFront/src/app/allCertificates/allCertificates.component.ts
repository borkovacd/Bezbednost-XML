import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CertificateBackModel} from '../model/certificateBack.model';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SecurityService} from '../service/security.service';
import {UserService} from '../service/user.service';

@Component ({
  templateUrl: './allCertificates.component.html'

})

export class AllCertificatesComponent implements OnInit {

  certif: CertificateBackModel[];
  selfCert: CertificateBackModel;
  user: any;
  visibleMessage: boolean;
  public form: FormGroup;
  public message: AbstractControl;

  constructor(protected router: Router,
              private fb: FormBuilder, private data: SecurityService, private  userService: UserService,) {
    this.form = this.fb.group({
      'message': ['', Validators.compose([Validators.required])],
    });
    this.message = this.form.controls['message'];


  }
  ngOnInit() {

    this.user = this.userService.getLoggedUser();

    // alert(this.user.email);

    this.data.getAllCert(this.user.email).subscribe( data => this.certif = data);
    this.visibleMessage=false;

  }
  revoke(serialNumber: any){

    this.visibleMessage=true;
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
    alert(isRevoked);
    if  (isRevoked == true) {
      return 'Povucen';
    } else {
      return 'Aktivan';
    }
  }

}

