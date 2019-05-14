import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from "../service/user.service";

@Component ({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['/home.component.css']

})

export class HomeComponent implements  OnInit{

  constructor(protected router: Router,
              private userService: UserService){

  }
  ngOnInit() {
  }
  createCertificate(){
    this.router.navigateByUrl('createCertificate');
  }

  show() {
    this.router.navigateByUrl('certificates');
  }

  odjaviSe() {
    this.userService.logout().subscribe(res => {
      alert(res);
    });
    // window.location.reload();
    this.router.navigateByUrl('');

  }
}
