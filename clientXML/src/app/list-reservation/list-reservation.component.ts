import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ReservationBackModel} from '../model/reservationBack.model';
import {ReservationService} from '../service/reservation.service';

@Component({
  selector: 'app-list-reservation',
  templateUrl: './list-reservation.component.html',
  styleUrls: ['./list-reservation.component.css']
})
export class ListReservationComponent implements OnInit {

  public reservations: ReservationBackModel[];

  vis: boolean = false;

  constructor(protected router: Router, private data: ReservationService) { }

  ngOnInit() {

      if (localStorage.getItem('loggedUser') != null) {
        this.vis = false;
        this.data.getMyRes().subscribe(data => {
          this.reservations = data;
        });
      } else {
        this.vis = true;
      }

  }

  cancelRes(id: number) {
    this.data.cancelRes(id).subscribe(data => {

      if(data==true) {
        alert('Uspesno otkazana rezervacija');
        window.location.reload();

      } else {
        alert('Nije moguce otkazati rezervaciju');
      }
    });

  }

  back() {
    this.router.navigateByUrl('');
  }
  oceniAcc(id: any, idRoom: any){
    this.data.checkIfComment(id).subscribe(data => {
      if(data == true){
        this.router.navigateByUrl('myReservations/' + idRoom + '/rating');

      }else{
        alert('Nije Vam prosla rezervacija!');

      }
    })


  }
}
