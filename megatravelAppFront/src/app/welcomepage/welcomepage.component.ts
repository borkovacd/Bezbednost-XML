import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  templateUrl: './welcomepage.component.html',
})

export  class WelcomepageComponent{
  constructor(protected router: Router,){

  }

  logIn(){
    this.router.navigateByUrl('login');
  }
}
