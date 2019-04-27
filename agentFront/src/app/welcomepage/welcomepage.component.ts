import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'welcomepage',
  templateUrl: './welcomepage.component.html',

})

export class WelcomepageComponent implements OnInit{

  accomodation = false;
  constructor(protected router: Router,){

  }
  ngOnInit(){

  }
  accomodationButton(){
    this.accomodation = true;


  }
  message(){

  }
  reservation(){

  }
}
