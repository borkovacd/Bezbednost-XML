
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
              private accomodationService: AccomodationService ){

  }

  ngOnInit(){
    this.idagent = localStorage.getItem('agentId');
    this.accomodationService.getAccomodation(this.idagent).subscribe(data => {
      this.items = data;
    })

  }
  addAccomodation(){
    this.router.navigateByUrl('add/accomodation/');
  }

  editAccomodation(id: any){
    this.accomodationService.checkIfReservedAccomodation(id).subscribe(data => {
      if(data == false){
        this.router.navigateByUrl('edit/accomodation/' + id);
      }else{
        alert('Smestaj je rezervisan!');
      }
    })



  }
  deleteAccomodation(id: any){
   // this.accomodationService.checkIfReservedAccomodation(id).subscribe(data => {
     // if(data === false){
        this.accomodationService.deleteAccomodation(id).subscribe(dd => {
         this.items = this.items.filter(el => el.id != id);
          //this.router.navigateByUrl('/welcomepage' );

        })
     // }else{
       // alert('Smestaj je rezervisan!');
     // }
    //})

  }
  showAccomodation(idA: any){
    this.router.navigateByUrl('welcomepage/room/' + idA);
  }


}
