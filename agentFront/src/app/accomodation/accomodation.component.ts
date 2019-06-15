
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccomodationService} from "../service/accomodation.service";

@Component({
  selector: 'accomodation',
  templateUrl: './accomodation.component.html'
})

export class AccomodationComponent implements OnInit{
  items = []

  constructor(protected router: Router,
              private accomodationService: AccomodationService,){

  }

  ngOnInit(){
    this.accomodationService.getAccomodation().subscribe(data => {
      this.items = data;
    })

  }
  addAccomodation(){
    this.router.navigateByUrl('add/accomodation')
  }


}
