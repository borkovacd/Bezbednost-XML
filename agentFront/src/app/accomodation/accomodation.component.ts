
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccomodationService} from "../service/accomodation.service";

@Component({
  selector: 'accomodation',
  templateUrl: './accomodation.component.html'
})

export class AccomodationComponent implements OnInit{
  items = []
  idagent: any;

  constructor(protected router: Router,
              private accomodationService: AccomodationService,){

  }

  ngOnInit(){
    this.idagent = localStorage.getItem('agentId');
    this.accomodationService.getAccomodation(this.idagent).subscribe(data => {
      this.items = data;
    })

  }
  addAccomodation(){
    this.router.navigateByUrl('add/accomodation');
  }

  editAccomodation(id: any){

  }
  deleteAccomodation(id: any){

  }
  showAccomodation(idA: any){
    this.router.navigateByUrl('welcomepage/room/' + idA);
  }


}
