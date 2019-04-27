
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'accomodation',
  templateUrl: './accomodation.component.html'
})

export class AccomodationComponent implements OnInit{

  constructor(protected router: Router,){

  }

  ngOnInit(){

  }
  addAccomodation(){
    this.router.navigateByUrl('add/accomodation')
  }
}
