
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ReservationService} from '../service/reservation.service';

@Component({
  selector: 'reservations',
  templateUrl: './reservation.component.html',
})

export class ReservationComponent implements  OnInit {
  reservations = [];

  vis: boolean = false;

  constructor(protected router: Router,
              public reservationService: ReservationService) {
  }

  ngOnInit() {

    const idAgent = localStorage.getItem('agentId');

    if(idAgent!=null) {
      this.vis = false;
      this.reservationService.getAllReservation(idAgent).subscribe(data => {
        this.reservations = data;
      });
    } else {
      this.vis = true;
    }
  }
  confirmeReservation(id: any) {
  this.reservationService.confirmeReservation(id).subscribe(data => {

  });
    location.reload();
  }
}
