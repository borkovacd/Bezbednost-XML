
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccomodationService} from '../service/accomodation.service';

@Component({
  selector: 'accomodation',
  templateUrl: './accomodation.component.html'
})

export class AccomodationComponent implements OnInit{
  items = []
  idagent: any;
  vis: boolean = false;

  constructor(protected router: Router,
              private accomodationService: AccomodationService ){

  }

  ngOnInit() {

    const log = localStorage.getItem('agentId');

    if (log != null) {
      this.vis = false;
      this.accomodationService.getAccomodation().subscribe(data => {
        this.items = data;
      });
    } else {
      this.vis = true;
    }
  }
  addAccomodation(){
    this.router.navigateByUrl('add/accomodation/');
  }

  editAccomodation(id: any){
    this.accomodationService.checkIfReservedAccomodation(id).subscribe(data => {
      if(data == false){
        this.router.navigateByUrl('edit/accomodation/' + id);
      }else{
        alert('Soba u tom smestaju je rezervisana!');
      }
    })



  }
  deleteAccomodation(id: any){
    this.accomodationService.checkIfReservedAccomodation(id).subscribe(data => {
     if(data === false){
        this.accomodationService.deleteAccomodation(id).subscribe(dd => {
         this.items = this.items.filter(el => el.id != id);
          //this.router.navigateByUrl('/welcomepage' );

        })
     }else{
        alert('Soba u tom smestaju je rezervisana!');
      }
    })

  }
  showAccomodation(idA: any){
    this.router.navigateByUrl('welcomepage/room/' + idA);
  }


}
