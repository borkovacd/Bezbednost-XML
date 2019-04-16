import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  templateUrl: './welcomepage.component.html',
  styleUrls: ['./welcomepage.component.css']
})

export  class WelcomepageComponent{
  constructor(protected router: Router,){

  }

  logIn(){
    this.router.navigateByUrl('login');
  }

  registration(){
    this.router.navigateByUrl('registration');
  }

}
